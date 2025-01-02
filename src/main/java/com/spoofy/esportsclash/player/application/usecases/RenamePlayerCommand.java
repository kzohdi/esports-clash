package com.spoofy.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record RenamePlayerCommand(String id, String newName) implements Command<Voidy> {
}
