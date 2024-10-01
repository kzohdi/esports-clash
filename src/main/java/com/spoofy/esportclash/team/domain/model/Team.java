package com.spoofy.esportclash.team.domain.model;

import com.spoofy.esportclash.core.domain.model.BaseEntity;
import com.spoofy.esportclash.player.domain.model.Player;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity<Team> {

    @Column
    private String name;

    @OneToMany(
            mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TeamMember> members;

    public Team() {
    }

    public Team(String id, String name) {
        super(id);
        this.name = name;
        this.members = new HashSet<>();
    }

    private Team(String id, String name, Set<TeamMember> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public Set<TeamMember> getMembers() {
        return members;
    }

    public void addMember(String playerId, Role role) {
        if (isPlayerAlreadyInTeam(playerId)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Player %s is already in team",
                            playerId
                    )
            );
        }

        if (isRoleTaken(role)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Role %s is already taken",
                            role
                    )
            );
        }

        var teamMember = new TeamMember(
                UUID.randomUUID().toString(),
                playerId,
                this.id,
                role
        );

        members.add(teamMember);
    }

    private boolean isPlayerAlreadyInTeam(String playerId) {
        return members
                .stream()
                .anyMatch(member -> member.getPlayerId().equals(playerId));
    }

    private boolean isRoleTaken(Role role) {
        return members
                .stream()
                .anyMatch(member -> member.getRole().equals(role));
    }

    public boolean hasMember(String player1, Role role) {
        return members
                .stream()
                .anyMatch(member -> member.playerId.equals(player1) && member.role.equals(role));
    }

    private boolean isPlayerNotInTeam(String playerId) {
        return members
                .stream()
                .noneMatch(member -> member.getPlayerId().equals(playerId));
    }

    public void removeMember(String playerId) {
        if (isPlayerNotInTeam(playerId)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Player %s not in team",
                            playerId
                    )
            );
        }
        members.removeIf(member -> member.playerId.equals(playerId));
    }

    @Override
    public Team deepClone() {
        return new Team(
                this.id,
                this.name,
                members.stream()
                        .map(BaseEntity::deepClone)
                        .collect(Collectors.toSet())
        );
    }

    @Entity
    @Table(name = "team_members")
    public static class TeamMember extends BaseEntity<TeamMember> {
        @Column(name = "player_id")
        private String playerId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "player_id", insertable = false, updatable = false)
        @MapsId("playerId")
        private Player player;

        @Column
        @Enumerated(EnumType.STRING)
        private Role role;

        @Column(name = "team_id")
        private String teamId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", insertable = false, updatable = false)
        private Team team;

        public TeamMember() {
        }

        public TeamMember(String id, String playerId, String teamId, Role role) {
            super(id);
            this.playerId = playerId;
            this.teamId = teamId;
            this.role = role;
        }

        public String getPlayerId() {
            return playerId;
        }

        public Role getRole() {
            return role;
        }

        public Player getPlayer() {
            return player;
        }

        @Override
        public TeamMember deepClone() {
            return new TeamMember(this.id, this.playerId, this.teamId, this.role);
        }
    }
}
