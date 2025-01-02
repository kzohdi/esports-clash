package com.spoofy.esportsclash.player.domain.models;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends BaseModel<Player> {

    @Column
    private String name;

    public Player() {
    }

    public Player(String id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public Player deepClone() {
        return new Player(id, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void rename(String newName) {
        setName(newName);
    }
}
