package io.github.tomatan515.kakoiplugin;

import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class AssignCharacter {

    public static void register (ChType type , UUID uuid)
    {
        Character ch = GameManager.makeCharacter(type , uuid);

        if (!GameManager.containsPlayer(uuid))
        {
            GameManager.getJoinedPlayers().add(ch);
        }
    }

    public static void remove(UUID uuid)
    {
        //fromをそのチームから抜けさせる。
        //エラー用の処理↓
        //一人しかいない役職からは移動できない
        if (GameManager.getCharacter(uuid) != null)
        {
            Character c = GameManager.getCharacter(uuid);
            GameManager.getJoinedPlayers().remove(c);
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + Bukkit.getPlayer(uuid).getName() + "を削除しました。");
        }
    }

    public static void specificGirl()
    {

        GameManager.getJoinedPlayers().clear();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (Arrays.asList(GameManager.getGirlNames()).contains(p.getName()))
            {
                Girl girl = new Girl(p.getUniqueId());
                GameManager.getJoinedPlayers().add(girl);
                continue;
            }
            else
            {
                Man man = new Man(p.getUniqueId());
                GameManager.getJoinedPlayers().add(man);
            }
        }

        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "プレイヤーを割り振りました！");
        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.LIGHT_PURPLE + "女:" + GameManager.getCount(ChType.GIRL) + "," +
                ChatColor.BLUE + "男:" +ChatColor.WHITE + GameManager.getCount(ChType.MAN));
    }

    public static void randomize(int manCount , int girlCount)
    {
        //ランダムにプレイヤーを割り振りする。
        if ((manCount + girlCount) != Bukkit.getOnlinePlayers().size())
        {
            Bukkit.getServer().getConsoleSender().sendMessage(KakoiPlugin.PREFIX + KakoiPlugin.COMMAND);
            return;
        }
        GameManager.getJoinedPlayers().clear();

        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (GameManager.getCount(ChType.MAN) < manCount)
            {
                Man man = new Man(p.getUniqueId());
                GameManager.getJoinedPlayers().add(man);
            }
            else
            {
                Girl girl = new Girl(p.getUniqueId());
                GameManager.getJoinedPlayers().add(girl);
            }
        }

        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "プレイヤーを割り振りました！");
        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.LIGHT_PURPLE + "女:" + GameManager.getCount(ChType.GIRL) + "," +
                ChatColor.BLUE + "男:" +ChatColor.WHITE + GameManager.getCount(ChType.MAN));
    }
}
