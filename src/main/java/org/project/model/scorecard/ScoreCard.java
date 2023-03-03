package org.project.model.scorecard;


import org.project.model.CricketGame;
import org.project.service.BattingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScoreCard {


    @Autowired
    BattingScoreCard battingScoreCard;
    @Autowired
    BattingScoreCard battingScoreCard2;
    @Autowired
    BowlingScoreCard bowlingScoreCard;
    @Autowired
    BowlingScoreCard bowlingScoreCard2;

    public void setScoreCard(CricketGame game) {
        if (game.getBattingTeamIndex() == 1) {
            battingScoreCard.setBattingScoreCard(game,game.getTeam1());
            battingScoreCard2.setBattingScoreCard(game,game.getTeam2());
            bowlingScoreCard.setBowlingScoreCard(game,game.getTeam2());
            bowlingScoreCard.setBowlingScoreCard(game,game.getTeam1());
        } else {
            battingScoreCard.setBattingScoreCard(game,game.getTeam2());
            battingScoreCard2.setBattingScoreCard(game,game.getTeam1());
            bowlingScoreCard.setBowlingScoreCard(game,game.getTeam1());
            bowlingScoreCard.setBowlingScoreCard(game,game.getTeam2());
        }
    }

    public void printScoreCard() {
        battingScoreCard.showStats();
        battingScoreCard2.showStats();
        bowlingScoreCard.showStats();
        bowlingScoreCard2.showStats();
    }
}
