package org.project.model;

import org.project.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Umpire {

    private final String name;
    @Autowired
    CommentaryService commentaryService;

    public Umpire() {
        name = "Kumar DharmaSena.";
    }

    String wicket() {
        /*
            Function which helps umpire to signal wicket.
        */
        String commentary = "Its a Wicket";
        System.out.println(commentary);
        return commentary;
    }

    String four() {
        /*
            Function which helps umpire to signal four.
        */
        String commentary = "Its a four";
        System.out.println(commentary);
        return commentary;
    }

    String six() {
        /*
            Function which helps umpire to signal Six.
        */
        String commentary = "Its a Six";
        System.out.println(commentary);
        return commentary;
    }

    String wide() {
        /*
            Function which helps umpire to signal Wide.
        */
        String commentary = "Its a wide";
        System.out.println(commentary);
        return commentary;
    }

    String noBall() {
        /*
            Function which helps umpire to signal NoBall.
        */
        String commentary = "Its a NoBall and a FreeHit!!!";
        System.out.println(commentary);
        return commentary;
    }

    String runs(int RunsScored) {
        /*
            Runs scored other than 4 or 6.
        */
        String commentary = "On this ball BatsMan scored " + RunsScored;
        System.out.println(commentary);
        return commentary;

    }

    public void signal(CricketGame game, Ball ball, int inningNo) {
        /*
            Helps Others.Umpire decides to signal different function depending on the OutcomeOfTheBall.
        */
        int OutcomeOfTheBall = ball.getOutcomeOfTheBall();
        String commentaryText = "";
        if (OutcomeOfTheBall == 7) {
            commentaryText = wicket();
        } else if (OutcomeOfTheBall == 4) {
            commentaryText = four();
        } else if (OutcomeOfTheBall == 6) {
            commentaryText = six();
        } else {
            commentaryText = runs(OutcomeOfTheBall);
        }
        commentaryService.addCommentary(ball, game, commentaryText, inningNo);
    }

}

