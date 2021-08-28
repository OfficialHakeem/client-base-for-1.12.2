package club.xena.client.impl.gui.editor.component.components;

import club.xena.client.Xena;
import club.xena.client.impl.gui.editor.component.HudComponent;

public class Watermark extends HudComponent {
    public Watermark() {
        super("Watermark");

        setWidth(mc.fontRenderer.getStringWidth(Xena.NAME_VERSION));
        setHeight(mc.fontRenderer.FONT_HEIGHT);
    }

    public void render(float ticks) {
        mc.fontRenderer.drawStringWithShadow(Xena.NAME_VERSION, getX(), getY(), -1);
    }
}
