package club.xena.client.impl.commands;

import club.xena.client.Xena;
import club.xena.client.api.command.Command;
import club.xena.client.api.module.Module;
import club.xena.client.api.util.client.MessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Binds modules to a key.","bind" + " " + "[module]" + " " + "[key]");
    }

    @Override
    public void runCommand(String[] args) {
        if (args.length > 2) {
            for (Module module : Xena.moduleManager.getModules()) {
                if (module.getName().equalsIgnoreCase(args[1])) {
                    try {
                        module.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                        MessageUtil.sendMessagePrefix(ChatFormatting.AQUA + module.getName() + ChatFormatting.WHITE + " is now bound to " + ChatFormatting.RED + args[2].toUpperCase() + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + Keyboard.getKeyIndex(args[2].toUpperCase()) + ChatFormatting.GRAY + ")");
                    } catch (Exception e) {
                        MessageUtil.sendMessagePrefix(ChatFormatting.RED + "Something went wrong, yikes!");
                    }
                }
            }
        }
    }
}
