package club.xena.client.impl.gui.editor.component;

import club.xena.client.impl.gui.editor.component.components.Inventory;
import club.xena.client.impl.gui.editor.component.components.Watermark;

import java.util.ArrayList;

public class HudComponentManager {
    public ArrayList<HudComponent> hudComponents = new ArrayList<>();

    public HudComponentManager() {
        hudComponents.add(new Watermark());
        hudComponents.add(new Inventory());
    }

    public ArrayList<HudComponent> getHudComponents() {
        return hudComponents;
    }

    public HudComponent getHudComponentByName(String name) {
        return hudComponents.stream().filter(hudComponent -> hudComponent.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
