package tomasborsje.plugin.fantasymmo.buffs;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.Buff;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.util.EffectUtil;

public class BasicIntelligenceBoost extends Buff {
    public BasicIntelligenceBoost() {
        super("Intelligence Boost", 100);
    }

    @Override
    public void onApply(IBuffable buffHolder) {
        if(buffHolder instanceof PlayerData playerData) {
            playerData.player.sendMessage(ChatColor.BLUE+"You feel smarter! (+100 Intelligence)");
        }
    }

    @Override
    public void modifyStats(PlayerData playerStats) {
        playerStats.intelligence += 100;
    }

    @Override
    public void tick(IBuffable buffHolder) {
        super.tick(buffHolder);

        // Spawn a particle every 2 ticks
        if(this.ticksLeft % 2 == 0) {
            Player player = ((PlayerData) buffHolder).player;
            // Spawn blue particles around player
            player.getWorld().spawnParticle(Particle.REDSTONE, EffectUtil.getPointAroundPlayer(player.getLocation()),
                    0, 1, 0, 0, 0, new Particle.DustOptions(org.bukkit.Color.BLUE, 0.7f));
        }
    }
}
