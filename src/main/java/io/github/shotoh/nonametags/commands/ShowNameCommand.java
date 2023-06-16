package io.github.shotoh.nonametags.commands;

import io.github.shotoh.nonametags.NoNameTags;
import io.github.shotoh.nonametags.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowNameCommand implements CommandExecutor {
    private final NoNameTags plugin;

    public ShowNameCommand(NoNameTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp() && sender instanceof Player player) {
            if (args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    plugin.showNameTag(player, target);
                } else {
                    Utils.sendMessage(player, "<red>Invalid player!");
                }
                return true;
            }
            Utils.sendMessage(player, "<red>Invalid Usage! \"/showname (player)\"");
        }
        return true;
    }
}
