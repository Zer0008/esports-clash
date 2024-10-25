package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.player.application.usecases.CreatePlayerCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.DeletePlayerCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.RenamePlayerCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> chamgeNamePlayer(@RequestBody RenamePlayerDto renamePlayerDto, @PathVariable("id") String id) {
        this.pipeline.send(new RenamePlayerCommand(id, renamePlayerDto.getName()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> chamgeNamePlayer(@PathVariable("id") String id) {
        this.pipeline.send(new DeletePlayerCommand(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
