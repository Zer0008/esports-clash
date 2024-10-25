package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

public class RenamePlayerDto {
    private String name;

    public RenamePlayerDto() {}

    public RenamePlayerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
