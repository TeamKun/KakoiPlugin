package io.github.tomatan515.kakoiplugin.commands;

import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.shop.Shop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] args) {

        if (c.getName().equalsIgnoreCase("s") || c.getName().equalsIgnoreCase("shop"))
        {
            if (GameManager.isStarted && s instanceof Player)
            {
                Shop shop = new Shop();

                if (GameManager.getCharacter(((Player) s).getUniqueId()).getType().equals(ChType.MAN) && ((Man)GameManager.getCharacter(((Player) s).getUniqueId())).isCought())
                {
                    return true;
                }

                shop.open((Player) s);
            }
            else
            {
                return true;
            }
        }
        return false;
    }
}
