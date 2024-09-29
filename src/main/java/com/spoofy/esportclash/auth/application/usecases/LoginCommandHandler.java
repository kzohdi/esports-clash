package com.spoofy.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.domain.viewmodel.LoggedInUserViewModel;
import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;

    public LoginCommandHandler(
            UserRepository repository,
            JwtService jwtService,
            PasswordHasher passwordHasher
    ) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoggedInUserViewModel handle(LoginCommand loginCommand) {
        var user = repository
                .findByEmailAddress(loginCommand.getEmailAddress())
                .orElseThrow(() -> new NotFoundException("User"));

        var match = passwordHasher.match(loginCommand.getPassword(), user.getPasswordHash());

        if (!match) {
            throw new BadRequestException("The password is incorrect");
        }

        var token = jwtService.tokenize(user);
        return new LoggedInUserViewModel(user.getId(), user.getEmailAddress(), token);
    }
}
