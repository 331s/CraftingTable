package brs.craftingtable;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CraftingTable extends JavaPlugin implements TabCompleter {

    private FileConfiguration config;
    private final Map<UUID, Long> cooldown = new HashMap<>();
    private String prefix;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        config = getConfig();

        prefix = color(config.getString("prefix", "&a[CraftingTable] &f"));

        Objects.requireNonNull(getCommand("craft")).setExecutor(this);
        Objects.requireNonNull(getCommand("craft")).setTabCompleter(this);

        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);

        Bukkit.getLogger().info("CraftingTable aktif!");
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public FileConfiguration getCfg() {
        return config;
    }

    public String getPrefix() {
        return prefix;
    }

    public String msg(String key) {
        return color(config.getString("messages." + key, "§cMissing: " + key));
    }

    private String color(String s) {
        return s.replace("&", "§");
    }

    // Ses çözümleyici (Paper 1.21+ güvenli yöntem)
    public Sound getSoundSafe(String id) {
        NamespacedKey key = NamespacedKey.fromString(id.toLowerCase());
        if (key == null) return null;
        return Registry.SOUNDS.get(key);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Bu komut oyuncular içindir.");
            return true;
        }

        // /craft settings
        if (args.length > 0 && args[0].equalsIgnoreCase("settings")) {
            p.openInventory(new SettingsGUI(this).create(p));
            return true;
        }

        // cooldown
        long now = System.currentTimeMillis();
        long last = cooldown.getOrDefault(p.getUniqueId(), 0L);

        int cd = config.getInt("cooldown", 3000);

        if (now - last < cd) {
            p.sendMessage(prefix + msg("cooldown"));
            return true;
        }

        cooldown.put(p.getUniqueId(), now);

        // çalıştır
        p.openWorkbench(null, true);

        // ses
        boolean enabled = config.getBoolean("sound." + p.getUniqueId(), true);

        if (enabled) {
            String id = config.getString("sound-default", "minecraft:entity.villager.work_fletcher");
            Sound s = getSoundSafe(id);
            if (s != null) {
                p.playSound(p.getLocation(), s, 1f, 1f);
            }
        }

        p.sendMessage(prefix + msg("workbench-opened"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        if (!(sender instanceof Player)) return List.of();

        if (args.length == 1)
            return List.of("settings");

        return List.of();
    }
}
