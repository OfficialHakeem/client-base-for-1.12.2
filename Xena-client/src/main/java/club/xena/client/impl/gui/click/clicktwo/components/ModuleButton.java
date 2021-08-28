package club.xena.client.impl.gui.click.clicktwo.components;

import club.xena.client.Xena;
import club.xena.client.api.module.Module;
import club.xena.client.api.setting.Setting;
import club.xena.client.api.util.colour.GUIColourUtil;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import club.xena.client.impl.gui.click.clicktwo.Panel;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class ModuleButton extends Component {
    private final ArrayList<Component> subcomponents;
    public Module mod;
    public Panel parent;
    public int offset;
    private boolean open;
    private boolean hovered;

    public ModuleButton(Module mod, Panel parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<>();
        this.open = false;
        int opY = offset + 15;

        if (Xena.settingsManager.getSettingsModule(mod) != null) {
            for (Setting setting : Xena.settingsManager.getSettingsModule(mod)) {
                if (setting.getType() == "boolean") {
                    this.subcomponents.add(new BooleanComponent(setting, this, opY));
                    opY += 15;
                } else if (setting.getType() == "integer") {
                    this.subcomponents.add(new IntegerComponent(setting, this, opY));
                    opY += 15;
                } else if (setting.getType() == "double") {
                    this.subcomponents.add(new DoubleComponent(setting, this, opY));
                    opY += 15;
                } else if (setting.getType() == "mode") {
                    this.subcomponents.add(new ModeComponent(setting, this, opY));
                    opY += 15;
                }
            }
        }
        this.subcomponents.add(new KeybindComponent(this, opY));
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
        int opY = this.offset + 15;
        for (final Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 15;
        }
    }

    @Override
    public void renderComponent() {
        if (this.mod.isToggled()) {
            Gui.drawRect(parent.getX() - 1, parent.getY() + offset, parent.getX() + parent.getWidth() + 1, parent.getY() + 15 + offset + 1, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() - 1, parent.getY() + offset, parent.getX(), parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() + parent.getWidth(), parent.getY() + offset, parent.getX() + parent.getWidth() + 1, parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 15 + offset, 0x75101010);
        } else {
            Gui.drawRect(parent.getX() - 1, parent.getY() + offset, parent.getX() + parent.getWidth() + 1, parent.getY() + 15 + offset + 1, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() - 1, parent.getY() + offset, parent.getX(), parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
            Gui.drawRect(parent.getX() + parent.getWidth(), parent.getY() + offset, parent.getX() + parent.getWidth() + 1, parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 15 + offset, 0xFF111111);
        }

        if (Xena.settingsManager.getSettingID("ClickGUIHoverChange").getValBoolean() && hovered ) {
            FontUtil.drawText(this.mod.getName(), parent.getX() + 6, parent.getY() + offset + 4, -1);
        } else {
            FontUtil.drawText(this.mod.getName(), parent.getX() + 4, parent.getY() + offset + 4, -1);
        }

        if (this.subcomponents.size() > 1) {
            FontUtil.drawText("...", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 4), -1);
        }

        if (Xena.settingsManager.getSettingID("ClickGUIDescriptions").getValBoolean() && hovered ) {
            FontUtil.drawText(mod.getDescription(), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getFontHeight() - 2), -1);
        }

        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }
    }

    @Override
    public void closeAllSub() {
        this.open = false;
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return 15 * (this.subcomponents.size() + 1);
        }
        return 15;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);

        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }

        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            if (!this.isOpen()) {
                parent.closeAllSetting();
                this.setOpen(true);
            } else {
                this.setOpen(false);
            }
            this.parent.refresh();
        }

        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > parent.getX ( ) && x < parent.getX ( ) + 100 && y > this.parent.getY ( ) + this.offset && y < this.parent.getY ( ) + 15 + this.offset;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
