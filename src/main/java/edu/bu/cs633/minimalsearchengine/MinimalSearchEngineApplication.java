package edu.bu.cs633.minimalsearchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MinimalSearchEngineApplication {
    public static void main( String[] args ) {
        SpringApplication.run(MinimalSearchEngineApplication.class);
    }
}
