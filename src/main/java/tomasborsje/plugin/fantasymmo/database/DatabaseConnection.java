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

import static com.mongodb.client.model.Filters.eq;

public class DatabaseConnection {
    private final String connectionString = "***REMOVED***";
    private final ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
    private final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();

    private final ReplaceOptions INSERT_OR_REPLACE = new ReplaceOptions().upsert(true);
    private MongoClient client;
    private MongoDatabase database;
    private MongoCollection<Document> players;
    public DatabaseConnection() {
        // Connect to the cluster
        client = MongoClients.create(settings);

        // Store connection to our MMO database
        database = client.getDatabase("mmo");

        // Store player collection
        players = database.getCollection("players");

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
            playerData.setLevel(level);

            int experience = playerDoc.getInteger("experience");
            playerData.setExperience(experience);

            int money = playerDoc.getInteger("money");
            playerData.addMoney(money);
        } catch (Exception e) {
            Bukkit.getLogger().info("Error loading player data for " + username + ": " + e.getMessage());
        }

        // Give player max health and mana on login
        playerData.fillHealthAndMana();

        Bukkit.getLogger().info("Loaded player data for " + username + " from database.");
        return playerData;
    }

    public boolean playerExistsInDB(String username) {
        return getPlayers().countDocuments(eq("username", username)) > 0;
    }

    private MongoCollection<Document> getPlayers() {
        return database.getCollection("players");
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

        Bson query = eq("username", username);

        // Save document to database
        getPlayers().replaceOne(query, playerDoc, INSERT_OR_REPLACE);

        Bukkit.getLogger().info("Saved player data for " + username + " to database.");
    }
}
