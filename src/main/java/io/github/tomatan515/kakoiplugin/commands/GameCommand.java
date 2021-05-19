package io.github.tomatan515.kakoiplugin.commands;

import io.github.tomatan515.kakoiplugin.AssignCharacter;
import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.GameTimer;
import io.github.tomatan515.kakoiplugin.game.GameUpdator;
import io.github.tomatan515.kakoiplugin.game.WorldPreparer;
import io.github.tomatan515.kakoiplugin.shop.Shop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender s, Command c,  String l, String[] args) {

        if (args[0].isEmpty()) {
            s.sendMessage(ChatColor.GRAY + KakoiPlugin.PREFIX + KakoiPlugin.COMMAND);
            return false;
        } else if (args[0].equalsIgnoreCase("start")) {

            if (!s.isOp()) {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "このコマンドを実行する権限がありません。");
                return true;
            }

            if (GameManager.isStarted == false && GameManager.getJoinedPlayers().size() > 0 && GameManager.getCount(ChType.GIRL) > 0 && GameManager.getCount(ChType.MAN) > 0 && WorldPreparer.getSpawnLocation() != null)
            {
                GameManager.isStarted = true;

                new GameTimer(315).runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class), 0, 20);
                new GameUpdator().runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class), 0, 20);

                WorldPreparer.onStart();
            }
            else
            {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "プレイヤーの役職の割り振りがまだ完了していないか、ゲームがすでに開始しています。スポーン地点の設定ができているかの確認をしてください。");
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "---------INFO---------");
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "女の子:" + GameManager.getCount(ChType.GIRL));
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "男:" + GameManager.getCount(ChType.MAN));
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "参加人数:" + Bukkit.getOnlinePlayers().size());
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "スポーン地点:" + WorldPreparer.getSpawnLocation());
            }

            return true;

        }
        else if (args[0].equalsIgnoreCase("location") && s instanceof Player)
        {
            WorldPreparer.setSpawnLocation(((Player)s).getLocation());
            s.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "スポーン地点を" + WorldPreparer.getSpawnLocation().toString() + "に設定しました！");
            return true;
        }
        else if (args[0].equalsIgnoreCase("test"))
        {
            if (s.isOp() && s instanceof Player)
            {
                Player p = (Player) s;
                p.getWorld().spawnParticle(Particle.HEART , p.getLocation() , 10 , 1 , 1 , 1 , 10);
            }
        }
        else if (args[0].equalsIgnoreCase("debug"))
        {
            if (s.isOp() && s instanceof Player)
            {
                if (args[1].equalsIgnoreCase("shop"))
                {
                    GameManager.getJoinedPlayers().add(GameManager.makeCharacter(ChType.GIRL , ((Player) s).getUniqueId()));
                    ((Girl)GameManager.getCharacter(((Player) s).getUniqueId())).addMoney(500);

                    Shop shop = new Shop();
                    shop.open(((Player)s));

                }
                else if (args[1].equalsIgnoreCase("man"))
                {
                    GameManager.getJoinedPlayers().add(GameManager.makeCharacter(ChType.MAN , ((Player) s).getUniqueId()));
                }
            }
        }
        return true;
    }
}
