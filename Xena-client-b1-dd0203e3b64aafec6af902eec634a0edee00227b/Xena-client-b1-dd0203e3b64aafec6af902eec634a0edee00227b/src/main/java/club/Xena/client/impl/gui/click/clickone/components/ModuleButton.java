package club.xena.client.impl.gui.click.clickone.components;

import club.xena.client.Xena;
import club.xena.client.api.module.Module;
import club.xena.client.api.setting.Setting;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import club.xena.client.impl.gui.click.clickone.Panel;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class ModuleButton extends Component {
    private final ArrayList<Component> subcomponents;
    public Module mod;
    public Panel parent;
    public int offset;
    private boolean open;
    private boolean hovered;
    int mousexx;
    int mouseyy;

    public ModuleButton(Module mod, Panel parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<>();
        this.open = false;
        int opY = offset + 12;

        if (Xena.settingsManager.getSettingsModule(mod) != null) {
            for (Setting setting : Xena.settingsManager.getSettingsModule(mod)) {
                if (setting.getType() == "boolean") {
                    this.subcomponents.add(new BooleanComponent(setting, this, opY));
                    opY += 12;
                }
                if (setting.getType() == "integer") {
                    this.subcomponents.add(new IntegerComponent(setting, this, opY));
                    opY += 12;
                }
                if (setting.getType() == "double") {
                    this.subcomponents.add(new DoubleComponent(setting, this, opY));
                    opY += 12;
                }
                if (setting.getType() == "mode") {
                    this.subcomponents.add(new ModeComponent(setting, this, opY));
                    opY += 12;
                }
            }
        }
        this.subcomponents.add(new KeybindComponent(this, opY));
    }

    @Override
    public void renderComponent() {
        if (Xena.settingsManager.getSettingID("OldClickGUIRainbow").getValBoolean() && this.mod.isToggled()) {
            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 12 + offset, RainbowUtil.getMultiColour().getRGB());
        } else if (this.mod.isToggled()) {
            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 12 + offset, 0xFF222222);
        } else {
            Gui.drawRect(parent.getX(), parent.getY() + offset, parent.getX() + parent.getWidth(), parent.getY() + 12 + offset, 0xFF111111);
        }

        FontUtil.drawText(this.mod.getName(), parent.getX() + 2, (parent.getY() + offset + 2), -1);

        if (this.subcomponents.size() > 1) {
            if (!this.isOpen()) {
                FontUtil.drawText("+", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
            } else if (this.isOpen()) {
                FontUtil.drawText("-", parent.getX() + parent.getWidth() - 10, (parent.getY() + offset + 2), -1);
            }
        }

        if (this.open && !this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }

        if (Xena.settingsManager.getSettingID("OldClickGUIDescriptions").getValBoolean() && hovered ) {
            if (Xena.settingsManager.getSettingID("OldClickGUIRainbow").getValBoolean()) {
                if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.latoFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, RainbowUtil.getMultiColour().getRGB());
                } else if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.verdanaFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, RainbowUtil.getMultiColour().getRGB());
                } else if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.arialFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, RainbowUtil.getMultiColour().getRGB());
                } else {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + mc.fontRenderer.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, RainbowUtil.getMultiColour().getRGB());
                }
            } else {
                if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Lato") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.latoFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, 0xFF222222);
                } else if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Verdana") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.verdanaFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, 0xFF222222);
                } else if (Xena.settingsManager.getSettingID("FontFont").getValueString() == "Arial") {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + Xena.arialFont.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, 0xFF222222);
                } else {
                    Gui.drawRect(mousexx - 2, mouseyy - 2, mousexx + mc.fontRenderer.getStringWidth(mod.getDescription()) + 2, mouseyy + FontUtil.getFontHeight() + 2, 0xFF222222);
                }
            }

            FontUtil.drawText(mod.getDescription(), mousexx, mouseyy, -1);
        }
    }

    @Override
    public void closeAllSub() {
        this.open = false;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);

        mousexx = mouseX + 10;
        mouseyy = mouseY - 5;

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
        return x > parent.getX ( ) && x < parent.getX ( ) + 80 && y > this.parent.getY ( ) + this.offset && y < this.parent.getY ( ) + 12 + this.offset;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
