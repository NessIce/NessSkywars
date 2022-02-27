package br.com.ness.skywars.game.chest;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.List;

public class ChestItem {

    private int sort;
    private ItemStack itemStack;

    public ChestItem(int sort, String itemData) {
        this.sort = sort;

        String[] attributes = itemData.split(" : ");

        Material material = Material.getMaterial(attributes[0]);
        int data = Integer.parseInt(attributes[1]);
        int amount = Integer.parseInt(attributes[2]);

        ItemStack item = new ItemStack(material, amount, (short)data);

        if(attributes.length>3) {
            ItemMeta meta = item.getItemMeta();

            for (int i = 3; i < attributes.length; i++) {
                if (attributes[i] == null) {
                    break;
                }

                String[] attributeData = attributes[i].split("=");
                String caracter = attributeData[0];
                String value = attributeData[1];

                switch (caracter) {
                    case "display": {
                        meta.setDisplayName(value.replace("&", "§"));
                        break;
                    }
                    case "lore": {
                        List<String> lore = Arrays.asList(value.split(";"));
                        lore.replaceAll(e -> e.replace("&", "§"));
                        meta.setLore(lore);
                        break;
                    }
                    case "enchants": {
                        for (String s : value.split(";")) {
                            String enchantName = s.split(":")[0];
                            int enchantLevel = Integer.parseInt(s.split(":")[1]);
                            meta.addEnchant(Enchantment.getByName(enchantName), enchantLevel, true);
                        }
                        break;
                    }
                    case "flags": {
                        for (String s : value.split(";")) {
                            meta.addItemFlags(ItemFlag.valueOf(s));
                        }
                        break;
                    }
                }
            }
            item.setItemMeta(meta);
        }
        itemStack = item;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
