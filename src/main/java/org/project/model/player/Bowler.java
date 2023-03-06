package org.project.model.player;

import org.springframework.stereotype.Component;

@Component
public class Bowler extends Player {

    public Bowler() {
    }

    public void setName(String Name) {
        this.name = Name;
    }
}
