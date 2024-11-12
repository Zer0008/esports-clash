package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.DeleteTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teams")
public class TeamController {

    private final Pipeline pipeline;

    public TeamController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping()
    public ResponseEntity<IdResponse> createTeam(@RequestBody CreateTeamDto createTeamDto) {
        var result = this.pipeline.send(new CreateTeamCommand(createTeamDto.getName()));
        return new ResponseEntity<>(new IdResponse(result.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("teamId") String teamId) {
        this.pipeline.send(new DeleteTeamCommand(teamId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add-player-to-team")
    public ResponseEntity<Void> addPlayerToTeam(
            @RequestBody AddPlayerToTeamDto addPlayerToTeamDto) {
        this.pipeline.send(
                new AddPlayerToTeamCommand(
                        addPlayerToTeamDto.getPlayerId(),
                        addPlayerToTeamDto.getTeamId(),
                        addPlayerToTeamDto.getRole()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-player-from-team")
    public ResponseEntity<Void> removePlayerFromTeam(
            @RequestBody RemovePlayerFromTeamDto removePlayerFromTeamDto) {
        this.pipeline.send(
                new RemovePlayerFromTeamCommand(
                        removePlayerFromTeamDto.getTeamId(),
                        removePlayerFromTeamDto.getPlayerId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
