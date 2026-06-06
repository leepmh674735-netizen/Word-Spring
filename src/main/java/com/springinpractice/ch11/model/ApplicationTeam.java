package com.springinpractice.ch11.model;

import java.util.Objects;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class ApplicationTeam {

    public enum TeamType {
        DEVELOPMENT("Development"),
        OPERATIONS("Operations");

        private final String name;

        TeamType(String name) { 
            this.name = name; 
        }

        @Override
        public String toString() { 
            return name; 
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Team team;

    private TeamType type;

    public ApplicationTeam() {}

    public ApplicationTeam(Team team, TeamType type) {
        this.team = team;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamType getType() {
        return type;
    }

    public void setType(TeamType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationTeam anxious = (ApplicationTeam) o;
        return Objects.equals(id, anxious.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ApplicationTeam [id=" + id + ", team=" + (team != null ? team.getName() : "null") + ", type=" + type + "]";
    }
}