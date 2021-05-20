package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onManMove (PlayerMoveEvent e)
    {
        if (GameManager.isStarted && GameManager.isManStopped)
        {
            Location fromLoc = e.getFrom();
            Location toLoc = e.getTo();
            double fromX, fromY, fromZ, toX, toY, toZ;

            fromX = fromLoc.getX();
            fromY = fromLoc.getY();
            fromZ = fromLoc.getZ();
            toX = toLoc.getX();
            toY = toLoc.getY();
            toZ = toLoc.getZ();

            Character c = GameManager.getCharacter(e.getPlayer().getUniqueId());

            if (c.getType().equals(ChType.MAN) && !((Man)c).isCought() && !(fromX == toX && fromY == toY && fromZ == toZ))
            {
                e.setCancelled(true);
                e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR , TextComponent.fromLegacyText(ChatColor.YELLOW + "やばい！！！！自撮りから目が離せない！！！動けません！"));
                return;
            }

        }
    }
}
