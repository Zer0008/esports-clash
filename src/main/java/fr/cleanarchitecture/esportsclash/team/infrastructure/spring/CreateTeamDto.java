package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

public class CreateTeamDto {
    private String name;

    public CreateTeamDto() {}

    public CreateTeamDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
