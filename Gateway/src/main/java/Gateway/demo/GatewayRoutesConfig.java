package Gateway.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class GatewayRoutesConfig {

    // ApplicationService Route
    @Bean
    public RouterFunction<ServerResponse> applicationServiceRoute() {
        return route(
                path("/api/applications/**"),
                request -> ServerResponse.temporaryRedirect(
                        java.net.URI.create("http://localhost:8083" + request.path())
                ).build()
        );
    }

    // EventService Route
    @Bean
    public RouterFunction<ServerResponse> eventServiceRoute() {
        return route(
                path("/api/events/**"),
                request -> ServerResponse.temporaryRedirect(
                        java.net.URI.create("http://localhost:8082" + request.path())
                ).build()
        );
    }

    // UserService Route
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return route(
                path("/api/users/**"),
                request -> ServerResponse.temporaryRedirect(
                        java.net.URI.create("http://localhost:8081" + request.path())
                ).build()
        );
    }
}
