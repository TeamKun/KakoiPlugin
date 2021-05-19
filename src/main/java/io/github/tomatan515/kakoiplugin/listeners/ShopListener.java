package io.github.tomatan515.kakoiplugin.listeners;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Character;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.characters.Man;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null)
        {
            return;
        }

        if (!e.getCurrentItem().hasItemMeta())
        {
            return;
        }

        if (e.getView().getTitle().contains("Shop"))
        {
            Character c = GameManager.getCharacter(p.getUniqueId());

            Integer price = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1));

            if (c.getMoney() <= price)
            {
                p.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "お金が足りないので、このアイテムは購入できません！ 必要金額:" + price + ", 所持金額:" + c.getMoney());
                e.setCancelled(true);
                return;
            }

            c.decMoney(price);
            p.getInventory().addItem(e.getCurrentItem());
            p.updateInventory();
            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + e.getCurrentItem().getItemMeta().getDisplayName() +" を" + price + "コインで購入しました！");
            p.closeInventory();
        }
    }
}
