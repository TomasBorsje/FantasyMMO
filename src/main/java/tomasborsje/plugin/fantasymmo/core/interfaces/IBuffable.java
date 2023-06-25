package tomasborsje.plugin.fantasymmo.core.interfaces;

import tomasborsje.plugin.fantasymmo.core.Buff;

/**
 * Represents an entity that can receive buffs (or debuffs).
 */
public interface IBuffable {
    public void addBuff(Buff buff);
    public void removeAllBuffs();
}
