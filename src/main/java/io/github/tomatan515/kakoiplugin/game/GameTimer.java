package io.github.tomatan515.kakoiplugin.game;


import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
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
            return;
        }

        update();
        time--;


        if (time >= 0 && time <= 5)
        {
            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.playSound(p.getLocation() , Sound.UI_STONECUTTER_SELECT_RECIPE , 1 , 1);
            }
        }
        else if (time < 0 && GameManager.isStarted)
        {
            //男の勝ち
            //逃げ切った男を表示
            //女のキル数を表示
            GameManager.isStarted = false;

            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + "ShowResultが呼び出されました (timer) ");

            showResult();
            this.cancel();
        }
    }

    private void update()
    {
        if (GameManager.getHonestMan().size() <= 0)
        {
            GameManager.isStarted = false;
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + "ShowResultが呼び出されました (update) ");
            showResult();
        }
        for (Player p : Bukkit.getOnlinePlayers())
        {
            //ActionBarの表示
            //残り時間や残り人数など
            String msg = "残り時間:" + timeToMinute(time) + " , " + "残りの男:" + GameManager.getHonestMan().size() + "人" + " , " + "参加人数:" + GameManager.getJoinedPlayers().size();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR , TextComponent.fromLegacyText(msg));
        }
    }

    private String timeToMinute(int second)
    {
        return (( second / 60 ) % 60) + "分" + (second % 60) + "秒";
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
                        player.sendTitle("" , ChatColor.GREEN + "終了！！！" , 0 , 40 , 20);
                        player.playSound(player.getLocation() , Sound.BLOCK_ANVIL_USE , 0.5F , 1);
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
                else if (i == 7)
                {
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        player.sendTitle("" , ChatColor.GREEN + "初期地にリスポーンします・・・。" , 10 , 40 , 10);
                    }
                }
                else if (i > 8)
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
                p.playSound(p.getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
            }

            for (Character ch : GameManager.getJoinedPlayers())
            {
                Player p = Bukkit.getPlayer(ch.getUniqueId());
                if (ch.getType().equals(ChType.MAN))
                {
                    p.sendTitle(ChatColor.RED + "敗北！" , ChatColor.RED + "女の誘惑に勝てた男は一人もいませんでした" , 10 , 40 , 10);
                    p.playSound(p.getLocation() , Sound.ENTITY_DRAGON_FIREBALL_EXPLODE , 0.5F , 1);
                }
                else
                {
                    p.sendTitle(ChatColor.GREEN + "勝利！" , ChatColor.YELLOW + "落とせない男は存在しないようだ" , 10 , 40 , 10);
                    p.playSound(p.getLocation() , Sound.UI_TOAST_CHALLENGE_COMPLETE , 0.5F , 1);
                }
            }
        }
        else
        {
            String str = result.stream().collect(Collectors.joining(ChatColor.GRAY + ","));

            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(KakoiPlugin.PREFIX + str + " " + ChatColor.RESET + "" + ChatColor.GREEN + "が自我を保ち、女の子から断腸の思いで逃げ切りました！" + ChatColor.RESET);
                p.playSound(p.getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
            }

            for (Character ch : GameManager.getJoinedPlayers())
            {
                Player p = Bukkit.getPlayer(ch.getUniqueId());
                if (ch.getType().equals(ChType.MAN))
                {
                    p.sendTitle(ChatColor.GREEN + "勝利！" , ChatColor.YELLOW + "男は女から逃げ切り、秩序は保たれた" , 10 , 40 , 10);
                    p.playSound(p.getLocation() , Sound.UI_TOAST_CHALLENGE_COMPLETE , 0.5F , 1);
                }
                else
                {
                    p.sendTitle(ChatColor.RED + "敗北！" , ChatColor.RED + "女は男を落とせず、秩序は保たれた" , 10 , 40 , 10);
                    p.playSound(p.getLocation() , Sound.ENTITY_DRAGON_FIREBALL_EXPLODE , 0.5F , 1);
                }
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

                player.playSound(player.getLocation() , Sound.ENTITY_CAT_STRAY_AMBIENT , 1 , 1);
            }
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "バグが発生しました。このバグを開発者に報告してください。(GameTimer - 137)");
        }

    }
}
