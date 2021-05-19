package io.github.tomatan515.kakoiplugin.characters;

import java.util.UUID;

public class Character {

    private UUID uuid;
    private ChType type;

    public Character(UUID uuid)
    {
        this.uuid = uuid;
    }

    public void setType(ChType type) {
        this.type = type;
    }

    public UUID getUniqueId()
    {
        return uuid;
    }

    public ChType getType()
    {
        return type;
    }
}
