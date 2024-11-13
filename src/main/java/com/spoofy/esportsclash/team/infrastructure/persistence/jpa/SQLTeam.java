package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLEntity;
import com.spoofy.esportsclash.player.infrastructure.persistence.jpa.SQLPlayer;
import com.spoofy.esportsclash.team.domain.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SQLTeam extends SQLEntity {
    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "team",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<SQLTeamMember> members;

    @Entity
    @Table(name = "team_members")
    @Getter
    @Setter
    static class SQLTeamMember extends SQLEntity {

        @Column(name = "player_id")
        private String playerId;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "player_id", insertable = false, updatable = false)
        private SQLPlayer player;

        @Column
        @Enumerated(EnumType.STRING)
        private Role role;

        @Column(name = "team_id")
        private String teamId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "team_id", insertable = false, updatable = false)
        private SQLTeam team;
    }
}
