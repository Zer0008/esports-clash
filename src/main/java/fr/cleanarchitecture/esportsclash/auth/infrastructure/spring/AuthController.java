package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.LoginUserCommand;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommand;
import fr.cleanarchitecture.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {

    private final Pipeline pipeline;

    public AuthController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping(value = "register")
    public ResponseEntity<IdResponse> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        var result = pipeline.send(new RegisterUserCommand(
                registerUserDto.getEmailAddress(),
                registerUserDto.getPassword()));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoggedInUserViewModel> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        var result = pipeline.send(new LoginUserCommand(
                loginUserDto.getEmailAddress(),
                loginUserDto.getPassword()));

        return ResponseEntity.ok(result);
    }
}
