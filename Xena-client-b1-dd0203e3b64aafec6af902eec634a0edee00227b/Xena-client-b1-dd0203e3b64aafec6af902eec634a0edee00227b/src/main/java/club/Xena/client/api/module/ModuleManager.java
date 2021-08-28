package club.xena.client.api.module;

import club.xena.client.impl.modules.chat.*;
import club.xena.client.impl.modules.combat.*;
import club.xena.client.impl.modules.core.*;
import club.xena.client.impl.modules.exploit.*;
import club.xena.client.impl.modules.misc.*;
import club.xena.client.impl.modules.movement.*;
import club.xena.client.impl.modules.player.*;
import club.xena.client.impl.modules.render.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* Chat */
        modules.add(new ChatSuffix());
        modules.add(new AutoInsult());
        modules.add(new DurabilityWarn());

        /* Combat */


        /* Core */
        modules.add(new OldClickGUI());
        modules.add(new HUD());
        modules.add(new HUDEditor());
        modules.add(new ClickGUI());

        /* Exploit */

        /* Misc */

        /* Movement */

        /* Player */
		
        /* Render */

    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}