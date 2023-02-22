package org.model;

public class Toss {
    private int teamWhoWonTheToss;
    private int battingTeamIndex;
    private int bowlingTeamIndex;
    public int AssignBattingTeam(){
        /*
            Assigning the batting team to the game.
        */
        double value = Math.random();
        int tossValue = (int) (value * 2);
        if (tossValue == 0)
            return 1;
        else return 2;
    }
    public int callForToss() {
        /*
            Function which decides which team have won the toss.
        */
        double value = Math.random();
        int tossValue = (int) (value * 2);
        teamWhoWonTheToss = tossValue==0?1:2;
        battingTeamIndex = this.AssignBattingTeam();
        bowlingTeamIndex = battingTeamIndex==1?2:1;
        return teamWhoWonTheToss;
    }

    public int getTeamWhoWonTheToss() {
        return teamWhoWonTheToss;
    }
    public int getBattingTeamIndex(){
        return battingTeamIndex;
    }
    public int getBowlingTeamIndex(){
        return bowlingTeamIndex;
    }
}
