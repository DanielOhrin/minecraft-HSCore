package net.highskiesmc.hscore.commands;

import net.highskiesmc.hscore.highskies.HSPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public abstract class HSCommand implements CommandExecutor {
    protected final HSPlugin main;
    protected final Map<CommandConfig.Type, List<CommandConfig<?>>> config;
    public HSCommand(HSPlugin main) {
        this.main = main;
        this.config = createConfig();
    }
    protected abstract Map<CommandConfig.Type, List<CommandConfig<?>>> createConfig();
    protected abstract String getPermissionToReload();

    @Override
    public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return executeCommand(commandSender, command, s, strings);
    }

    public abstract boolean executeCommand(CommandSender sender, Command cmd, String label, String[] args);

    protected final boolean reload(CommandSender sender, String successMsg, String permissionMsg) {
        if (!hasPermission(sender, getPermissionToReload(), permissionMsg)) {
            return false;
        }

        this.main.onReload();

        sender.sendMessage(successMsg);

        return true;
    }

    protected final boolean hasPermission(@Nonnull CommandSender sender, @Nonnull String permission, String msg) {
        if (sender.hasPermission(permission))
            return true;

        sender.sendMessage(msg);

        return false;
    }
}
