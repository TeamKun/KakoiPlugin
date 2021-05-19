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
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender s, Command c,  String l, String[] args) {

        if (!s.isOp()) {
            s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "" + ChatColor.BOLD + "You don't have enough permission to perform this command.");
            return true;
        }

        if (args[0].isEmpty()) {
            s.sendMessage(ChatColor.GRAY + KakoiPlugin.PREFIX + KakoiPlugin.COMMAND);
            return false;
        } else if (args[0].equalsIgnoreCase("start")) {

            if (!s.isOp()) {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "このコマンドを実行する権限がありません。");
                return true;
            }
            if (args[1].isEmpty())
            {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "第二引数に時間を指定してください。");
                return true;
            }

            if (GameManager.isStarted == false && GameManager.getJoinedPlayers().size() > 0 && GameManager.getCount(ChType.GIRL) > 0 && GameManager.getCount(ChType.MAN) > 0 && WorldPreparer.getSpawnLocation() != null)
            {
                try
                {
                    GameManager.isStarted = true;

                    new GameTimer(Integer.parseInt(args[1])).runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class), 0, 20);
                    new GameUpdator().runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class), 0, 20);
                    WorldPreparer.onStart();
                }
                catch (NumberFormatException e)
                {
                    GameManager.isStarted = false;
                    s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "第二引数には数値を入力してください。");
                }
            }
            else
            {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "プレイヤーの役職の割り振りがまだ完了していないか、ゲームがすでに開始しています。スポーン地点の設定ができているかの確認をしてください。");
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
            s.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "スポーン地点を設定しました！");
            return true;
        }
        else if (args[0].equalsIgnoreCase("warihuri"))
        {
            if (GameManager.isStarted)
            {
                return false;
            }
            AssignCharacter.specificGirl();
            s.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "プレイヤーを割り振りました！");
            return true;
        }
        else if (args[0].equalsIgnoreCase("warifuri") && args[1] != null)
        {
            try
            {
                AssignCharacter.randomize(Integer.parseInt(args[1]) , Bukkit.getOnlinePlayers().size() - Integer.parseInt(args[1]));
            }
            catch (NumberFormatException e)
            {
                s.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "第二引数には数値を入力してください。");
            }
        }
        else if (args[0].equalsIgnoreCase("test"))
        {
            if (s.isOp() && s instanceof Player)
            {
                Player p = (Player) s;
                p.getWorld().spawnParticle(Particle.EXPLOSION_HUGE , p.getLocation() , 2 , 1 , 1 , 1, 1);
            }
        }

        return true;
    }
}
