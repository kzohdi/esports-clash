package com.spoofy.esportsclash.player.domain.model;

import com.spoofy.esportsclash.core.domain.model.BaseModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player extends BaseModel {
    private String name;

    public Player(String id, String name) {
        super(id);
        this.name = name;
    }

    public void rename(String name) {
        this.name = name;
    }
}
