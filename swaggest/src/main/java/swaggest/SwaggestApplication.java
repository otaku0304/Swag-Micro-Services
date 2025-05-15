package swaggest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "swaggest.Entity")
public class SwaggestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggestApplication.class, args);
    }

}
