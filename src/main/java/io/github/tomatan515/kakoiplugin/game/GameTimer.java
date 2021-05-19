package io.github.tomatan515.kakoiplugin.game;

import com.sun.xml.internal.fastinfoset.util.PrefixArray;
import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameTimer extends BukkitRunnable {

    private int time;
    public GameTimer(int time)
    {
        this.time = time;
    }

    @Override
    public void run() {

        if (!GameManager.isStarted)
        {
            this.cancel();
        }

        update();
        time--;

        if (time < 0)
        {
            //男の勝ち
            //逃げ切った男を表示
            //女のキル数を表示
            showResult();
            this.cancel();
        }
    }

    private void update()
    {
        for (Player p : Bukkit.getOnlinePlayers())
        {
            //ActionBarの表示
            //残り時間や残り人数など
            String msg = "残り時間:" + time + " , " + "残りの男:" + GameManager.getHonestMan().size() + "人" + " , " + "参加人数:" + GameManager.getJoinedPlayers().size();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR , TextComponent.fromLegacyText(msg));
        }

        if (GameManager.getHonestMan().size() <= 0)
        {
            showResult();
        }
    }

    public void addTime()
    {
        time++;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public void showResult()
    {
        new BukkitRunnable()
        {
            int i = 0;
            @Override
            public void run()
            {
                if (i == 0)
                {
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        player.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "終了！！！");
                    }
                }
                else if (i == 3)
                {
                    showMen();
                }
                else if (i == 6)
                {
                    rankingGirl();
                }
                else if (i > 6)
                {
                    WorldPreparer.end();
                    this.cancel();
                }

                i++;
            }

        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
    }

    private void showMen()
    {
        List<String> result = new ArrayList<>();
        //逃げ切った人一覧の表示
        for (Character ch : GameManager.getPlayers(ChType.MAN))
        {
            Man man = (Man) ch;
            //逃げ切ったら
            if (!man.isCought())
            {
                String name = Bukkit.getPlayer(ch.getUniqueId()).getName();
                result.add(ChatColor.YELLOW + "" + ChatColor.BOLD +  name);
            }
        }

        if (result.size() == 0)
        {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "" + ChatColor.BOLD + "逃げ切れた男は誰一人おらず、全員女のとりこになりました。" + ChatColor.RESET);
            }
        }
        else
        {
            String str = result.stream().collect(Collectors.joining(ChatColor.GRAY + ","));

            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(KakoiPlugin.PREFIX + str + " " + ChatColor.RESET + "" + ChatColor.GREEN + "が自我を保ち、女の子から断腸の思いで逃げ切りました！" + ChatColor.RESET);
            }
        }
    }

    private void rankingGirl()
    {
        Girl mvp = null;
        int mvpKill = 0;

        for (Character ch : GameManager.getPlayers(ChType.GIRL))
        {
            Girl girl = (Girl) ch;

            if (girl.getKillCount() >= mvpKill)
            {
                mvpKill = girl.getKillCount();
                mvp = girl;
            }
        }

        if (mvp != null)
        {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                player.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "" + ChatColor.BOLD + "一番男を落とした女の子は");
                player.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "" + ChatColor.BOLD + Bukkit.getPlayer(mvp.getUniqueId()).getName()
                + ChatColor.RESET + "で" + ChatColor.GREEN + ChatColor.BOLD + mvp.getKillCount() + "人落としました！！！");
            }
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "バグが発生しました。このバグを開発者に報告してください。(GameTimer - 137)");
        }

    }
}
