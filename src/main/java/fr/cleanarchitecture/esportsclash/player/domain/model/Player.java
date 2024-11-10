package fr.cleanarchitecture.esportsclash.player.domain.model;

import fr.cleanarchitecture.esportsclash.core.domain.model.BaseEntity;

public class Player extends BaseEntity {
    private String name;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void renamePlayer(String newName) {
        this.name = newName;
    }
}
