package brs.craftingtable;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final CraftingTable plugin;

    public InventoryClickListener(CraftingTable plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;
        if (e.getClickedInventory() == null) return;

        if (!e.getView().getTitle().equals("CraftingTable Ayarları")) return;

        e.setCancelled(true);

        FileConfiguration cfg = plugin.getCfg();
        UUID id = p.getUniqueId();

        int slot = e.getRawSlot();

        if (slot == 13) {
            boolean now = cfg.getBoolean("sound." + id, true);
            cfg.set("sound." + id, !now);
            plugin.saveConfig();

            p.sendMessage(plugin.getPrefix() + plugin.msg("sound-toggled"));
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            p.openInventory(new SettingsGUI(plugin).create(p));
        }
    }
}
