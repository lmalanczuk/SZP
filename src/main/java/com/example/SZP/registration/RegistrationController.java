package com.example.SZP.registration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("api/v1/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

/*    @GetMapping(path = "confirm")*/
    @GetMapping("api/v1/registration/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
