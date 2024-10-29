package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

public class DeletePlayerDto {
    private String id;

    public DeletePlayerDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
