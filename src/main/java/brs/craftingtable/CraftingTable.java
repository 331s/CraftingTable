package brs.craftingtable;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingTable extends JavaPlugin {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getLogger().info("CraftingTable aktif edildi!");
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("CraftingTable devre dışı bırakıldı!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut yalnızca oyuncular tarafından kullanılabilir.");
            return true;
        }

        Player p = (Player) sender;

        if (args.length > 0 && args[0].equalsIgnoreCase("togglesound")) {
            boolean current = config.getBoolean("sound." + p.getUniqueId(), true);
            boolean next = !current;
            config.set("sound." + p.getUniqueId(), next);
            saveConfig();
            p.sendMessage(next ? "§aSes efekti açıldı." : "§cSes efekti kapatıldı.");
            return true;
        }

        if (!p.hasPermission("craftingtable.use")) {
            p.sendMessage("§cBu komutu kullanma iznine sahip değilsiniz.");
            return true;
        }

        p.openWorkbench(null, true);

        boolean sound = config.getBoolean("sound." + p.getUniqueId(), true);
        if (sound) {
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_WORK_FLETCHER, 1f, 1f);
        }

        p.sendMessage("§aÇalışma masası açıldı!");

        return true;
    }
}
