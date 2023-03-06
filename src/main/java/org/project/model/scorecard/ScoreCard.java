package org.project.model.scorecard;


import org.project.model.CricketGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScoreCard {


    @Autowired
    private BattingScoreCard battingScoreCard;
    @Autowired
    private BattingScoreCard battingScoreCard2;
    @Autowired
    private BowlingScoreCard bowlingScoreCard;
    @Autowired
    private BowlingScoreCard bowlingScoreCard2;

    public void setScoreCard(CricketGame game) {
        if (game.getBattingTeamIndex() == 1) {
            battingScoreCard.setBattingScoreCard(game, game.getTeam1());
            battingScoreCard2.setBattingScoreCard(game, game.getTeam2());
            bowlingScoreCard.setBowlingScoreCard(game, game.getTeam2());
            bowlingScoreCard2.setBowlingScoreCard(game, game.getTeam1());
        } else {
            battingScoreCard.setBattingScoreCard(game, game.getTeam2());
            battingScoreCard2.setBattingScoreCard(game, game.getTeam1());
            bowlingScoreCard.setBowlingScoreCard(game, game.getTeam1());
            bowlingScoreCard2.setBowlingScoreCard(game, game.getTeam2());
        }
    }

    public void printScoreCard() {
        battingScoreCard.showStats();
        bowlingScoreCard.showStats();
        battingScoreCard2.showStats();
        bowlingScoreCard2.showStats();
    }
}
