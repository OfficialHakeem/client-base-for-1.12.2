package club.xena.client.impl.gui.click.clicktwo.components;

import club.xena.client.Xena;
import club.xena.client.api.util.colour.GUIColourUtil;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindComponent extends Component {
    private boolean isBinding;
    private final ModuleButton parent;
    private int offset;
    private int x;
    private int y;
    private String points;
    private float tick;

    public KeybindComponent(ModuleButton parent, int offset) {
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.points = ".";
        this.tick = 0;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX(), parent.parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + 15 + offset, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() - 1, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() + 1, parent.parent.getY() + offset + 16, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, 0xFF111111);

        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 1, parent.parent.getY() + offset + 15, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());
        Gui.drawRect(parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth() - 1, parent.parent.getY() + offset + 15, Xena.settingsManager.getSettingID("ClickGUIRainbow").getValBoolean() ? RainbowUtil.getMultiColour().getRGB() : GUIColourUtil.getGUIColour());

        if (isBinding) {
            tick += 0.5f;

            FontUtil.drawText("Listening" + ChatFormatting.GRAY + " " + points, parent.parent.getX() + 4, parent.parent.getY() + offset + 4, -1);
        } else {
            FontUtil.drawText("Bind" + ChatFormatting.GRAY + " " + Keyboard.getKeyName(this.parent.mod.getKey()), parent.parent.getX() + 4, parent.parent.getY() + offset + 4, -1);
        }

        if (isBinding) {
            if (tick >= 15) {
                points = "..";
            }
            if (tick >= 30) {
                points = "...";
            }
            if (tick >= 45) {
                points = ".";
                tick = 0.0f;
            }
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() + this.offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.isBinding = !this.isBinding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (this.isBinding) {
            if (Keyboard.isKeyDown(Keyboard.KEY_DELETE)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                this.parent.mod.setKey(Keyboard.KEY_NONE);
                this.isBinding = false;
            } else {
                this.parent.mod.setKey(key);
                this.isBinding = false;
            }
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
