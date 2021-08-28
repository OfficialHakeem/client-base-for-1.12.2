package club.xena.client.impl.gui.editor.screen;

import club.xena.client.Xena;
import club.xena.client.impl.gui.editor.component.HudComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class HudEditor extends GuiScreen {
    public static ArrayList<HudPanel> hudpanels;

    public HudEditor() {
        hudpanels = new ArrayList<>();
        int hudPanelX = 5;
        int hudPanelY = 5;
        int hudPanelWidth = 100;
        int hudPanelHeight = 15;

        HudEditor.hudpanels.add(new HudPanel("Xena Client HUD", hudPanelX, hudPanelY, hudPanelWidth, hudPanelHeight));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Xena.settingsManager.getSettingID("HudEditorBackground").getValBoolean()) {
            drawDefaultBackground();
        }

        for (HudPanel hp : hudpanels) {
            hp.updatePosition(mouseX, mouseY);
            hp.drawScreen(mouseX, mouseY, partialTicks);

            for (Element elem : hp.getElements()) {
                elem.updateElement(mouseX, mouseY);
            }
        }

        for (HudComponent hudComponent : Xena.hudComponentManager.getHudComponents()) {
            if (hudComponent.isEnabled()) {
                hudComponent.updatePosition(mouseX,mouseY);
                if (hudComponent.isDragging()) {
                    Gui.drawRect(hudComponent.getX() + -2, hudComponent.getY() + -2, hudComponent.getX() + hudComponent.getWidth() + 2, hudComponent.getY() + hudComponent.getHeight() + 2, 0x90303030);
                }
                Gui.drawRect(hudComponent.getX() + -1, hudComponent.getY() + -1, hudComponent.getX() + hudComponent.getWidth() + 1, hudComponent.getY() + hudComponent.getHeight() + 1, 0x75101010);
                hudComponent.render(partialTicks);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (HudPanel hp : hudpanels) {
            if (hp.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                hp.setDragging(true);
                hp.dragX = mouseX - hp.getX();
                hp.dragY = mouseY - hp.getY();
            }

            if (hp.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                hp.setOpen(!hp.isOpen());
            }

            if (hp.isOpen() && !hp.getElements().isEmpty()) {
                for (Element elem : hp.getElements()) {
                    elem.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }

        for (HudComponent hudComponent : Xena.hudComponentManager.getHudComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && mouseButton == 0 && hudComponent.isEnabled()) {
                hudComponent.setDragging(true);
                hudComponent.setDragX(mouseX - hudComponent.getX());
                hudComponent.setDragY(mouseY - hudComponent.getY());
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (HudPanel hp : hudpanels) {
            hp.setDragging(false);

            if (hp.isOpen() && !hp.getElements().isEmpty()) {
                for (Element elem : hp.getElements()) {
                    elem.mouseReleased(mouseX, mouseY, state);
                }
            }
        }

        for (HudComponent hudComponent : Xena.hudComponentManager.getHudComponents()) {
            if (hudComponent.isMouseOnComponent(mouseX, mouseY) && hudComponent.isEnabled()) {
                hudComponent.setDragging(false);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}

