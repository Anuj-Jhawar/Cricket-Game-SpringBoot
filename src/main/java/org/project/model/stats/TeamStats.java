package org.project.model.stats;

public class TeamStats implements Stats{
    private int runsScored;
    private int runsConceded;
    private int wicketsTaken;
    private int wicketsFallen;

    public void TeamStats(){
        runsConceded = 0;
        runsScored = 0;
        wicketsFallen = 0;
        wicketsTaken = 0;
    }
    public void updateStats(int runs){

    }
}
