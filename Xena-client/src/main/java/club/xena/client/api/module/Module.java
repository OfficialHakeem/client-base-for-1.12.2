package club.xena.client.api.module;

import club.xena.client.Xena;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name = getAnnotation().name();
    private String description = getAnnotation().description();
    private Category category = getAnnotation().category();
    private Integer key = getAnnotation().key();

    private boolean toggled;

    public Module() {
        toggled = false;
        setup();
    }

    private ModuleInfo getAnnotation() {
        if (getClass().isAnnotationPresent(ModuleInfo.class)) {
            return getClass().getAnnotation(ModuleInfo.class);
        }
        throw new IllegalStateException("No annotation present!");
    }

    public void toggle() {
        toggled = !toggled;
        onToggle();
        if (toggled) {
            onEnableEvent();
        } else {
            onDisableEvent();
        }
    }

    public boolean isToggled() {
        return toggled;
    }

    public void onEnableEvent() {
        Xena.EVENT_BUS.subscribe(this);

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveLoadedModules();
            } catch (Exception e) {}
        }

        onEnable();
    }

    public void onDisableEvent() {
        Xena.EVENT_BUS.unsubscribe(this);

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveLoadedModules();
            } catch (Exception e) {}
        }

        onDisable();
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;

        if (Xena.configUtil != null) {
            try {
                Xena.configUtil.saveKeybinds();
            } catch (Exception e) {}
        }
    }

    public String getArraylistInfo() {
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void onToggle() {}

    public void onUpdate() {}

    public void setup() {}

    public void onEnable() {}

    public void onDisable() {}
}