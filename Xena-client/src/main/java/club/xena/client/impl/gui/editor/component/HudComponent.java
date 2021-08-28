package club.xena.client.impl.gui.editor.component;

import net.minecraft.client.Minecraft;

public class HudComponent {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name;
    private boolean enabled;
    private boolean dragging;
    private int x = 2;
    private int y = 2;
    private int width;
    private int height;
    private int dragX;
    private int dragY;

    public HudComponent(String name) {
        this.name = name;
        setup();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDragX() {
        return dragX;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public int getDragY() {
        return dragY;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public boolean isMouseOnComponent(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.dragging) {
            this.setX(mouseX - getDragX());
            this.setY(mouseY - getDragY());
        }
    }

    public void render(float ticks) {}

    public void setup() {}
}
