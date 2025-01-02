package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.viewmodels.LoggedInUserViewModel;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;

public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {

    private final UserRepository repository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public LoginCommandHandler(UserRepository repository, PasswordHasher passwordHasher, JwtService jwtService) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    @Override
    public LoggedInUserViewModel handle(LoginCommand command) {
        var user = repository.findByEmailAddress(command.emailAddress())
                .orElseThrow(() -> new NotFoundException("User"));

        var passwordMatch = passwordHasher.matches(command.password(), user.getPasswordHash());

        if (!passwordMatch) {
            throw new BadRequestException("The password is invalid");
        }

        var token = jwtService.tokenize(user);

        return new LoggedInUserViewModel(
                user.getId(),
                user.getEmailAddress(),
                token
        );
    }
}
