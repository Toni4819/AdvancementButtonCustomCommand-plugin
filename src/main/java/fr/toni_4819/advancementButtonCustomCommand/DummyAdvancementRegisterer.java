package fr.toni_4819.advancementButtonCustomCommand;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DummyAdvancementRegisterer implements Listener {

    private final AdvancementButtonCustomCommand plugin;
    private final NamespacedKey advancementKey;

    public DummyAdvancementRegisterer(AdvancementButtonCustomCommand plugin) {
        this.plugin = plugin;
        this.advancementKey = new NamespacedKey("abcc", "dummy");
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getConfig().getBoolean("enabled", true)) return;

        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(plugin, () -> grantAdvancement(player), 20L);
    }

    private void grantAdvancement(Player player) {
        Advancement advancement = Bukkit.getAdvancement(advancementKey);
        if (advancement == null) {
            plugin.getLogger().warning("Advancement factice introuvable : " + advancementKey);
            return;
        }

        player.getAdvancementProgress(advancement).awardCriteria("impossible");
    }
}