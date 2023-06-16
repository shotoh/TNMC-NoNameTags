package io.github.shotoh.nonametags.listeners;

import io.github.shotoh.nonametags.NoNameTags;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final NoNameTags plugin;

    public PlayerListener(NoNameTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (ArmorStand as : plugin.getStands().values()) {
            ((CraftPlayer) player).getHandle().connection.send(new ClientboundAddEntityPacket(as));
        }
        plugin.hideNameTag(player);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArmorStand stand = plugin.getStands().get(player.getUniqueId());
        ClientboundRemoveEntitiesPacket removeEntitiesPacket = new ClientboundRemoveEntitiesPacket(stand.getId());
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().connection.send(removeEntitiesPacket);
        }
        plugin.getStands().remove(player.getUniqueId());
    }
}
