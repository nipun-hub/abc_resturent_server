package com.project.abc.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ServerConfig {

    @Value("${server.secret_key:VJ64Ay4PpceNAAx61MzkCLtbg}")
    private String secretKey = "VJ64Ay4PpceNAAx61MzkCLtbg";

    @Value("${server.token_time:36000}")
    private int tokenExpireTime = 36000;

    @Value("${server.app_url:localhost}")
    private String appURL = "localhost";

}
