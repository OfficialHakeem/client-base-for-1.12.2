package club.xena.client.impl.gui.click.clickone.components;

import club.xena.client.Xena;
import club.xena.client.api.setting.Setting;
import club.xena.client.api.util.colour.RainbowUtil;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import net.minecraft.client.gui.Gui;

public class BooleanComponent extends Component {
    private final Setting op;
    private final ModuleButton parent;
    private final int offset;
    private int x;
    private int y;

    public BooleanComponent(Setting op, ModuleButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        if (Xena.settingsManager.getSettingID("OldClickGUIRainbow").getValBoolean() && this.op.getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, RainbowUtil.getMultiColour().getRGB());
        } else if (this.op.getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, 0xFF222222);
        } else {
            Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, 0xFF111111);
        }

        FontUtil.drawText(this.op.getName(), parent.parent.getX() + 82, (parent.parent.getY() + offset - 10), -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            this.op.setValBoolean(!op.getValBoolean());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12;
    }
}