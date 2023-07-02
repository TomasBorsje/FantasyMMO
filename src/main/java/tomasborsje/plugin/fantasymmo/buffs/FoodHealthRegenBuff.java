package tomasborsje.plugin.fantasymmo.buffs;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.Buff;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.util.EffectUtil;

public class FoodHealthRegenBuff extends Buff {
    private final int healthPerSec;
    public FoodHealthRegenBuff(String name, int duration, int healthPerSec) {
        super(name, duration);
        this.healthPerSec = healthPerSec;
        this.nonCombat = true;
    }

    @Override
    public void modifyStats(PlayerData playerStats) {
        playerStats.healthRegenFlat += healthPerSec;
    }

    @Override
    public void tick(IBuffable buffHolder) {
        super.tick(buffHolder);

        // Spawn a blue particle every 2 ticks
        if(this.ticksLeft % 8 == 0) {
            Player player = ((PlayerData) buffHolder).player;
            // Spawn blue particles around player
            EffectUtil.SpawnParticleAroundPlayer(player, Particle.HEART);
        }
    }
}
