package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import io.github.tomatan515.kakoiplugin.game.WorldPreparer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerKilledListener implements Listener {

    @EventHandler
    public void PlayerDamageReceive(EntityDamageByEntityEvent e) {

        if (!GameManager.isStarted) return;

        //攻撃を受けたのが男なら
        if(e.getEntity() instanceof Player && GameManager.getCharacter(e.getEntity().getUniqueId()).getType().equals(ChType.MAN)) {
            Player damaged = (Player) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Character c = GameManager.getCharacter(e.getDamager().getUniqueId());

                //攻撃した人が男であって、まだ捕まってなければ
                if (c.getType().equals(ChType.MAN) && !((Man) c).isCought()) {
                    e.setCancelled(true);
                    return;
                }
                else if (((Man)GameManager.getCharacter(damaged.getUniqueId())).isCought())
                {
                    e.setCancelled(true);
                    return;
                }

                Player damager = (Player) e.getDamager();

                if ((damaged.getHealth() - e.getDamage()) <= 0) {
                    //Killed
                    e.setCancelled(true);
                    damaged.setHealth(20);

                    //死んだときの処理
                    onDeath(damaged, damager);
                    //不真面目にした！！！等のメッセージ
                } else {
                    damaged.getWorld().spawnParticle(Particle.HEART, damaged.getLocation(), 10, 1, 1, 1, 10);
                }
            }
            else{
                e.setCancelled(true);
            }
        }
        else
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDamageReceive (EntityDamageEvent e)
    {
        if (GameManager.isStarted)
        {
            if (e.getEntity() instanceof Player)
            {
                //エンティティーから攻撃をうける以外だったら
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID))
                {
                    Player p = (Player) e.getEntity();
                    p.teleport(WorldPreparer.getSpawnLocation());
                    e.setCancelled(true);

                    if (GameManager.getCharacter(p.getUniqueId()).getType().equals(ChType.MAN)
                            && !((Man)GameManager.getCharacter(p.getUniqueId())).isCought())
                    {
                        //捕まってなければ
                        for (Player pl : Bukkit.getOnlinePlayers())
                        {
                            pl.sendMessage(KakoiPlugin.PREFIX + ChatColor.BLUE + p.getName() + "が女の子に自ら囲われに行った。");
                        }

                        Man man = (Man) GameManager.getCharacter(p.getUniqueId());
                        man.setCought(true);

                        new BukkitRunnable()
                        {
                            int i = 0;

                            @Override
                            public void run()
                            {

                                if (i > 3)
                                {
                                    PlayerJoinLeftListener.manRespawn(p);
                                    this.cancel();
                                }
                                else
                                {
                                    p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "あと" + (3 - i) + "秒でリスポーンします。");
                                    p.setGameMode(GameMode.SPECTATOR);
                                }

                                i++;

                            }
                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                    }
                    else
                    {
                        new BukkitRunnable()
                        {
                            int i = 0;

                            @Override
                            public void run()
                            {

                                if (i > 3)
                                {
                                    p.teleport(WorldPreparer.getSpawnLocation());
                                    p.setGameMode(GameMode.ADVENTURE);
                                    this.cancel();
                                }
                                else
                                {
                                    p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "あと" + (3 - i) + "秒でリスポーンします。");
                                    p.setGameMode(GameMode.SPECTATOR);
                                }

                                i++;

                            }
                        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
                    }
                }
                else if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && !e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK))
                {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void onDeath(Player damaged , Player damager)
    {
        //isCought = trueに。
        //装備をわたす⇒黒の革全身
        //damagerが女ならkillcountを足す。

        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.BLUE + damaged.getName() + "が" + damager.getName() + "の誘惑に乗り理性を失った。");
        }
        try
        {
            Man man = (Man) GameManager.getCharacter(damaged.getUniqueId());
            man.setCought(true);
            if (GameManager.getCharacter(damager.getUniqueId()).getType().equals(ChType.GIRL))
            {
                Girl girl = (Girl) GameManager.getCharacter(damager.getUniqueId());
                girl.addKillCount();
            }

            new BukkitRunnable()
            {
                int i = 0;

                @Override
                public void run()
                {

                    if (i > 3)
                    {
                        PlayerJoinLeftListener.manRespawn(damaged);
                        this.cancel();
                    }
                    else
                    {
                        damaged.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "あと" + (3 - i) + "秒でリスポーンします。");
                        damaged.setGameMode(GameMode.SPECTATOR);
                    }

                    i++;

                }
            }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
        }
        catch (Exception e)
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "エラーが発生しました。このバグを管理者に報告してください。(PlayerKilledListener 81)");
        }
    }
}
