package brs.craftingtable;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingTable extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("CraftingTable plugin aktif!");
    }

    @Override
    public void onDisable() {
        getLogger().info("CraftingTable plugin kapatıldı!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut sadece oyuncular içindir.");
            return true;
        }

        Player p = (Player) sender;

        p.openWorkbench(null, true);

        p.playSound(p.getLocation(), Sound.BLOCK_WOOD_PLACE, 1f, 1f);

        p.sendMessage("§aÇalışma masası açıldı!");

        return true;
    }
}
