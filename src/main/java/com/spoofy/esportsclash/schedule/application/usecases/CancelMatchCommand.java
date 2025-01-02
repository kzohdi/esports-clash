package com.spoofy.esportsclash.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record CancelMatchCommand(String matchId) implements Command<Voidy> {
}
