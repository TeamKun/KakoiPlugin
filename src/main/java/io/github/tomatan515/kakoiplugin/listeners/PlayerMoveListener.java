package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onManMove (PlayerMoveEvent e)
    {
        if (GameManager.isStarted)
        {

        }
    }
}
