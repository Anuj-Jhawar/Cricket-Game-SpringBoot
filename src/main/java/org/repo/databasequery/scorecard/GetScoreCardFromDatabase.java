package org.repo.databasequery.scorecard;


import org.service.cricketgame.CricketGame;
import org.service.stats.Stats;

public interface GetScoreCardFromDatabase{
    Stats getStats(CricketGame game, String teamName, String playerName);
}
