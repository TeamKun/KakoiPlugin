package io.github.tomatan515.kakoiplugin;

import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.commands.GameCommand;
import io.github.tomatan515.kakoiplugin.commands.ShopCommand;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.ItemManager;
import io.github.tomatan515.kakoiplugin.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class KakoiPlugin extends JavaPlugin {

    public static String PREFIX = ChatColor.DARK_PURPLE + "[囲われプラグイン] " + ChatColor.RESET;
    public static String COMMAND = ChatColor.RED + "コマンドが正しくありません。正しく入力してください。";

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.girlItems.put(ChatColor.YELLOW + "自撮りテイカー" , Material.RED_DYE);
        ItemManager.girlItems.put(ChatColor.YELLOW + "DMデリバー" , Material.BLUE_DYE);
        ItemManager.girlItems.put(ChatColor.YELLOW + "アスレサポーター" , Material.YELLOW_DYE);

        ItemManager.manItems.put(ChatColor.YELLOW + "デマ「KUNさん来たから行ってくる！！」", Material.GREEN_DYE);
        ItemManager.manItems.put(ChatColor.YELLOW + "ヘイト誘導" , Material.LIGHT_BLUE_DYE);
        ItemManager.manItems.put(ChatColor.YELLOW + "お気持ち表明" , Material.LIME_DYE);

        this.getCommand("game").setExecutor(new GameCommand());
        this.getCommand("s").setExecutor(new ShopCommand());
        this.getCommand("shop").setExecutor(new ShopCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinLeftListener() , this);
        Bukkit.getPluginManager().registerEvents(new PlayerKilledListener() , this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener() , this);
        Bukkit.getPluginManager().registerEvents(new ShopListener() , this);
        Bukkit.getPluginManager().registerEvents(new UseItemListener() , this);
        Bukkit.getPluginManager().registerEvents(new ItemDropListener() , this);

        GameManager.typeToClass.put(ChType.MAN , Man.class);
        GameManager.typeToClass.put(ChType.GIRL , Girl.class);
    }
}
