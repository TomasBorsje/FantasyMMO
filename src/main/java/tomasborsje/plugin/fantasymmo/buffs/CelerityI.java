package tomasborsje.plugin.fantasymmo.buffs;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.Buff;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.util.EffectUtil;

public class CelerityI extends Buff {
    private final int flatManaRegenBuff = 2;
    public CelerityI() {
        super("Celerity I", 200);
    }

    @Override
    public void onApply(IBuffable buffHolder) {
        if(buffHolder instanceof PlayerData playerData) {
            playerData.player.sendMessage(ChatColor.BLUE+"Your mana surges! (+"+flatManaRegenBuff+"/s Mana Regeneration)");
        }
    }

    @Override
    public void modifyStats(PlayerData playerStats) {
        playerStats.manaRegenFlat += flatManaRegenBuff;
    }

    @Override
    public void tick(IBuffable buffHolder) {
        super.tick(buffHolder);

        // Spawn a blue particle every 2 ticks
        if(this.ticksLeft % 2 == 0) {
            Player player = ((PlayerData) buffHolder).player;
            // Spawn blue particles around player
            EffectUtil.spawnColoredParticleAroundPlayer(player, Color.BLUE);
        }
    }
}
