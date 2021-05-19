package io.github.tomatan515.kakoiplugin;

import io.github.tomatan515.kakoiplugin.commands.GameCommand;
import io.github.tomatan515.kakoiplugin.commands.ShopCommand;
import io.github.tomatan515.kakoiplugin.game.ItemManager;
import io.github.tomatan515.kakoiplugin.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class KakoiPlugin extends JavaPlugin {

    public static String PREFIX = "[囲われプラグイン] ";
    public static String COMMAND = ChatColor.RED + "コマンドが正しくありません。正しく入力してください。";

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.items.put(ChatColor.YELLOW + "自撮りテイカー" , Material.RED_DYE);
        ItemManager.items.put(ChatColor.YELLOW + "DMデリバー" , Material.BLUE_DYE);
        ItemManager.items.put(ChatColor.YELLOW + "アスレサポーター" , Material.YELLOW_DYE);

        this.getCommand("game").setExecutor(new GameCommand());
        this.getCommand("s").setExecutor(new ShopCommand());
        this.getCommand("shop").setExecutor(new ShopCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinLeftListener() , this);
        Bukkit.getPluginManager().registerEvents(new PlayerKilledListener() , this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener() , this);
        Bukkit.getPluginManager().registerEvents(new ShopListener() , this);
        Bukkit.getPluginManager().registerEvents(new UseItemListener() , this);
    }
}
