package io.github.tomatan515.kakoiplugin.characters;

import java.util.UUID;

public class Man extends Character{

    private boolean isCought = false;

    public Man(UUID uuid) {
        super(uuid);
        super.setType(ChType.MAN);
    }

    public boolean isCought()
    {
        return isCought;
    }

    public void setCought(boolean isCought)
    {
        this.isCought = isCought;
    }
}
