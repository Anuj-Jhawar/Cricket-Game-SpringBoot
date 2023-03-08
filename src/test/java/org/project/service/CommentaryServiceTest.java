package org.project.service;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.model.Ball;
import org.project.model.BallCommentary;
import org.project.model.Match;
import org.project.model.Team;
import org.project.model.stats.BattingStats;
import org.project.repo.CommentaryRepository;
import org.project.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentaryServiceTest {
    @Mock
    private CommentaryRepository commentaryRepository;
    @Mock
    private MatchService matchService;
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private CommentaryService commentaryService;
    @BeforeEach
    void beforeEach(){

    }
    @Test
    void addCommentary() {

        Ball ball = new Ball();
        ball.setBatsmanName("Rohit");
        ball.setBowlerName("Dhoni");

        Match match = new Match();
        Team team = new Team();
        Team team1 = new Team();
        team.setTeamName("Mumbai");
        team1.setTeamName("Chennai");
        match.setTournamentName("Anuj");
        match.setBattingTeamIndex(1);
        match.setTeam1(team);
        match.setTeam2(team1);


        when(matchService.getMatchId("Anuj","Mumbai","Chennai",1)).thenReturn(1);
        when(playerService.getPlayerId("Rohit")).thenReturn(1);
        when(playerService.getPlayerId("Dhoni")).thenReturn(2);

        String commentaryText = "Its a Four.";
        int inningNo = 1;
        BallCommentary ballCommentary = new BallCommentary(1,2,"Its a Four.");

        commentaryService.addCommentary(ball,match,commentaryText,inningNo);
        Mockito.verify(commentaryRepository, Mockito.times(1)).updateCommentary(1,ballCommentary,inningNo);
    }

    @Test
    void getCommentary() {
        ArrayList<ArrayList<Document>> arrayLists = new ArrayList<>();
        Document document = new Document();
        document.append("aa","bb");
        ArrayList<Document>arrayList = new ArrayList<>();
        arrayList.add(document);
        arrayLists.add(arrayList);
        arrayLists.add(arrayList);
        arrayLists.add(arrayList);
        arrayLists.add(arrayList);
        when(commentaryRepository.getCommentary(1)).thenReturn(arrayLists);
        assertEquals(arrayLists,commentaryService.getCommentary(1));
    }
}