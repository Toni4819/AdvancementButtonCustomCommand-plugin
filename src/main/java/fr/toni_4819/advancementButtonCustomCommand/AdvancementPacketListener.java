package fr.toni_4819.advancementButtonCustomCommand;

import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientAdvancementTab;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AdvancementPacketListener extends PacketListenerAbstract {

    private final AdvancementButtonCustomCommand plugin;

    public AdvancementPacketListener(AdvancementButtonCustomCommand plugin) {
        super(PacketListenerPriority.NORMAL);
        this.plugin = plugin;
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.ADVANCEMENT_TAB) return;
        if (!plugin.getConfig().getBoolean("enabled", true)) return;

        WrapperPlayClientAdvancementTab wrapper = new WrapperPlayClientAdvancementTab(event);
        if (wrapper.getAction() != WrapperPlayClientAdvancementTab.Action.OPENED_TAB) return;

        String playerName = event.getUser().getName();


        Bukkit.getScheduler().runTask(plugin, () -> {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player != null) {
                plugin.runConfiguredCommands(player);
            }
        });
    }
}