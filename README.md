# AdvancementButtonCustomCommand (abcc)

**Trigger commands and open menus when clicking the advancements button**

---

## 👀 Quick look

AdvancementButtonCustomCommand lets you intercept the click on the **Advancements** button (the "Progression" menu) and run your own server-side commands instead — open a custom GUI, play a sound, send a message, or anything else.

Perfect for servers that want to replace the vanilla Advancements screen with their own stats menu, profile GUI, or custom progression system.

### Example usage

<img alt="Plugin preview" src="https://cdn.modrinth.com/data/cached_images/fad9c1f44b0281398eff9feb7bdc17105317e947.gif" width="500">

(More use cases below)

---

## 📦 Requirements

- **Minecraft 1.21+**
- **PacketEvents 2.14.0+**
- **Java 21+**
---

## ⚙️ Installation

1. Download **[PacketEvents](https://modrinth.com/plugin/packetevents)** in your `plugins/` folder.
2. Put `AdvancementButtonCustomCommand-*.jar` into `plugins/`.
3. If you enable `disable-saving` in spigot.yml, **you must install the provided datapack** (see below).
4. Start the server once to generate the config.
5. And there you go.

---

## 📁 Required datapack

The plugin ships with a small datapack that creates a **dummy advancement**, which is required to force the client to send the `OPENED_TAB` packet.

Place it in your datapacks folder `world/datapacks/`

> ⚠️ **If you enable `disable-saving` in `spigot.yml`, you must use the provided datapack.** Otherwise the dummy advancement won't work and the button will do nothing.

---

## 🔧 Config

```yaml
# Enable or disable the redirection of the Advancements button.
enabled: true

# If you enable "disable-saving" in spigot.yml, you must use the provided datapack.
# Otherwise, the dummy advancement will not work and the button will do nothing.

# Command(s) executed when the player opens the Advancements menu.
# Note that the advancement menu does not close automatically.
# Use {player} as placeholder for the player name.
# Prefix with [player] to run as the player, [console] to run from console.
# If no prefix is given, it runs from console.
commands:
  - "[console] execute at {player} run playsound minecraft:entity.ender_dragon.death ui {player}"
  - "[console] tell {player} Trigger"
```
---

## 🎮 Commands & Permissions

| Command | Permission | Description |
|---|---|---|
| `/advancementbuttoncustomcommand reload` | `advancementbuttoncustomcommand.reload` | Reload the config |
| `/abcc reload`| same | Short alias |

---

## 💡 Example use cases

- Open a **custom stats GUI** when the player clicks Advancements.
- Show a **profile menu** with playtime, rank, balance, etc.
- Play a **sound** as feedback. (**This does not close the menu.**)
- Log the action to a **Databse** or **Discord webhook** via console commands.

---

## ⚠️ Known limitations

- The vanilla Advancements screen **cannot be closed by the server**. It will briefly appear before your command opens another GUI on top of it. (you can remove the concerned textures and language key to hide content)
- This plugin only reacts to the packet the client sends when the menu is opened.
- Requires the dummy advancement datapack to be installed and loaded.