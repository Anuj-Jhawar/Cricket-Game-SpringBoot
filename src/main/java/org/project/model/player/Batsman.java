package org.project.model.player;

import org.springframework.stereotype.Component;

@Component
public class Batsman extends Player {

    public Batsman() {
    }

    public void setName(String Name) {
        this.name = Name;
    }

}
