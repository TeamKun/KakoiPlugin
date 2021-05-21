package io.github.tomatan515.kakoiplugin.game;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameManager {

    private static List<Character> joinedPlayers = new ArrayList<>();
    public static HashMap<ChType , Class> typeToClass = new HashMap<>();

    public static HashMap<UUID , Boolean> isPlayerRespawning = new HashMap<>();

    public static boolean isStarted = false;

    //自撮りテイカーにより男が止められているか
    public static boolean isManStopped = false;

    //ヘイト誘導が行われているか
    public static boolean isAngryLured = false;

    private static final String[] girlNames =
            {
                    "23zi_51hun" ,
                    "akenn1549"  ,
                    "Arimamiko"  ,
                    "Bro_Catherine" ,
                    "dbk_huuka" ,
                    "gin499" ,
                    "kakikama" ,
                    "kono_a" ,
                    "n0r41nu" ,
                    "norunoru315" ,
                    "Osaaaaa" ,
                    "rosec_pin" ,
                    "suzuki2929" ,
                    "urn1o" ,
                    "Yaci56" ,
                    "YOSHIKO__CHAN__",
                    "ikeko_99" ,
                    "Ns_politan" ,
                    "syakaijin_4869"
            };

    public static List<Character> getJoinedPlayers() {
        return joinedPlayers;
    }

    public static Character getCharacter(UUID uuid)
    {
        for (Character c : joinedPlayers)
        {
            if (c.getUniqueId().equals(uuid))
            {
                return c;
            }
        }
        return null;
    }

    public static int getCount(ChType type)
    {
        int result = 0;
        for (Character ch : joinedPlayers)
        {
            if (ch.getType().equals(type))
            {
                result++;
            }
        }

        return result;
    }

    public static List<Character> getPlayers (ChType type)
    {
        List<Character> players = new ArrayList<>();

        for (Character ch : joinedPlayers)
        {
            if (ch.getType().equals(type))
            {
                players.add(ch);
            }
        }

        return players;
    }

    public static Character makeCharacter(ChType chType , UUID uuid) {
        try
        {
            Character ch = (Character) typeToClass.get(chType).getDeclaredConstructor(UUID.class).newInstance(uuid);

            return ch;
        }
        catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "開発者へこのバグを報告してください。(in GameManager.class 101l)");
            return null;
        }
    }

    public static boolean containsPlayer(UUID uuid)
    {
        for (Character ch : joinedPlayers)
        {
            if (ch.getUniqueId().equals(uuid))
            {
                return true;
            }
        }

        return false;
    }

    //捕まってない男を返す
    public static List<Man> getHonestMan()
    {
        List<Man> players = new ArrayList<>();

        for (Character ch : getPlayers(ChType.MAN))
        {
            if (!((Man)ch).isCought())
            {
                players.add(((Man)ch));
            }
        }

        return players;
    }

    public static String[] getGirlNames() {
        return girlNames;
    }
}
