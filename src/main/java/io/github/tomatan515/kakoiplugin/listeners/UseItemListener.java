package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class UseItemListener implements Listener {

    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent e)
    {
        if (GameManager.isStarted && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
        {
            ItemStack item = e.getItem();

            if (ItemManager.items.containsKey(item.getItemMeta().getDisplayName()))
            {
                Material mtr = item.getType();

                if (GameManager.getCharacter(e.getPlayer().getUniqueId()).getType().equals(ChType.MAN))
                {
                    e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "男には使えない代物だ。。。");
                    return;
                }

                useItem(e);

                switch (mtr)
                {
                    case RED_DYE:
                        //自撮りテイカー
                        new BukkitRunnable()
                        {
                            int i = 0;

                            @Override
                            public void run()
                            {
                                if (!GameManager.isStarted)
                                {
                                    this.cancel();
                                    return;
                                }

                                if (GameManager.isManStopped)
                                {
                                    e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "他の人がすでに自撮りを公開していたため、あまりバズらなかった。。。");
                                    this.cancel();
                                    return;
                                }

                                if (i == 0)
                                {
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + e.getPlayer().getName() + "の自撮りがTWITTERに公開された！！！");
                                    }
                                }
                                else if (i > 0 && i < 5)
                                {
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "あと" + (5 - i) + "秒後にはTWITTERをみるために" + ChatColor.GREEN + "男が" + ChatColor.YELLOW + "3秒間スタックします！");
                                    }
                                }
                                else if (i == 5)
                                {
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "男は自撮りを見るためにスマホを開いている！！！");
                                    }
                                    GameManager.isManStopped = true;
                                }
                                else if (i >= 8)
                                {
                                    for (Man man : GameManager.getHonestMan()) {
                                        Random random = new Random();

                                        Player p = Bukkit.getPlayer(man.getUniqueId());

                                        if (random.nextInt(2) == 0) {
                                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + p.getName() + "の自撮り、思ってたんと違ってたわ。は～あ。無駄な時間を過ごしちまったぜ！！！");
                                        } else {
                                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + p.getName() + "の自撮り意外に可愛かったな。これは推せる！！！同担拒否！！！");
                                        }
                                    }
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "男は動けるようになった！！");
                                    }
                                    GameManager.isManStopped = false;
                                }
                            }
                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                        break;
                    case BLUE_DYE:
                        //DMデリバー
                        Random random = new Random();
                        //ランダムで死んでない男を抽出
                        Man man = GameManager.getHonestMan().get(random.nextInt(GameManager.getHonestMan().size()));
                        Player p = Bukkit.getPlayer(man.getUniqueId());

                        new BukkitRunnable()
                        {
                            int i = 0;

                            @Override
                            public void run()
                            {
                                if (!GameManager.isStarted)
                                {
                                    this.cancel();
                                    return;
                                }

                                if (i == 0)
                                {
                                    for (Player pl : Bukkit.getOnlinePlayers())
                                    {
                                        pl.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + p.getName() + "が女の子とDMのやり取りをしていたらリークされてしまった！！！");
                                        pl.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "ツイッターのアンチが騒ぎ出した！！2秒間発光します。");
                                    }
                                }
                                else if (i == 2)
                                {
                                    p.removePotionEffect(PotionEffectType.GLOWING);
                                    this.cancel();
                                }
                                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING , Integer.MAX_VALUE , 3 , false , false));
                                i++;
                            }

                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                        break;
                    case YELLOW_DYE:
                        //アスレサポーター
                        e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "アスレサポーターを使用した！！！");
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP , 100 , 3 , false , false));
                        break;
                }
            }
        }
        else
        {
            return;
        }
    }

    private void useItem(PlayerInteractEvent e)
    {
        //アイテムを減らす
        e.getItem().setAmount(e.getItem().getAmount() - 1);
        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer() + "のアイテムを一つ使用しました。");

        e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getItem().getItemMeta().getDisplayName() + "を使用しました！！");

    }
}
