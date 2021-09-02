package com.smartfoxpro.marchenko;

import com.smartfoxpro.marchenko.config.CliConfig;
import com.smartfoxpro.marchenko.service.MavenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Slf4j
@SpringBootApplication
public class MavenGrabberMarchenkoApplication {
    @Autowired
    private MavenService mavenService;
    @Autowired
    private Options options;

    public static void main(String[] args) {

        SpringApplication.run(MavenGrabberMarchenkoApplication.class, args);

    }
    @EventListener(ApplicationReadyEvent.class)
    public void initReader() throws IOException {
        new HelpFormatter().printHelp("Maven Grabber", options);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!br.ready()) {
            log.info("Enter -command <argument>: ");
            mavenService.parseArgument(br.readLine().split(" "));
        }

    }
}
