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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BattingStatsServiceTest {
    @Mock
    private BattingStatsRepository battingStatsRepository;
    @InjectMocks
    private BattingStatsService battingStatsService;
    @BeforeEach
    void beforeEach(){

    }
    @Test
    void getBattingStats() {
        BattingStats battingStats = new BattingStats();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(6);
        battingStats.setBallsPlayed(10);
        battingStats.setScore(11);
        when(battingStatsRepository.getBattingStats(1,1,1)).thenReturn(battingStats);
        assertEquals(battingStats,battingStatsService.getBattingStats(1,1,1));
    }
    @Test
    void addBattingStats(){
        BattingStats battingStats = new BattingStats();
        battingStats.setScore(1);
        battingStats.setBallsPlayed(1);
        battingStats.setStrikeRate();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(1);
        battingStatsService.addBattingStats(battingStats,1,1,1);
        Mockito.verify(battingStatsRepository, Mockito.times(1)).addBattingStats(battingStats,1,1,1);

    }
    @Test
    void updateBattingStats(){
        BattingStats battingStats = new BattingStats();
        battingStats.setScore(1);
        battingStats.setBallsPlayed(1);
        battingStats.setStrikeRate();
        battingStats.setStrikeRate();
        battingStats.setBoundaries(1);
        battingStatsService.updateBattingStats(1,1,1,4,5,2);
        Mockito.verify(battingStatsRepository, Mockito.times(1)).updateBattingStats(1,1,1,4,5,2);
    }
}