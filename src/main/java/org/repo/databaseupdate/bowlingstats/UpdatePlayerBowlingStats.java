package org.repo.databaseupdate.bowlingstats;

import java.sql.Connection;

public interface UpdatePlayerBowlingStats {
    void update(int stats, Connection connection);
}
