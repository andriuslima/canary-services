package xyz.andriuslima.canaryviewapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CanaryViewApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanaryViewApiApplication.class, args);
    }

}
