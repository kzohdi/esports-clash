package com.spoofy.esportclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.player.domain.viewmodel.IdResponse;

public class CreatePlayerCommand implements Command<IdResponse> {
    private final String name;

    public CreatePlayerCommand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
