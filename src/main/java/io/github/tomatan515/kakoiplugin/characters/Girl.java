package io.github.tomatan515.kakoiplugin.characters;

import java.util.UUID;

public class Girl extends Character {

    private int killCount = 0;
    private int money = 0;

    public Girl(UUID uuid) {
        super(uuid);
        super.setType(ChType.GIRL);
    }

    public int getKillCount() {
        return killCount;
    }

    public void addKillCount()
    {
        killCount++;
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
}
