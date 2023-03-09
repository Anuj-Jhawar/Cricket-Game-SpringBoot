package org.project.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Bowler;
import org.project.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class PlayerServiceImpl {

    @Autowired
    private PlayerRepository playerRepository;
//    public PlayerService(PlayerRepository playerRepository){
//        this.playerRepository = playerRepository;
//    }

    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        return playerRepository.getPlayerId(playerName);
    }

    public String getPlayerName(int playerID) {
        return playerRepository.getPlayerName(playerID);
    }

    public void addPlayerToPlayerTable(Match match) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? match.getTeam1() : match.getTeam2();
            org.project.model.player.Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                String type = players[j] instanceof Bowler ? "Bowler" : "Batsman";
                this.addPlayer(players[j].getName(),type, 1);
            }
        }
    }

    public void addPlayer(String playerName,String type, int age) {
        playerRepository.addPlayer(playerName,type, age);
    }
}
