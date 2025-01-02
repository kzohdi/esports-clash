package com.spoofy.esportsclash.team.domain.models;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import com.spoofy.esportsclash.player.domain.models.Player;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
public class Team extends BaseModel<Team> {

    @Column
    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<TeamMember> members;

    private static final int REQUIRED_NUMBER_OF_PLAYERS = 5;

    public Team() {
    }

    public Team(String id, String name) {
        super(id);
        this.name = name;
        this.members = new HashSet<>();
    }

    private Team(String id, String name, Set<TeamMember> members) {
        super(id);
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TeamMember> getMembers() {
        return members;
    }

    @Override
    public Team deepClone() {
        return new Team(
                id,
                name,
                members.stream().map(TeamMember::deepClone).collect(Collectors.toSet())
        );
    }

    public void addMember(String playerId, Role role) {
        if (hasMember(playerId)) {
            throw new IllegalStateException("The player is already in the team");
        }

        if (hasMember(role)) {
            throw new IllegalStateException("The role is already in the team");
        }

        var teamMember = new TeamMember(
                UUID.randomUUID().toString(),
                id,
                playerId,
                role);

        members.add(teamMember);
    }

    public void removeMember(String playerId) {
        if (!hasMember(playerId)) {
            throw new IllegalStateException("The player is not in the team");
        }
        members.removeIf(teamMember -> teamMember.getPlayerId().equals(playerId));
    }

    public boolean hasMember(String playerId, Role role) {
        return members.stream()
                .anyMatch(member -> member.getPlayerId().equals(playerId)
                        && member.getRole().equals(role));
    }

    public boolean hasMember(String playerId) {
        return members.stream()
                .anyMatch(member -> member.getPlayerId().equals(playerId));
    }

    public boolean hasMember(Role role) {
        return members.stream()
                .anyMatch(member -> member.getRole().equals(role));
    }

    public boolean isIncomplete() {
        return members.size() < REQUIRED_NUMBER_OF_PLAYERS;
    }

    @Entity
    @Table(name = "team_members")
    public static class TeamMember extends BaseModel<TeamMember> {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "player_id", insertable = false, updatable = false)
        @MapsId("playerId")
        private Player player;

        @Column(name = "player_id")
        private String playerId;

        @Column
        @Enumerated(EnumType.STRING)
        private Role role;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", insertable = false, updatable = false)
        @MapsId("teamId")
        private Team team;

        @Column(name = "team_id")
        private String teamId;

        public TeamMember() {
        }

        public TeamMember(String id, String teamId, String playerId, Role role) {
            super(id);
            this.teamId = teamId;
            this.playerId = playerId;
            this.role = role;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public Player getPlayer() {
            return player;
        }

        @Override
        public TeamMember deepClone() {
            return new TeamMember(id, teamId, playerId, role);
        }
    }
}
