package org.service.cricketgame;

public class Umpire {
    private final String name;

    Umpire() {
        name = "Kumar DharmaSena.";
    }

    void wicket() {
        /*
            Function which helps umpire to signal wicket.
        */
        System.out.println("Its a Wicket");
    }

    void four() {
        /*
            Function which helps umpire to signal four.
        */
        System.out.println("Its a four");
    }

    void six() {
        /*
            Function which helps umpire to signal Six.
        */
        System.out.println("Its a Six");
    }

    void wide() {
        /*
            Function which helps umpire to signal Wide.
        */
        System.out.println("Its a wide");
    }

    void noBall() {
        /*
            Function which helps umpire to signal NoBall.
        */
        System.out.println("Its a NoBall and a FreeHit!!!");
    }

    void runs(int RunsScored) {
        /*
            Runs scored other than 4 or 6.
        */
        System.out.println("On this ball BatsMan scored " + RunsScored);
    }

    void signal(int OutcomeOfTheBall) {
        /*
            Helps Others.Umpire decides to signal different function depending on the OutcomeOfTheBall.
        */
        if (OutcomeOfTheBall == 7)
            wicket();
        else if (OutcomeOfTheBall == 4)
            four();
        else if (OutcomeOfTheBall == 6)
            six();
        else
            runs(OutcomeOfTheBall);
    }

}

