package club.xena.client.api.command;

import club.xena.client.Xena;
import club.xena.client.impl.commands.BindCommand;
import club.xena.client.impl.commands.ToggleCommand;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.ClientChatEvent;

import java.util.ArrayList;

public class CommandManager {
    public CommandManager() {
        Xena.EVENT_BUS.subscribe(this);
        init();
    }

    public String prefix = "@";

    public ArrayList<Command> commands = new ArrayList<>();

    public void init() {
        commands.add(new ToggleCommand());
        commands.add(new BindCommand());
    }

    @EventHandler
    public Listener<ClientChatEvent> listener = new Listener<>(event -> {
        String[] args = event.getMessage().split(" ");
        if (event.getMessage().startsWith(prefix)) {
            event.setCanceled(true);
            for (Command command : commands) {
                if (args[0].equalsIgnoreCase(prefix + command.getName())) {
                    command.runCommand(args);
                }
            }
        }
    });
}
