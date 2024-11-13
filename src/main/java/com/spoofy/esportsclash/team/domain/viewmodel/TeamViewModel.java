package com.spoofy.esportsclash.team.domain.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamViewModel {
    private String id;
    private String name;
    private List<TeamMemberViewModel> members;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMemberViewModel {
        private String id;
        private String playerId;
        private String playerName;
        private String role;
    }
}
