package io.github.tomatan515.kakoiplugin.characters;

import java.util.UUID;

public class Character {

    private UUID uuid;
    private ChType type;

    private int money = 0;

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

    public int getMoney()
    {
        return money;
    }

    public void addMoney(int addition)
    {
        money += addition;
    }

    public void decMoney (int dec)
    {
        money -= dec;
    }

    public void clearMoney()
    {
        money = 0;
    }
}
