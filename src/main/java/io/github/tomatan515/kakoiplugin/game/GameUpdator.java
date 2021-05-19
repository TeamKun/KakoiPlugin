package io.github.tomatan515.kakoiplugin.game;

import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import org.bukkit.scheduler.BukkitRunnable;

public class GameUpdator extends BukkitRunnable {

    @Override
    public void run() {

        if (GameManager.isStarted)
        {
            girlUpdate();
            manUpdate();
        }
        else
        {
            this.cancel();
        }
    }

    private void girlUpdate()
    {
        for (Character ch : GameManager.getPlayers(ChType.GIRL))
        {
            Girl girl = (Girl) ch;
            girl.addMoney(1);
        }
    }

    private void manUpdate()
    {
        for (Character ch : GameManager.getPlayers(ChType.MAN))
        {
            Man man = (Man) ch;

            if (man.isCought())
            {
                //捕まってたら
            }
            else
            {
                //捕まってなかったら
            }
        }
    }
}
