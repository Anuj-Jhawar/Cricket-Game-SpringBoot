package org.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.model.stats.BattingStats;
import org.project.repo.BattingStatsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BattingStatsServiceImplTest {

    @Mock
    private BattingStatsRepository battingStatsRepository;
    @InjectMocks
    private BattingStatsServiceImpl battingStatsServiceImpl;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void getBattingStats() {
        BattingStats battingStats = new BattingStats();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(6);
        battingStats.setBallsPlayed(10);
        battingStats.setScore(11);
        when(battingStatsRepository.getBattingStats(1, 1, 1)).thenReturn(battingStats);
        assertEquals(battingStats, battingStatsServiceImpl.getBattingStats(1, 1, 1));
    }

    @Test
    void addBattingStats() {
        BattingStats battingStats = new BattingStats();
        battingStats.setScore(1);
        battingStats.setBallsPlayed(1);
        battingStats.setStrikeRate();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(1);
        battingStatsServiceImpl.addBattingStats(battingStats, 1, 1, 1);
        Mockito.verify(battingStatsRepository, Mockito.times(1)).addBattingStats(battingStats, 1, 1, 1);

    }

    @Test
    void updateBattingStats() {
        BattingStats battingStats = new BattingStats();
        battingStats.setScore(1);
        battingStats.setBallsPlayed(1);
        battingStats.setStrikeRate();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(1);
        battingStatsServiceImpl.updateBattingStats(1, 1, 1, 4, 5, 2);
        Mockito.verify(battingStatsRepository, Mockito.times(1)).updateBattingStats(1, 1, 1, 4, 5, 2);
    }

}