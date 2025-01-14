package com.core.perabot.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxConfiguration {

//    @Value("${dropbox.api.key}")
//    private String apiKey;
//
//    @Value("${dropbox.api.secret}")
//    private String apiSecret;

    @Value("${dropbox.token}")
    private String accessToken;

    @Bean
    public DbxClientV2 dropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("perabot-shop").build();
        return new DbxClientV2(config, accessToken);
    }
}