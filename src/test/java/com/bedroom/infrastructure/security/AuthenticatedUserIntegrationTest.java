package com.bedroom.infrastructure.security;

import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.application.security.AuthenticatedUser;
import com.bedroom.application.security.AuthenticatedUserProvider;
import com.bedroom.domain.identity.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = {
                SecurityConfiguration.class,
                SecurityTestKeyConfiguration.class,
                SecurityTestController.class,
                AuthenticatedUserIntegrationTest.TestController.class
        }
)
@AutoConfigureMockMvc
class AuthenticatedUserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationTokenIssuer tokenIssuer;

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Test
    void shouldResolveAuthenticatedUserFromJwt()
            throws Exception {

        UserId userId = UserId.generate();

        String token = tokenIssuer.issue(userId);

        mockMvc.perform(
                        get("/authenticated-user/test")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        result -> assertEquals(
                                userId.value().toString(),
                                result.getResponse().getContentAsString()
                        )
                );
    }

    @RestController
    static class TestController {

        private final AuthenticatedUserProvider
                authenticatedUserProvider;

        TestController(
                AuthenticatedUserProvider authenticatedUserProvider
        ) {
            this.authenticatedUserProvider =
                    authenticatedUserProvider;
        }

        @GetMapping("/authenticated-user/test")
        String authenticatedUser() {

            AuthenticatedUser authenticatedUser =
                    authenticatedUserProvider
                            .getAuthenticatedUser();

            return authenticatedUser
                    .userId()
                    .value()
                    .toString();
        }
    }
}