package net.highskiesmc.hscore.highskies;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public abstract class HSCommand implements CommandExecutor {
    protected final HSPlugin main;

    public HSCommand(HSPlugin main) {
        this.main = main;
    }

    protected abstract String getPermissionToReload();

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return executeCommand(commandSender, command, s, strings);
    }

    public abstract boolean executeCommand(CommandSender sender, Command cmd, String label, String[] args);

    protected final boolean reload(CommandSender sender) {
        if (!hasPermission(sender, getPermissionToReload()))
            return false;

        this.main.onReload();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.main.getMessages().getString("commands.reload.success", "&6&l[!] &eYou have successfully " +
                        "reloaded " + this.main.getName())));

        return true;
    }

    protected final boolean hasPermission(@Nonnull CommandSender sender, @Nonnull String permission) {
        if (sender.hasPermission(permission))
            return true;

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                this.main.getMessages().getString("no-permission", "&4&l[!] &cYou do not have permission to do " +
                        "that!")));

        return false;
    }
}
