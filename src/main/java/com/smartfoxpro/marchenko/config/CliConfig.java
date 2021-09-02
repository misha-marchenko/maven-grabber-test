package com.smartfoxpro.marchenko.config;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CliConfig {
    @Bean
    public Options initOptions() {
        return new Options()
                .addOption("h", "help", false, "Покажи помощь")
                .addOption("a","artifactId",true,"Получить артефакт")
                .addOption("g","groupId",true,"Получить групповой Id");

    }
}
