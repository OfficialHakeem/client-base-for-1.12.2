package club.xena.client.impl.gui.click.clickone.components;

import club.xena.client.api.setting.Setting;
import club.xena.client.api.util.render.text.FontUtil;
import club.xena.client.impl.gui.click.Component;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;

public class ModeComponent extends Component {
    private final Setting op;
    private final ModuleButton parent;
    private final int offset;
    private int x;
    private int y;
    private int modeIndex;

    public ModeComponent(Setting op, ModuleButton parent, int offset) {
        this.op = op;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
        this.modeIndex = 0;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(parent.parent.getX() + 80, parent.parent.getY() - 12 + offset, parent.parent.getX() + parent.parent.getWidth() + parent.parent.getWidth(), parent.parent.getY() + offset, 0xFF111111);

        FontUtil.drawText(this.op.getName() + " " + ChatFormatting.GRAY + this.op.getValueString().toUpperCase(), parent.parent.getX() + 82, (parent.parent.getY() + offset - 10), -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.y = parent.parent.getY() - 12 + this.offset;
        this.x = parent.parent.getX() + 80;
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.isOpen()) {
            final int maxIndex = this.op.getModes().size() - 1;
            this.modeIndex++;
            if (this.modeIndex > maxIndex) {
                this.modeIndex = 0;
            }
            this.op.setValueString(this.op.getModes().get(this.modeIndex));
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 80 && y > this.y && y < this.y + 12;
    }
}
