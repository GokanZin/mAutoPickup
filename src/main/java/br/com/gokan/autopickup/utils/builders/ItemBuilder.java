package br.com.gokan.mmodos.utils.builders;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder( Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }
    public ItemBuilder( String material_name, Byte data) {
        Material material = org.bukkit.Material.matchMaterial(material_name);
        this.itemStack = new ItemStack(material);
        this.itemStack.getData().setData(data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder( String idData) {
        if (idData.contains(":")) {
            String[] parts = idData.split(":");
            int id = Integer.parseInt(parts[0]);
            short data = parts.length > 1 ? Short.parseShort(parts[1]) : 0;
            Material material = Material.getMaterial(id);
            if (material == null) {
                throw new IllegalArgumentException("Invalid item ID: " + id);
            }
            this.itemStack = new ItemStack(material, 1, data);
        } else {
            Material material = Material.matchMaterial(idData);
            if (material == null) {
                throw new IllegalArgumentException("Invalid item ID or name: " + idData);
            }
            this.itemStack = new ItemStack(material);
        }
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setType(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        itemStack.setDurability((short) damage);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(Enchantment[] enchantments, int[] levels) {
        for (int i = 0; i < enchantments.length; i++) {
            itemStack.addEnchantment(enchantments[i], levels[i]);
        }
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) return this;
        itemMeta.setDisplayName(displayName.replace("&", "§"));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore == null || lore.isEmpty()) return this;
        itemMeta.setLore(lore);
        return this;
    }


    public ItemBuilder setSkullOwner(String owner) {
        if (owner == null || owner.isEmpty()) return this;
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwner(owner);
            itemMeta = skullMeta;
        }
        return this;
    }


    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}