package io.github.tomatan515.kakoiplugin.commands;

import com.sun.istack.internal.NotNull;
import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.GameTimer;
import io.github.tomatan515.kakoiplugin.game.GameUpdator;
import io.github.tomatan515.kakoiplugin.game.WorldPreparer;
import javafx.concurrent.Task;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

public class GameCommand implements CommandExecutor {

    public static int timerId;

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command c, @NotNull String l, @NotNull String[] args) {

            if (args[0].isEmpty()) {
                s.sendMessage(ChatColor.GRAY + KakoiPlugin.PREFIX + KakoiPlugin.COMMAND);
                return false;
            }
            else if (args[0].equalsIgnoreCase("start")) {
                GameManager.isStarted = true;

                new GameTimer().runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class), 0, 20);
                new GameUpdator().runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);

                WorldPreparer.onStart();

            }
    }
}
