package com.bedroom.infrastructure.security;

import com.bedroom.application.identity.port.AuthenticationTokenIssuer;
import com.bedroom.domain.identity.valueobject.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = {
                SecurityConfiguration.class,
                SecurityTestKeyConfiguration.class,
                SecurityTestController.class
        }
)
@AutoConfigureMockMvc
class SecurityFilterChainTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationTokenIssuer tokenIssuer;

    @Test
    void shouldAllowPublicEndpointWithoutAuthentication()
            throws Exception {

        mockMvc.perform(
                        get("/public/test")
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectProtectedEndpointWithoutAuthentication()
            throws Exception {

        mockMvc.perform(
                        get("/protected/test")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAllowProtectedEndpointWithValidJwt()
            throws Exception {

        String token = tokenIssuer.issue(
                UserId.generate()
        );

        mockMvc.perform(
                        get("/protected/test")
                                .header(
                                        "Authorization",
                                        "Bearer " + token
                                )
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectProtectedEndpointWithInvalidJwt()
            throws Exception {

        mockMvc.perform(
                        get("/protected/test")
                                .header(
                                        "Authorization",
                                        "Bearer invalid-token"
                                )
                )
                .andExpect(status().isUnauthorized());
    }

}