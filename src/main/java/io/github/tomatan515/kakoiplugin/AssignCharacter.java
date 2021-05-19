package io.github.tomatan515.kakoiplugin;

import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
}
