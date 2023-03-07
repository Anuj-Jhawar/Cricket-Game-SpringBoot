package org.project.model.scorecard;


import org.project.model.Match;
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

    public void setScoreCard(Match match) {
        if (match.getBattingTeamIndex() == 1) {
            battingScoreCard.setBattingScoreCard(match, match.getTeam1());
            battingScoreCard2.setBattingScoreCard(match, match.getTeam2());
            bowlingScoreCard.setBowlingScoreCard(match, match.getTeam2());
            bowlingScoreCard2.setBowlingScoreCard(match, match.getTeam1());
        } else {
            battingScoreCard.setBattingScoreCard(match, match.getTeam2());
            battingScoreCard2.setBattingScoreCard(match, match.getTeam1());
            bowlingScoreCard.setBowlingScoreCard(match, match.getTeam1());
            bowlingScoreCard2.setBowlingScoreCard(match, match.getTeam2());
        }
    }

    public void printScoreCard() {
        battingScoreCard.showStats();
        bowlingScoreCard.showStats();
        battingScoreCard2.showStats();
        bowlingScoreCard2.showStats();
    }
}
