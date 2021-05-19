package io.github.tomatan515.kakoiplugin.game;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class WorldPreparer {

    private static Location spawnLocation = null;

    public static void end()
    {
        if (GameManager.isStarted)
        {
            GameManager.getJoinedPlayers().clear();
            GameManager.isStarted = false;

            for (Player p : Bukkit.getOnlinePlayers())
            {
                p.teleport(spawnLocation);
            }
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "ゲームが開始していません。ゲームを終了できませんでした。");
            return;
        }
    }

    public static void onStart()
    {
        //メッセージ
        //装備を配る
        //ショップ等の説明
        //スポーン

        for (Player p : Bukkit.getOnlinePlayers())
        {
            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().clear();
            p.getInventory().addItem(new ItemStack(Material.BREAD , 64));
            p.updateInventory();
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
                }

                if (i == 0)
                {
                    for (Character ch : GameManager.getPlayers(ChType.MAN))
                    {
                        Man man = (Man) ch;
                        man.setCought(false);
                        wearArmors(getLeatherArmors(Color.BLUE) , Bukkit.getPlayer(ch.getUniqueId()));
                        Bukkit.getPlayer(ch.getUniqueId()).sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "誘惑してくる女の子から逃げ切れ！！");
                        Bukkit.getPlayer(ch.getUniqueId()).playSound(Bukkit.getPlayer(ch.getUniqueId()).getLocation() , Sound.UI_LOOM_TAKE_RESULT , 1 , 1);
                        Bukkit.getPlayer(ch.getUniqueId()).teleport(WorldPreparer.getSpawnLocation());
                        Bukkit.getPlayer(ch.getUniqueId()).setGameMode(GameMode.ADVENTURE);
                    }
                }
                if (i > 0 && i <= 1)
                {
                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "あと15秒で女の子が誘惑しに来ます！！！");
                        p.playSound(p.getLocation() , Sound.ENTITY_CAT_STRAY_AMBIENT , 1 , 1);
                    }
                }
                else if (i > 10 && i <= 15)
                {
                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "女の子が動き出すまで:" + (15 - i));
                        p.playSound(p.getLocation() , Sound.UI_STONECUTTER_SELECT_RECIPE , 1 , 1);
                    }
                }
                else if (i > 15)
                {
                    for (Character ch : GameManager.getPlayers(ChType.GIRL))
                    {
                        Girl girl = (Girl) ch;
                        girl.clearMoney();
                        wearArmors(getLeatherArmors(Color.RED) , Bukkit.getPlayer(ch.getUniqueId()));
                        Bukkit.getPlayer(ch.getUniqueId()).sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "男を魅惑し、全ての男の理性を奪え！！！");
                        Bukkit.getPlayer(ch.getUniqueId()).sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "/shop (または/s)でコインを使用してアイテムが購入できます！");

                        Bukkit.getPlayer(ch.getUniqueId()).teleport(WorldPreparer.getSpawnLocation());
                        Bukkit.getPlayer(ch.getUniqueId()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE , Integer.MAX_VALUE , 4 , false , false));
                        Bukkit.getPlayer(ch.getUniqueId()).setGameMode(GameMode.ADVENTURE);
                    }

                    for (Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendTitle(ChatColor.WHITE + "" + ChatColor.BOLD + "ゲームスタート!!" ,"" , 0 , 30 , 20);
                        p.playSound(p.getLocation() , Sound.ENTITY_DRAGON_FIREBALL_EXPLODE , 0.5F , 1);
                    }

                    this.cancel();
                }

                i++;
            }
        }.runTaskTimer(KakoiPlugin.getPlugin(KakoiPlugin.class) , 0 , 20);
    }

    public static List<ItemStack> getLeatherArmors(Color clr)
    {
        List<ItemStack> armors = new ArrayList<>();

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET , 1);
        LeatherArmorMeta he = (LeatherArmorMeta) helmet.getItemMeta();
        he.setColor(clr);
        he.addEnchant(Enchantment.DURABILITY , 30 , true);
        helmet.setItemMeta(he);
        armors.add(helmet);

        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE , 1);
        LeatherArmorMeta cp = (LeatherArmorMeta) chestPlate.getItemMeta();
        cp.setColor(clr);
        cp.addEnchant(Enchantment.DURABILITY , 30 , true);
        chestPlate.setItemMeta(cp);
        armors.add(chestPlate);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS , 1);
        LeatherArmorMeta le = (LeatherArmorMeta) leggings.getItemMeta();
        le.setColor(clr);
        le.addEnchant(Enchantment.DURABILITY , 30 , true);
        leggings.setItemMeta(le);
        armors.add(leggings);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS , 1);
        LeatherArmorMeta bo = (LeatherArmorMeta) boots.getItemMeta();
        bo.setColor(clr);
        bo.addEnchant(Enchantment.DURABILITY , 30 , true);
        boots.setItemMeta(bo);
        armors.add(boots);

        return armors;
    }

    private static void wearArmors(List<ItemStack> armors , Player player)
    {
        player.getInventory().setHelmet(armors.get(0));
        player.getInventory().setChestplate(armors.get(1));
        player.getInventory().setLeggings(armors.get(2));
        player.getInventory().setBoots(armors.get(3));
    }

    public static Location getSpawnLocation() {
        return spawnLocation;
    }

    public static void setSpawnLocation(Location loc)
    {
        spawnLocation = loc;
    }
}
