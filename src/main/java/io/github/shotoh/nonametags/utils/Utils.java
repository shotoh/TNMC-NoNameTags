package io.github.shotoh.nonametags.utils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.intellij.lang.annotations.Subst;

public class Utils {
    public static void sendMessage(CommandSender sender, String... strings) {
        for (String string : strings) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<!i>" + string));
        }
    }

    public static void playSound(Entity entity, @Subst("entity.experience_orb.pickup") String id, float volume, float pitch) {
        entity.playSound(Sound.sound(Key.key(id), Sound.Source.MASTER, volume, pitch), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
    }

    public static void playSound(Location loc, @Subst("entity.experience_orb.pickup") String id, float volume, float pitch) {
        loc.getWorld().playSound(Sound.sound(Key.key(id), Sound.Source.MASTER, volume, pitch), loc.getX(), loc.getY(), loc.getZ());
    }
}
