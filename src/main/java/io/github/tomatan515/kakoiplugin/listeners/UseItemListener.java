package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.ItemManager;
import org.bukkit.*;
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
        if (GameManager.isStarted && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && e.getItem() != null)
        {
            ItemStack item = e.getItem();

            if (ItemManager.girlItems.containsKey(item.getItemMeta().getDisplayName()))
            {
                Material mtr = item.getType();

                if (GameManager.getCharacter(e.getPlayer().getUniqueId()).getType().equals(ChType.MAN))
                {
                    e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "男には使えない代物だ。。。");
                    e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                    return;
                }

                useItem(e);

                switch (mtr)
                {
                    case RED_DYE:
                        //自撮りテイカー
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "自撮りテイカーが使用されました");
                        if (GameManager.isManStopped)
                        {
                            e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "他の人がすでに自撮りを公開していたため、あまりバズらなかった。。。");
                            e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_SHEEP_AMBIENT , 1 , 1);

                            return;
                        }

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
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + e.getPlayer().getName() + "の自撮りがTWITTERに公開された！！！");
                                        p.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                                    }
                                }
                                else if (i > 0 && i < 5)
                                {
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendTitle("" , ChatColor.YELLOW + "あと" + (5 - i) + "秒で男が3秒間スタックします！" , 0 , 20 , 0);
                                        p.playSound(p.getLocation() , Sound.UI_STONECUTTER_SELECT_RECIPE , 1 , 1);
                                    }
                                }
                                else if (i == 5)
                                {
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "男は" + e.getPlayer().getName() + "の自撮りを見るために止まらなければならない！！！");
                                        p.playSound(p.getLocation() , Sound.ENTITY_ENDER_DRAGON_SHOOT , 0.5F , 1);
                                    }
                                    GameManager.isManStopped = true;
                                }
                                else if (i >= 8)
                                {
                                    for (Man man : GameManager.getHonestMan()) {
                                        Random random = new Random();

                                        Player p = Bukkit.getPlayer(man.getUniqueId());

                                        /**
                                         * if (random.nextInt(2) == 0) {
                                         *      p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer().getName()  + "の自撮り、思ってたんと違ってたわ。は～あ。無駄な時間を過ごしちまったぜ！！！");
                                         * } else {
                                         *      p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer().getName() + "の自撮り意外に可愛かったな。これは推せる！！！同担拒否！！！");
                                         * }
                                         */
                                    }
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendTitle("" , ChatColor.GREEN + "自撮りを堪能し、男は動けるようになった！" , 0 , 20 , 10);
                                        p.playSound(p.getLocation() , Sound.UI_STONECUTTER_TAKE_RESULT , 1 , 1);
                                    }
                                    GameManager.isManStopped = false;

                                    this.cancel();
                                }

                                i++;
                            }
                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);

                        break;
                    case BLUE_DYE:
                        //DMデリバー
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "DMデリバーが使用されました");
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
                                        pl.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                                        pl.sendTitle("" , ChatColor.GREEN + "ツイッターのキッズが騒ぎ出した！！" + p.getName() + "は5秒間発光します。" , 0, 40 , 10);

                                    }

                                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING , 100 , 3 , false , false));
                                }
                                else if (i > 2)
                                {
                                    this.cancel();
                                }
                                i++;
                            }

                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                        break;
                    case YELLOW_DYE:
                        //アスレサポーター
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "アスレサポーターが使用されました");
                        e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.BLOCK_ANVIL_USE , 0.5F , 1);
                        e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "アスレサポーターを使用した！！！");
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP , 100 , 4 , false , false));
                        break;
                }
            }
            else if (ItemManager.manItems.containsKey(item.getItemMeta().getDisplayName()))
            {
                Material mtr = item.getType();

                if (GameManager.getCharacter(e.getPlayer().getUniqueId()).getType().equals(ChType.GIRL))
                {
                    e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "女には使えない代物だ。。。");
                    e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                    return;
                }

                useItem(e);

                switch (mtr)
                {
                    case LIGHT_BLUE_DYE:
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "ヘイト誘導が起こりました。");

                        if (GameManager.isAngryLured)
                        {
                            e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.YELLOW + "他の人がすでに炎上しているため、炎は上がらなかった。。。");
                            e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                            return;
                        }

                        Random random = new Random();
                        //ランダムで死んでない男を抽出
                        Man man = GameManager.getHonestMan().get(random.nextInt(GameManager.getHonestMan().size()));
                        Player pl = Bukkit.getPlayer(man.getUniqueId());

                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + e.getPlayer().getName() + "はヘイト誘導をした！！");
                            pl.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + ChatColor.BOLD + "矛先→" + pl.getName());
                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + pl.getName() + "は悪影響を被った！");
                        }

                        pl.addPotionEffect(new PotionEffect(PotionEffectType.SLOW , 100 , 5 , false , false));
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING , 100 , 3 , false , false));

                        new BukkitRunnable()
                        {
                            int i = 0;

                            @Override
                            public void run()
                            {
                                GameManager.isAngryLured = true;

                                if (!GameManager.isStarted)
                                {
                                    this.cancel();
                                    return;
                                }
                                else if (i > 10)
                                {
                                    GameManager.isAngryLured = false;
                                    this.cancel();
                                }

                                i++;
                            }

                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);

                        break;
                    case LIME_DYE:
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "お気持ち表明されました。");

                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer().getName() + "がお気持ち表明した！！！");
                            p.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                        }

                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE , 60 , 7 , false , false));

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
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.sendMessage(ChatColor.DARK_PURPLE + "<" + e.getPlayer().getName() + "> " + "この度は本当に申し訳ありませんでした。");
                                        p.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                                    }
                                }
                                else if (i == 2)
                                {
                                    Random rand = new Random();
                                    int j = rand.nextInt(3);

                                    if (j == 0)
                                    {
                                        e.getPlayer().sendTitle("" , ChatColor.DARK_PURPLE + "この通り頭を丸めさせていただきました・・・。", 10 , 40 , 10);
                                        e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                                    }
                                    else if (j == 1)
                                    {
                                        e.getPlayer().sendTitle("" , ChatColor.DARK_PURPLE +  "だって。。。おれ別に悪くないし！！！仕方ないやろ！！", 10 , 40 , 10);
                                        e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                                    }
                                    else if (j == 2)
                                    {
                                        e.getPlayer().sendTitle("" , ChatColor.DARK_PURPLE + "この界隈は今日でさよなら。ありがとうございました。", 10 , 40 , 10);
                                        e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                                    }
                                }
                                else if (i == 3)
                                {
                                    this.cancel();
                                }

                                i++;
                            }
                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);

                        break;
                    case GREEN_DYE:
                        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "「KUNさん来たから行ってくる！」のデマが流されました。");
                        e.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_HUGE , e.getPlayer().getLocation() , 2 , 1 , 1 , 1 , 1);

                        for (Player p : Bukkit.getOnlinePlayers())
                        {
                            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer().getName() + "がデマを流しました！！");

                            Character ch = GameManager.getCharacter(p.getUniqueId());

                            if (ch.getType().equals(ChType.GIRL))
                            {
                                //e.getPlayer()を隠す
                                p.hidePlayer(KakoiPlugin.getPlugin(KakoiPlugin.class) , e.getPlayer());
                            }

                            p.playSound(p.getLocation() , Sound.ENTITY_EGG_THROW , 1 , 1);
                        }

                        new BukkitRunnable()
                        {
                            int i = 0;
                            @Override
                            public void run()
                            {
                                if (!GameManager.isStarted)
                                {
                                    this.cancel();
                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        //e.getPlayer()をshowする
                                        p.showPlayer(KakoiPlugin.getPlugin(KakoiPlugin.class) , e.getPlayer());
                                    }
                                    return;
                                }

                                if (i == 0)
                                {
                                    e.getPlayer().sendMessage(ChatColor.AQUA + "[TWICALL]" + "<" + e.getPlayer().getName() + "> " + "（・・・気まずいなー）");
                                    e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_SHEEP_SHEAR , 1 , 1);
                                }
                                else if (i == 5)
                                {
                                    e.getPlayer().sendMessage(ChatColor.AQUA + "[TWICALL]" + "<" + e.getPlayer().getName() + "> " + "お！KUNさん来たわ！（嘘）じゃ！切るね～！");
                                    e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_SHEEP_SHEAR , 1 , 1);

                                    for (Player p : Bukkit.getOnlinePlayers())
                                    {
                                        p.showPlayer(KakoiPlugin.getPlugin(KakoiPlugin.class) , e.getPlayer());
                                    }
                                }
                                else if (i == 7)
                                {
                                    e.getPlayer().sendMessage(ChatColor.AQUA + "[TWICALL]" + "<" + e.getPlayer().getName() + "> " + "（・・・よし！！次の人！！次！！）");
                                    e.getPlayer().playSound(e.getPlayer().getLocation() , Sound.ENTITY_VILLAGER_AMBIENT , 1 , 1);
                                }
                                else if (i > 8)
                                {
                                    this.cancel();
                                }

                                i++;
                            }

                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 10);
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
        Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getPlayer() + "がアイテムを一つ使用しました。");
        e.getPlayer().sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getItem().getItemMeta().getDisplayName() + "を使用しました！！");

        e.getItem().setAmount(e.getItem().getAmount() - 1);

    }
}
