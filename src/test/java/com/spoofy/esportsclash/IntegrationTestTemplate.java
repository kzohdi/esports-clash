package com.spoofy.esportsclash;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoofy.esportsclash.auth.application.ports.UserRepository;
import com.spoofy.esportsclash.auth.application.services.jwtservice.JwtService;
import com.spoofy.esportsclash.auth.domain.models.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Transactional
public class IntegrationTestTemplate {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    protected String createJwtToken() {
        var user = new User(
                UUID.randomUUID().toString(),
                "intergration-test@spoofy.com",
                ""
        );

        userRepository.save(user);

        return "Bearer " + jwtService.tokenize(user);
    }

    protected void clearDatabaseCache() {
        entityManager.flush();
        entityManager.clear();
    }
}
