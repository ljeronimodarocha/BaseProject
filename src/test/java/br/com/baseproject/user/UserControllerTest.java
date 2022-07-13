package br.com.baseproject.user;


import br.com.baseproject.applicationUser.entity.dto.UserPostRequestBody;
import br.com.baseproject.applicationUser.entity.dto.UserReturnPostRequestBody;
import br.com.baseproject.applicationUser.services.ApplicationUserServices;
import br.com.baseproject.util.UtilTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private ApplicationUserServices services;

    @LocalServerPort
    private int port;

    private TestRestTemplate testRestTemplateRoleUser = new TestRestTemplate();

    public UserPostRequestBody USER = UtilTest.criarUsuario();

    @Test
    public void testUserLogin() {
        services.salvarUsuario(USER);
        HttpEntity<UserPostRequestBody> request = new HttpEntity<>(USER);
        ResponseEntity<LinkedHashMap> response = testRestTemplateRoleUser.exchange(UtilTest.createURLWithPort("/login"), HttpMethod.POST, request, LinkedHashMap.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().get("token"));
        assertNotNull(response.getBody().get("exp"));
    }

    @Test()
    public void testCreatuser() {
        ResponseEntity<UserReturnPostRequestBody> exchange =
                testRestTemplateRoleUser.postForEntity(UtilTest.createURLWithPort("/usuarios"), USER, UserReturnPostRequestBody.class);
        assertNotNull(exchange);
        assertNotNull(exchange.getBody());
        assertNotNull(exchange.getBody().getId());
        assertEquals(HttpStatus.CREATED, exchange.getStatusCode());
        assertEquals(USER.getUsername(), exchange.getBody().getUsername());
    }
}
