package player;

public class CreatePlayerDto {
    private String name;

    public CreatePlayerDto() {}

    public CreatePlayerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
