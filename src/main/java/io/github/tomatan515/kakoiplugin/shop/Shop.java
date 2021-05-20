package io.github.tomatan515.kakoiplugin.shop;


import io.github.tomatan515.kakoiplugin.characters.ChType;
import io.github.tomatan515.kakoiplugin.characters.Girl;
import io.github.tomatan515.kakoiplugin.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {

    public void open(Player player)
    {
        Inventory inv = Bukkit.createInventory(null , 9 , "Shop (所持金:" + GameManager.getCharacter(player.getUniqueId()).getMoney() + ")");

        if (GameManager.getCharacter(player.getUniqueId()).getType().equals(ChType.MAN))
        {
            inv.setItem(0 , makeItem(Material.LIGHT_BLUE_DYE , ChatColor.YELLOW + "ヘイト誘導" , 50 , "使うとランダムで選ばれる特定の男にデバフがかける" , Arrays.asList("え！？なんで俺だけなの！？" , "いやいや、他の人もしてますよ！！")));
            inv.setItem(1 , makeItem(Material.GREEN_DYE , ChatColor.YELLOW + "デマ「KUNさん来たから行ってくる！！」" , 80 , "5秒間の間、周囲のプレイヤーから見えなくなる" , Arrays.asList("「お！ＫＵＮさん来た！！じゃ！ツイコールきるね～！！」" , "↑よく使われる(ガチ）")));
            inv.setItem(2 , makeItem(Material.LIME_DYE, ChatColor.YELLOW + "お気持ち表明" , 50, "使うと三秒間、防御力が強化される。" , Arrays.asList("この度は申し訳ありませんでした。" , "お！アンチが減って、、いや増えてる！？")));
        }
        else
        {
            inv.setItem(0 , makeItem(Material.RED_DYE , ChatColor.YELLOW + "自撮りテイカー" , 80 , "生き残ってる男を全員3秒間動けなくする" , Arrays.asList("○○の自撮りが公開されてる！？", "マイクラなんてやってられねえ！ちょっと見てくるわ！")));
            inv.setItem(1 , makeItem(Material.BLUE_DYE , ChatColor.YELLOW + "DMデリバー" , 50, "生き残ってる男の中からランダムで特定の男を5秒間発光させる" , Arrays.asList("え！？僕ですか！？" , "いやいや～女の子とＤＭなんてｗしてませんってｗ")));
            inv.setItem(2 , makeItem(Material.YELLOW_DYE , ChatColor.YELLOW + "アスレサポーター" , 15 , "5秒間の間、ジャンプ力が二倍になる" , Arrays.asList("女性の皆さん、安心してください。" , "アスレマウントしてくる男にはこれです！！")));
        }

        player.openInventory(inv);
    }

    private ItemStack makeItem(Material mtr , String displayName , Integer price , String detail , List<String> additional)
    {
        ItemStack item = new ItemStack(mtr);
        ItemMeta iMeta = item.getItemMeta();

        iMeta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "価格 " + ChatColor.GRAY + "（コイン）:");
        lore.add(price.toString());
        lore.add(detail);
        lore.add(ChatColor.GREEN + "右クリックで使用可能");
        lore.add("説明");

        for (int i = 0 ; i < additional.size() ; i++)
        {
            lore.add(additional.get(i));
        }

        iMeta.setLore(lore);
        item.setItemMeta(iMeta);

        return item;

    }
}
