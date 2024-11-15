package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.auth.application.exceptions.EmailAddressAlreadyInUseException;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.model.User;
import com.spoofy.esportsclash.core.domain.viewmodel.IdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterCommandHandler implements Command.Handler<RegisterCommand, IdResponse> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    @Override
    public IdResponse handle(RegisterCommand registerCommand) {
        if (userRepository.isEmailAddressAlreadyInUse(registerCommand.emailAddress())) {
            throw new EmailAddressAlreadyInUseException();
        }

        var user = new User(
                UUID.randomUUID().toString(),
                registerCommand.emailAddress(),
                passwordHasher.hash(registerCommand.password())
        );

        userRepository.save(user);

        return new IdResponse(user.getId());
    }
}
