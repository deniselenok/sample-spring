package delenok.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
@ComponentScan("agileengine.task")
@EnableScheduling
@EnableMongoRepositories("agileengine.task.repository")
@EnableWebMvc
public class Initializer {

    public static void main(String[] args) {
        log.info("Starting the AgileEngine Application");
        SpringApplication.run(Initializer.class, args);
    }
}
