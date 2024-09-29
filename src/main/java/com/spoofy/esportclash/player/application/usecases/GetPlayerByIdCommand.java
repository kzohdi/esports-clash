package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommand implements Command<PlayerViewModel> {
    private final String id;

    public GetPlayerByIdCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
