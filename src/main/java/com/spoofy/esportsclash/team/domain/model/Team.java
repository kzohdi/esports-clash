package com.spoofy.esportsclash.team.domain.model;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Team extends BaseModel<Team> {

    private String name;
    private Set<TeamMember> members;

    public Team(String id, String name) {
        super(id);
        this.name = name;
        this.members = new HashSet<>();
    }

    private Team(String id, String name, Set<TeamMember> members) {
        this(id, name);
        this.members = members;
    }

    public void addMember(String playerId, Role role) {
        if (hasMember(playerId)) {
            throw new BadRequestException("Player is already in the team");
        }

        if (hasMember(role)) {
            throw new BadRequestException("Role is already taken");
        }

        var member = new TeamMember(
                UUID.randomUUID().toString(),
                playerId,
                role);

        members.add(member);
    }

    public void removeMember(String playerId) {
        if (!hasMember(playerId)) {
            throw new BadRequestException("Player is not in the team");
        }
        members.removeIf(member -> member.playerId.equals(playerId));
    }

    public boolean hasMember(String playerId, Role role) {
        return members.stream()
                .anyMatch(member -> member.playerId.equals(playerId) && member.role == role);
    }

    public boolean hasMember(String playerId) {
        return members.stream()
                .anyMatch(member -> member.playerId.equals(playerId));
    }

    public boolean hasMember(Role role) {
        return members.stream()
                .anyMatch(member -> member.role == role);
    }

    @Override
    public Team deepClone() {
        return new Team(
                id,
                name,
                members.stream()
                        .map(TeamMember::deepClone)
                        .collect(Collectors.toSet()));
    }

    @Getter
    @Setter
    public static class TeamMember extends BaseModel<TeamMember> {
        private final String playerId;
        private final Role role;

        public TeamMember(String id, String playerId, Role role) {
            super(id);
            this.playerId = playerId;
            this.role = role;
        }

        @Override
        public TeamMember deepClone() {
            return new TeamMember(id, playerId, role);
        }
    }
}
