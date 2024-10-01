package com.spoofy.esportclash.player.domain.model;

import com.spoofy.esportclash.core.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {

    @Column
    private String name;

    public Player() {
    }

    public Player(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        this.name = newName;
    }
}
