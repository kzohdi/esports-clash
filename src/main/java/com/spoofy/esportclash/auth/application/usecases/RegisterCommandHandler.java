package com.spoofy.esportclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportclash.auth.application.exceptions.EmailAddressUnavailableException;
import com.spoofy.esportclash.auth.application.ports.UserRepository;
import com.spoofy.esportclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportclash.auth.domain.model.User;
import com.spoofy.esportclash.core.domain.viewmodel.IdResponse;

import java.util.UUID;

public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {

    private final UserRepository repository;
    private final PasswordHasher passwordHasher;

    public RegisterCommandHandler(UserRepository repository, PasswordHasher passwordHasher) {
        this.repository = repository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public IdResponse handle(RegisterCommand registerCommand) {
        var isEmailAddressAvailable = repository.isEmailAddressAvailable(registerCommand.getEmailAddress());
        if (!isEmailAddressAvailable) {
            throw new EmailAddressUnavailableException();
        }

        var user = new User(
                UUID.randomUUID().toString(),
                registerCommand.getEmailAddress(),
                passwordHasher.hash(registerCommand.getPassword()));

        repository.save(user);

        return new IdResponse(user.getId());
    }
}
