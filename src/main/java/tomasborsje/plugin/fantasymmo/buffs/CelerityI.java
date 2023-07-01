package tomasborsje.plugin.fantasymmo.buffs;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.Buff;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.util.EffectUtil;

public class CelerityI extends Buff {
    private final float manaRegenBuff = 0.25f;
    public CelerityI() {
        super("Celerity I", 200);
    }

    @Override
    public void onApply(IBuffable buffHolder) {
        if(buffHolder instanceof PlayerData playerData) {
            playerData.player.sendMessage(ChatColor.BLUE+"Your mana surges! (+"+(int)(manaRegenBuff*100)+"% Mana Regeneration)");
        }
    }

    @Override
    public void modifyStats(PlayerData playerStats) {
        playerStats.manaRegenMultiplier += manaRegenBuff;
        playerStats.moveSpeedMultiplier += 1f;
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
