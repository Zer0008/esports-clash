package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.player.application.usecases.CreatePlayerCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final Pipeline pipeline;

    public PlayerController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping()
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        var result = this.pipeline.send(new CreatePlayerCommand(createPlayerDto.getName()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
