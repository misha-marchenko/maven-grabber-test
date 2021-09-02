package com.smartfoxpro.marchenko.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfoxpro.marchenko.entity.MavenEntity;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class MavenService {
    @Autowired
    private Options options;

    public void parseArgument(String[] args) {
        try {
            CommandLineParser cliParser = new DefaultParser();
            CommandLine cmdLine = cliParser.parse(options, args);
            if (cmdLine.hasOption("a")) {
                System.out.println("We have artifactId " + cmdLine.getOptionValue("a"));
                getArtifactId(cmdLine.getOptionValue("a"));
            }
            if (cmdLine.hasOption("g")) {
                System.out.println("We have groupId " + cmdLine.getOptionValue("g"));
                getArtifactId(cmdLine.getOptionValue("g"));
            }
        } catch (ParseException e) {
            e.getMessage();
        }
    }

    public void getArtifactId(String artifactId) {
        String url = "https://search.maven.org/solrsearch/select?q=a:" + artifactId + "+AND+p:jar&rows=20&wt=json";

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(url,
                HttpMethod.GET, null, String.class);

        String result = response.getBody();

        System.out.println(result);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);
            String jsonArtifactId = jsonNode
                    .get("response")
                    .get("docs")
                    .get(0)
                    .get("a")
                    .asText();
            String jsonGroupId = jsonNode
                    .get("response")
                    .get("docs")
                    .get(0)
                    .get("g")
                    .asText();
            MavenEntity mavenEntity = new MavenEntity();
            mavenEntity.setArtifactId(jsonArtifactId);
            mavenEntity.setGroupId(jsonGroupId);
            String jsonInString = objectMapper.writeValueAsString(mavenEntity);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
    }
}
