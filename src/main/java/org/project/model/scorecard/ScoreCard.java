package org.project.model.scorecard;


import org.project.model.CricketGame;

public class ScoreCard {

    InningScoreCard[] innings = new InningScoreCard[4];

    public ScoreCard(CricketGame game) {
        if (game.getBattingTeamIndex() == 1) {
            innings[0] = new BattingScoreCard(game, game.getTeam1());
            innings[1] = new BowlingScoreCard(game, game.getTeam2());
            innings[2] = new BattingScoreCard(game, game.getTeam2());
            innings[3] = new BowlingScoreCard(game, game.getTeam1());
        } else {
            innings[0] = new BattingScoreCard(game, game.getTeam2());
            innings[1] = new BowlingScoreCard(game, game.getTeam1());
            innings[2] = new BattingScoreCard(game, game.getTeam1());
            innings[3] = new BowlingScoreCard(game, game.getTeam2());
        }
    }

    public void printScoreCard() {
        for (InningScoreCard stats : innings) {
            stats.showStats();
        }
    }
}
