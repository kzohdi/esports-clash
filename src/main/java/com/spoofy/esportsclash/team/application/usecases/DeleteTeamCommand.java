package com.spoofy.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Voidy;

public record DeleteTeamCommand(String id) implements Command<Voidy> {
}
