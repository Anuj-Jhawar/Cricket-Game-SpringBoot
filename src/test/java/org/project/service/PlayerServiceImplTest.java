package org.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.repo.PlayerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerServiceImpl;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    @Test
    void getPlayerId() {
        //beforeEach();
        when(playerRepository.getPlayerId("Anuj")).thenReturn(1);
        int playerID = playerServiceImpl.getPlayerId("Anuj");
        assertEquals(1, playerID);
    }

    @Test
    void getPlayerName() {
        when(playerRepository.getPlayerName(1)).thenReturn("Anuj");
        String playerName = playerServiceImpl.getPlayerName(1);
        assertEquals("Anuj", playerName);
    }

    @Test
    void addPlayer() {
        playerServiceImpl.addPlayer("Anuj", "Batsman", 21);
        Mockito.verify(playerRepository, Mockito.times(1)).addPlayer("Anuj", "Batsman", 21);
    }
}