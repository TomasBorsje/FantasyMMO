package tomasborsje.plugin.fantasymmo.buffs;

import org.bukkit.Color;
import org.bukkit.entity.Player;
import tomasborsje.plugin.fantasymmo.core.Buff;
import tomasborsje.plugin.fantasymmo.core.PlayerData;
import tomasborsje.plugin.fantasymmo.core.interfaces.IBuffable;
import tomasborsje.plugin.fantasymmo.core.util.EffectUtil;

public class FoodManaRegenBuff extends Buff {
    private final int manaPerSec;
    public FoodManaRegenBuff(String name, int duration, int manaPerSec) {
        super(name, duration);
        this.manaPerSec = manaPerSec;
        this.nonCombat = true;
    }

    @Override
    public void modifyStats(PlayerData playerStats) {
        playerStats.manaRegenFlat += manaPerSec;
    }

    @Override
    public void tick(IBuffable buffHolder) {
        super.tick(buffHolder);

        // Spawn a blue particle every 2 ticks
        if(this.ticksLeft % 8 == 0) {
            Player player = ((PlayerData) buffHolder).player;
            // Spawn blue particles around player
            EffectUtil.SpawnColoredSwirlAroundPlayer(player, Color.AQUA);
        }
    }
}
