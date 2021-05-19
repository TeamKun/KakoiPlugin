package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.ItemManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class useItemListener implements Listener {

    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent e)
    {
        if (GameManager.isStarted)
        {
            Player p = e.getPlayer();
            ItemStack item = e.getItem();

            if (ItemManager.items.containsKey(item.getItemMeta().getDisplayName()))
            {
                Material mtr = item.getType();

                switch (mtr)
                {
                    case RED_DYE:
                        for (Man man : GameManager.getHonestMan())
                        {
                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    if ()
                                }
                            }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                        }
                        break;
                    case BLUE_DYE:
                        break;
                    case YELLOW_DYE:
                        break;
                }
            }
        }
        else
        {
            return;
        }
    }

    private void useItem()
    {

    }
}
