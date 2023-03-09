package org.project.service;

import org.project.model.Team;

import java.util.Map;

public interface TeamService {

    public Team setTeamRepository(Map<String, Object> document);

    public String addTeamToTeamTable(org.project.model.Team team);

    public int getTeamId(String teamName);

}
