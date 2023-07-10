package tomasborsje.plugin.fantasymmo.entities.npcs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.bukkit.Location;
import tomasborsje.plugin.fantasymmo.core.CustomNPC;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.util.GUIUtil;
import tomasborsje.plugin.fantasymmo.registries.QuestRegistry;

public class TestNPC extends CustomNPC {

    /**
     * Create a new instance of this entity at the given location.
     * Make sure you set id, name, location, etc. in any derived constructors.
     *
     * @param loc The location to spawn the entity at
     */
    public TestNPC(Location loc) {
        super(loc);
        this.id = "TEST_NPC";
        this.name = "Test NPC";
        this.heldItem = new ItemStack(Items.IRON_SWORD);
    }

    @Override
    public void interact(PlayerData playerData) {
        super.interact(playerData);
        // Open quest if not completed
        boolean questShown = GUIUtil.ShowQuestPromptIfNotCompleted(playerData, QuestRegistry.KILL_FOREST_SLIMES, this.name);
        if(questShown) { return; }
    }
}
