package club.xena.client.impl.commands;

import club.xena.client.Xena;
import club.xena.client.api.command.Command;
import club.xena.client.api.module.Module;
import club.xena.client.api.util.client.MessageUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Allows you to toggle a module.","toggle" + " " + "[module]");
    }

    @Override
    public void runCommand(String[] args) {
        if (args.length > 1) {
            try {
                for (Module m : Xena.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[1])) {
                        m.toggle();
                        if (m.isToggled()) {
                            MessageUtil.sendMessagePrefix(ChatFormatting.AQUA + m.getName() + ChatFormatting.WHITE + " has been " + ChatFormatting.GREEN + "enabled");
                        } else {
                            MessageUtil.sendMessagePrefix(ChatFormatting.AQUA + m.getName() + ChatFormatting.WHITE + " has been " + ChatFormatting.RED + "disabled");
                        }
                    }
                }
            } catch (Exception e) {}
        }
    }
}
