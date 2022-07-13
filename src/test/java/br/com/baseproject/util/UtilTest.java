package br.com.baseproject.util;


import br.com.baseproject.applicationUser.entity.dto.UserPostRequestBody;

import java.math.BigDecimal;

public class UtilTest {

    public static UserPostRequestBody criarUsuario() {
        UserPostRequestBody usuario = new UserPostRequestBody();
        usuario.setUsername("lucas");
        usuario.setPassword("123");
        return usuario;
    }
    public static String createURLWithPort(String uri) {
        return "http://localhost:3000"  + uri;
    }
}
