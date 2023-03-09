package org.project.service;

import org.project.model.stats.BattingStats;
import org.project.model.stats.BowlingStats;
import org.project.model.stats.Stats;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public interface PlayerStatsService {
    public Stats[] get(Map<String, Object> requestBody);

    public void setPlayerStatsService(Map<String, Object> requestBody);
}
