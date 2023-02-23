package org.model;

import org.model.stats.Stats;

public class ScoreCardForPlayer {
    private String name;
    private Stats stats;

    public ScoreCardForPlayer(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public String getName() {
        return name;
    }
}
