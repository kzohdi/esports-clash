package com.spoofy.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import com.spoofy.esportsclash.auth.application.port.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import com.spoofy.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginCommandHandler implements Command.Handler<LoginCommand, LoggedInUserViewModel> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    @Override
    public LoggedInUserViewModel handle(LoginCommand loginCommand) {
        var user = userRepository.findByEmailAddress(loginCommand.emailAddress())
                .orElseThrow(() -> new NotFoundException("User"));

        var match = passwordHasher.match(loginCommand.password(), user.getPasswordHash());

        if (!match) {
            throw new BadRequestException("The password is incorrect");
        }

        return new LoggedInUserViewModel(
                user.getId(),
                user.getEmailAddress(),
                jwtService.tokenize(user)
        );
    }
}
