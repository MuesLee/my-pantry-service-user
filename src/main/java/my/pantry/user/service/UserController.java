package my.pantry.user.service;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import java.security.Principal;

@Secured("isAuthenticated()")
@Controller("/user")
public class UserController {

    @Get("/")
    String index(Principal principal) {
        return principal.getName();
    }
}