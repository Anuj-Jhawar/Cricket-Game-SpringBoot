package org.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@Data
public class ScoreCardDTO {

    int tournamentId;
    int team1Id;
    int team2Id;
    Date date;
}
