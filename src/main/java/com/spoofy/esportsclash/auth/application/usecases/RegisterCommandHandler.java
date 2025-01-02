package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.auth.application.exceptions.EmailAlreadyInUseException;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.models.User;
import com.spoofy.esportsclash.core.domain.viewmodels.IdResponse;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {

    private final UserRepository repository;
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    public RegisterCommandHandler(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public IdResponse handle(RegisterCommand command) {
        var isEmailAddressAlreadyInUse = repository.isEmailAddressAlreadyInUse(command.emailAddress());

        if (isEmailAddressAlreadyInUse) {
            throw new EmailAlreadyInUseException();
        }

        var user = new User(
                UUID.randomUUID().toString(),
                command.emailAddress(),
                passwordHasher.hash(command.clearPassword())
        );

        repository.save(user);

        return new IdResponse(user.getId());
    }
}
