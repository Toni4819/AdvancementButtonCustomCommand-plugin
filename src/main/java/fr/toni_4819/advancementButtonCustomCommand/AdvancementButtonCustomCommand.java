package fr.toni_4819.advancementButtonCustomCommand;

import com.github.retrooper.packetevents.PacketEvents;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class AdvancementButtonCustomCommand extends JavaPlugin implements CommandExecutor {

    private AdvancementPacketListener packetListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        DummyAdvancementRegisterer dummyGenerator = new DummyAdvancementRegisterer(this);
        dummyGenerator.register();

        this.packetListener = new AdvancementPacketListener(this);
        PacketEvents.getAPI().getEventManager().registerListener(packetListener);

        var command = getCommand("advancementbuttoncustomcommand");
        if (command != null) {
            command.setExecutor(this);
        }

        getLogger().info("AdvancementButtonCustomCommand enabled.");
    }

    @Override
    public void onDisable() {
        if (packetListener != null) {
            PacketEvents.getAPI().getEventManager().unregisterListener(packetListener);
        }
        getLogger().info("AdvancementButtonCustomCommand disabled.");
    }

    public void runConfiguredCommands(Player player) {
        for (String cmd : getConfig().getStringList("commands")) {
            runFormattedCommand(cmd, player);
        }
    }

    private void runFormattedCommand(String rawCommand, Player player) {
        String formatted = rawCommand.replace("{player}", player.getName()).trim();
        if (formatted.isEmpty()) return;

        String lower = formatted.toLowerCase();
        if (lower.startsWith("[player]")) {
            player.performCommand(formatted.substring("[player]".length()).trim());
        } else if (lower.startsWith("[console]")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    formatted.substring("[console]".length()).trim());
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatted);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("advancementbuttoncustomcommand.reload")) {
                sender.sendMessage("§cYou don't have permission.");
                return true;
            }
            reloadConfig();
            sender.sendMessage("§aAdvancementButtonCustomCommand config reloaded!");
            return true;
        }
        sender.sendMessage("§eUsage: /advancementbuttoncustomcommand reload");
        return true;
    }
}