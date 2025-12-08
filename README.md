# CraftingTable Plugin (Paper 1.21+)

A simple and lightweight utility plugin that allows players to instantly open a crafting table using a command.  
Includes per-player sound settings, cooldown control, and a clean configuration system.

---

## ✨ Features

- **/craft command** to instantly open a workbench  
- **Settings GUI** to toggle crafting sound per player  
- **Configurable cooldown** to prevent command spam  
- **Customizable messages & prefix** from config.yml  
- **Permission support:** `craftingtable.use`  

---

## 📦 Installation

1. Download the plugin `.jar` from the Releases section  
2. Place it into your server's `plugins/` folder  
3. Restart or reload your server  
4. The plugin will generate a `config.yml` file automatically

---

## 🛠 Commands

| Command |       Description        |
|---------|--------------------------|
| `/craft`| Opens the crafting table |

---

## 🔐 Permissions

|     Permission      |      Description      | Default |
|---------------------|-----------------------|---------|
| `craftingtable.use` | Allows using `/craft` | `true`  |

---

## ⚙ Configuration (`config.yml`)

```yaml
prefix: "&a[CraftingTable] &f"

cooldown: 1000

sound-default: "minecraft:entity.villager.work_fletcher"

messages:
  workbench-opened: "&aÇalışma masası açıldı!"
  cooldown: "&cBiraz beklemelisin."
  sound-toggled: "&eSes ayarı değiştirildi."
