package org.repo.databaseupdate.battingstats;

import java.sql.Connection;

public interface UpdatePlayerBattingStats {
    void update(int stats, Connection connection);
}
