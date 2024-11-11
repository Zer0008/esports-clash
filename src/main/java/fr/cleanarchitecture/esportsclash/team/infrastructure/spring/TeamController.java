package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

import an.awesome.pipelinr.Pipeline;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.team.application.usecases.CreateTeamCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
