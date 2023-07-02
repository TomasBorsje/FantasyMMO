package tomasborsje.plugin.fantasymmo.core.interfaces;

import tomasborsje.plugin.fantasymmo.core.StatBoost;

public interface IHasItemScore {
    public default int getItemScore() {
        // Calculate item score
        int score = 1;
        if(this instanceof IStatProvider statsProvider) {
            StatBoost s = statsProvider.getStats();
            score += s.strength + s.intelligence + s.health/5 + s.defense/2;
        }

        return score;
    }
}
