package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record DeletePlayerCommand(String id) implements Command<Voidy> {
}
