package tomasborsje.plugin.fantasymmo.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.enums.PlayerRank;
import tomasborsje.plugin.fantasymmo.registries.QuestRegistry;
import tomasborsje.plugin.fantasymmo.quests.AbstractQuestInstance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class DatabaseConnection {
    private final String connectionString = "";
    private final ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
    private final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    private final ReplaceOptions INSERT_OR_REPLACE = new ReplaceOptions().upsert(true);
    private MongoClient client;
    private MongoDatabase database;
    public DatabaseConnection() {
        // Connect to the cluster
        client = MongoClients.create(settings);

        // Store connection to our MMO database
        database = client.getDatabase("mmo");

        // Log connection
        Bukkit.getLogger().info("Connected to database.");
    }

    /**
     * Gets and returns a PlayerData for a player from the MongoDB instance.
     * If the player doesn't exist in the database, a new PlayerData is created and returned.
     * @param player The player to load data for
     * @return The PlayerData for the player
     */
    public PlayerData loadPlayerData(Player player) {
        // Get player username
        String username = player.getName();

        // If the player doesn't exist in DB, just return a new blank player data
        if(!playerExistsInDB(username)) {
            Bukkit.getLogger().info("Player " + username + " does not exist in database. Returning new player data.");
            return new PlayerData(player);
        }

        // Retrieve player document from db
        Document playerDoc = getPlayers().find(eq("username", username)).first();

        // Build a new playerdata with this document
        PlayerData playerData = new PlayerData(player);

        try {
            int level = playerDoc.getInteger("level");
            int experience = playerDoc.getInteger("experience");
            playerData.setLevelAndExperience(level, experience);

            playerData.rank = PlayerRank.valueOf(playerDoc.getString("rank"));

            int money = playerDoc.getInteger("money");
            playerData.addMoney(money);

            List<String> knownRecipes = playerDoc.getList("knownRecipes", String.class);
            // Need to be careful of null values returned if player knows no recipes
            playerData.knownRecipeIds = knownRecipes == null ? new ArrayList<>() : knownRecipes;

            // Load completed quest ids
            List<String> completedQuestIds = playerDoc.getList("completedQuests", String.class);
            playerData.completedQuests = completedQuestIds == null ? new HashSet<>() : new HashSet<>(completedQuestIds);
        } catch (Exception e) {
            Bukkit.getLogger().info("Error loading player data for " + username + ": " + e.getMessage());
        }

        // Load each quest instance the player has
        List<Document> questInstances = (List<Document>) playerDoc.get("quests");

        if(questInstances != null) {
            for(Document questInstance : questInstances) {
                try {
                AbstractQuestInstance quest = QuestRegistry.QUESTS.get(questInstance.getString("questId")).apply(playerData);
                // Get quest stage
                int stage = questInstance.getInteger("stage");
                // Get objective progress array
                List<Integer> objectiveProgress = questInstance.getList("objectiveProgress", Integer.class);
                // Load quest instance
                quest.load(stage, objectiveProgress);
                // Add to player's quests again
                playerData.addQuest(quest); }
                catch (Exception e) {
                    Bukkit.getLogger().info("Error loading quest instance for " + username + ": " + e.getMessage());
                }
            }
        }

        // Give player max health and mana on login
        playerData.recalculateStats();
        playerData.fillHealthAndMana();

        Bukkit.getLogger().info("Loaded player data for " + username + " from database.");
        return playerData;
    }

    public void savePlayerData(PlayerData playerData) {
        // Get player username
        String username = playerData.getUsername();

        // Create a document for the player
        Document playerDoc = new Document();

        // Add player data to the document
        playerDoc.append("username", username);
        playerDoc.append("level", playerData.getLevel());
        playerDoc.append("experience", playerData.getExperience());
        playerDoc.append("money", playerData.getMoney());
        playerDoc.append("knownRecipes", playerData.knownRecipeIds);
        playerDoc.append("completedQuests", playerData.completedQuests);
        playerDoc.append("rank", playerData.rank.name());

        // Transform quests into documents
        List<Document> questInstances = new ArrayList<>();
        for(AbstractQuestInstance quest : playerData.activeQuests) {
            Document questInstance = new Document();
            questInstance.append("questId", quest.getCustomId());
            questInstance.append("stage", quest.getStage());
            questInstance.append("objectiveProgress", quest.getLoadData());
            questInstances.add(questInstance);
        }

        // Store quest array to player document
        playerDoc.append("quests", questInstances);


        Bson query = eq("username", username);

        // Save document to database
        getPlayers().replaceOne(query, playerDoc, INSERT_OR_REPLACE);

        Bukkit.getLogger().info("Saved player data for " + username + " to database.");
    }

    public boolean playerExistsInDB(String username) {
        return getPlayers().countDocuments(eq("username", username)) > 0;
    }

    private MongoCollection<Document> getPlayers() {
        return database.getCollection("players");
    }
}
