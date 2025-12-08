package brs.craftingtable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SettingsGUI {

    private final CraftingTable plugin;

    public SettingsGUI(CraftingTable plugin) {
        this.plugin = plugin;
    }

    public Inventory create(Player p) {

        Inventory gui = Bukkit.createInventory(null, 27, "CraftingTable Ayarları");

        FileConfiguration cfg = plugin.getCfg();

        boolean enabled = cfg.getBoolean("sound." + p.getUniqueId(), true);

        ItemStack sound = new ItemStack(Material.NOTE_BLOCK);
        ItemMeta sm = sound.getItemMeta();

        sm.setDisplayName("§eSes Ayarı");
        sm.setLore(List.of(
                enabled ? "§aAçık" : "§cKapalı",
                "§7Tıklayarak değiştir."
        ));

        sound.setItemMeta(sm);
        gui.setItem(13, sound);

        return gui;
    }
}
