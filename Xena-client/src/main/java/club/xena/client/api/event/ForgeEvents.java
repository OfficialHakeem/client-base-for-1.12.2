package club.xena.client.api.event;

import club.xena.client.Xena;
import club.xena.client.api.module.Module;
import club.xena.client.impl.gui.editor.component.HudComponent;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class ForgeEvents {

    public ForgeEvents() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_NONE || Keyboard.getEventKey() == Keyboard.CHAR_NONE) return;
            for (Module modules : Xena.moduleManager.getModules()) {
                if (modules.getKey() == Keyboard.getEventKey()) {
                    modules.toggle();
                }
            }
//            Xena.EVENT_BUS.post(event);
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Module module : Xena.moduleManager.getModules()) {
            if (module.isToggled()) {
                module.onUpdate();
            }
        }
//        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        for (HudComponent hudComponent : Xena.hudComponentManager.getHudComponents()) {
            if (hudComponent.isEnabled()) {
                hudComponent.render(event.getPartialTicks());
            }
        }
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fovEvent(EntityViewRenderEvent.FOVModifier event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fogColours(EntityViewRenderEvent.FogColors event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void renderBlockOverlay(RenderBlockOverlayEvent event) {
        Xena.EVENT_BUS.post(event);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        Xena.EVENT_BUS.post(event);
    }
}
