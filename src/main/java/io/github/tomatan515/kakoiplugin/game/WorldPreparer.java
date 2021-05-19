package io.github.tomatan515.kakoiplugin.game;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.Character;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class WorldPreparer {

    public static void respawn(Character c)
    {

    }

    public static void end()
    {
        if (GameManager.isStarted)
        {

        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "ゲームが開始していません。ゲームを終了できませんでした。");

            //isstarted = false
            return;
        }
    }

    public static void onStart()
    {
        //メッセージ
        //装備を配る
        //ショップ等の説明
    }
}
