package club.xena.client.impl.gui.click.clickone.components;

import club.xena.client.Xena;
import club.xena.client.api.setting.Setting;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class IntegerComponent extends Component {
    private final Setting set;
    private final ModuleButton parent;
    private final int offset;
    private int x;
    private int y;
    private boolean dragging;
    private double sliderWidth;

    public IntegerComponent(Setting value, ModuleButton button, int offset) {
        this.dragging = false;
        this.set = value;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, 0xFF111111);
        if (Xena.settingsManager.getSettingID("OldClickGUIRainbow").getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + (int) sliderWidth, parent.parent.getY() + offset, RainbowUtil.getMultiColour().getRGB());
        } else {
            Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + (int) sliderWidth, parent.parent.getY() + offset, 0xFF222222);
        }

        FontUtil.drawText(this.set.getName() + " " + ChatFormatting.GRAY + this.set.getValueInt(), parent.parent.getX() + 82, (parent.parent.getY() + offset - 10), -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
        double diff = Math.min(80, Math.max(0, mouseX - this.x));
        int min = this.set.getMin();
        int max = this.set.getMax();
        this.sliderWidth = 80 * (this.set.getValueInt() - min) / (max - min);
        if (this.dragging) {
            if (diff == 0) {
                this.set.setValueInt(this.set.getMin());
            } else {
                int newValue = (int) roundToPlace(diff / 80 * (max - min) + min, 2);
                this.set.setValueInt(newValue);
            }
        }
    }

    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12;
    }
}