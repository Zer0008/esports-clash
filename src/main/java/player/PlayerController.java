package player;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final CreatePlayerUseCase createPlayerUseCase;

    public PlayerController(CreatePlayerUseCase createPlayerUseCase) {
        this.createPlayerUseCase = createPlayerUseCase;
    }

    @PostMapping()
    public ResponseEntity<IdResponse> createPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        var result = this.createPlayerUseCase.execute(createPlayerDto.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
