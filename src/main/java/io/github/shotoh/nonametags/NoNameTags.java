package io.github.shotoh.nonametags;

import io.github.shotoh.nonametags.commands.ShowNameCommand;
import io.github.shotoh.nonametags.listeners.EntityListener;
import io.github.shotoh.nonametags.listeners.PlayerListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.UUID;

public class NoNameTags extends JavaPlugin {
    private final HashMap<UUID, ArmorStand> stands = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("showname").setExecutor(new ShowNameCommand(this));

        registerEvents(new EntityListener(this));
        registerEvents(new PlayerListener(this));
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    public HashMap<UUID, ArmorStand> getStands() {
        return stands;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void hideNameTag(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        ArmorStand stand = new ArmorStand(((CraftWorld) world).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        stand.setMarker(true);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        player.addPassenger(stand.getBukkitEntity());
        ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(stand);
        for (Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) p).getHandle().connection.send(addEntityPacket);
        }
        stands.put(player.getUniqueId(), stand);
    }

    public void showNameTag(Player player, Player viewer) {
        if (player.getLocation().distanceSquared(viewer.getLocation()) <= NumberConversions.square(this.getConfig().getInt("minimum-distance"))) {
            ArmorStand stand = stands.get(player.getUniqueId());
            ClientboundRemoveEntitiesPacket removeEntitiesPacket = new ClientboundRemoveEntitiesPacket(stand.getId());
            ((CraftPlayer) viewer).getHandle().connection.send(removeEntitiesPacket);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(stand);
                    ((CraftPlayer) viewer).getHandle().connection.send(addEntityPacket);
                }
            }.runTaskLater(this, this.getConfig().getInt("show-duration"));
        }
    }
}
