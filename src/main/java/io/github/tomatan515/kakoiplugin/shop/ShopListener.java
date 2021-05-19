package io.github.tomatan515.kakoiplugin.shop;

import io.github.tomatan515.kakoiplugin.KakoiPlugin;
import io.github.tomatan515.kakoiplugin.characters.Girl;
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
            Girl girl = (Girl) GameManager.getCharacter(p.getUniqueId());

            Integer price = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1));

            if (girl.getMoney() <= price)
            {
                p.sendMessage(KakoiPlugin.PREFIX + ChatColor.RED + "お金が足りないので、このアイテムは購入できません！ 必要金額:" + price + ", 所持金額:" + girl.getMoney());
                return;
            }

            girl.decMoney(price);
            p.getInventory().addItem(e.getCurrentItem());
            p.sendMessage(KakoiPlugin.PREFIX + ChatColor.GREEN + "%item% を" + price + "コインで購入しました！"
                    .replaceAll("%item%" , e.getCurrentItem().getItemMeta().getDisplayName()));
        }
    }
}
