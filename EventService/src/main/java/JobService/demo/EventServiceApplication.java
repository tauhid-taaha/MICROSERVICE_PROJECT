// File: EventServiceApplication.java
package EventService.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventServiceApplication {  // matches filename
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}
