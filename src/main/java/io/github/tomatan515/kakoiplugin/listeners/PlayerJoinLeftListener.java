package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.AssignCharacter;
import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.WorldPreparer;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerJoinLeftListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        if (GameManager.isStarted)
        {
            e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "男としてゲームに途中参加します。");
            AssignCharacter.register(ChType.MAN , e.getPlayer().getUniqueId());
            e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "目標：青の装備を着てるプレイヤーを全滅させよ！");
            ((Man)GameManager.getCharacter(e.getPlayer().getUniqueId())).setCought(true);

            manRespawn(e.getPlayer());
        }
    }

    public static void manRespawn(Player player)
    {
        //初期値にリスポーン
        player.teleport(WorldPreparer.getSpawnLocation());
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));

        List<ItemStack> armors = WorldPreparer.getLeatherArmors(Color.BLACK);
        player.getInventory().setChestplate(armors.get(1));
        player.getInventory().setLeggings(armors.get(2));
        player.getInventory().setBoots(armors.get(3));

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        if (GameManager.isStarted)
        {
            e.getPlayer().getInventory().clear();
            AssignCharacter.remove(e.getPlayer().getUniqueId());
        }
    }
}
