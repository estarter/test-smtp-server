package ch.e3ag.test.smtp.server;

import ch.e3ag.test.smtp.server.smtp.SmtpServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Alexey Merezhin
 * @since 8/25/17
 */
@SpringBootApplication
public class Application {
    @Value("${smtp.port}")
    private int port;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SmtpServer smtpServer() {
        return new SmtpServer(port);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
*/

}
