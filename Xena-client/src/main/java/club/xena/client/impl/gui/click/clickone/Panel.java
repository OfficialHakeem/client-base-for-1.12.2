package club.xena.client.impl.gui.click.clickone;

import club.xena.client.Xena;
import club.xena.client.api.module.Category;
import club.xena.client.api.module.Module;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import club.xena.client.impl.gui.click.clickone.components.ModuleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Panel {
    protected Minecraft mc = Minecraft.getMinecraft();

    public ArrayList<Component> components;
    public String title;
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean isSettingOpen;
    private boolean isDragging;
    private boolean open;
    public int dragX;
    public int dragY;
    public Category cat;

    public Panel(String title, int x, int y, int width, int height, Category cat) {
        this.components = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isSettingOpen = true;
        this.isDragging = false;
        this.open = true;
        this.cat = cat;
        int tY = this.height;

        for (Module mod : Xena.moduleManager.getModules()) {
            if (mod.getCategory() == cat) {
                ModuleButton modButton = new ModuleButton(mod, this, tY);
                this.components.add(modButton);
                tY += 12;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Xena.settingsManager.getSettingID("OldClickGUIRainbow").getValBoolean()) {
            Gui.drawRect(x, y, x + width, y + height, RainbowUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(x, y, x + width, y + height, 0xFF222222);
        }

        FontUtil.drawText(title, x + 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);

        if (this.open && !this.components.isEmpty()) {
            for (Component component : components) {
                component.renderComponent();
            }
        }
    }

    public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public void closeAllSetting() {
        for (Component component : components) {
            component.closeAllSub();
        }
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public int getWidth() {
        return width;
    }

    public void setDragging(boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveGuiPanels();
            } catch (Exception e) {}
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        this.x = newX;

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveGuiPanels();
            } catch (Exception e) {}
        }
    }

    public void setY(int newY) {
        this.y = newY;

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveGuiPanels();
            } catch (Exception e) {}
        }
    }

    public Category getCategory() {
        return cat;
    }
}
