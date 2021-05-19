package io.github.tomatan515.kakoiplugin.shop;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    public void open(Player player)
    {
        Inventory inv = Bukkit.createInventory(null , 9 , "Shop");
        inv.setItem(0 , makeItem(Material.RED_DYE , ChatColor.YELLOW + "自撮りテイカー" , 50 , "生き残ってる男を全員3秒間動けなくする"));
        inv.setItem(1 , makeItem(Material.BLUE_DYE , ChatColor.YELLOW + "DMデリバー" , 50 , "生き残ってる男の中からランダムで特定の男を二秒間発光させる"));
        inv.setItem(2 , makeItem(Material.YELLOW_DYE , ChatColor.YELLOW + "アスレサポーター" , 15 , "5秒間の間、ジャンプ力が二倍になる"));

        player.openInventory(inv);
    }

    private ItemStack makeItem(Material mtr , String displayName , Integer price , String detail)
    {
        ItemStack item = new ItemStack(mtr);
        ItemMeta iMeta = item.getItemMeta();

        iMeta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "価格 " + ChatColor.GRAY + "（コイン）:");
        lore.add(price.toString());
        lore.add(detail);
        lore.add(ChatColor.GREEN + "右クリックで使用可能");

        iMeta.setLore(lore);
        item.setItemMeta(iMeta);

        return item;

    }
}
