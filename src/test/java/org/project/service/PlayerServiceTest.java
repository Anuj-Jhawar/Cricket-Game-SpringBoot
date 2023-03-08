package org.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.project.model.player.Player;
import org.project.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void beforeEach(){
    }
    @AfterEach
    void afterEach(){
    }

    @Test
    void getPlayerId() {
        //beforeEach();
        when(playerRepository.getPlayerId("Anuj")).thenReturn(1);
        int playerID = playerService.getPlayerId("Anuj");
        assertEquals(1,playerID);
    }

    @Test
    void getPlayerName() {
        when(playerRepository.getPlayerName(1)).thenReturn("Anuj");
        String playerName = playerService.getPlayerName(1);
        assertEquals("Anuj", playerName);
    }

    @Test
    void addPlayer() {
        playerService.addPlayer("Anuj","Batsman",21);
        Mockito.verify(playerRepository, Mockito.times(1)).addPlayer("Anuj","Batsman",21);
    }
}