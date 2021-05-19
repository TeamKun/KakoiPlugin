package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {

    @EventHandler
    public void onItemPick (InventoryClickEvent e)
    {
        if (!e.getView().getTitle().contains("Shop") && GameManager.isStarted)
        {
            e.setCancelled(true);
        }
    }
}
