package io.github.shotoh.nonametags.listeners;

import io.github.shotoh.nonametags.NoNameTags;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityListener implements Listener {
    private final NoNameTags plugin;

    public EntityListener(NoNameTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDismountEvent(EntityDismountEvent event) {
        Entity entity = event.getDismounted();
        if (plugin.getStands().containsValue(((CraftArmorStand) entity).getHandle())) {
            event.setCancelled(true);
        }
    }
}
