package com.spoofy.esportsclash.schedule.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;
import com.spoofy.esportsclash.schedule.domain.models.Moment;

import java.time.LocalDate;

public record OrganizeMatchCommand(LocalDate date, Moment moment, String firstTeamId,
                                   String secondTeamId) implements Command<IdResponse> {
}
