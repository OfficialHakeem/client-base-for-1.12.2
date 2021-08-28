package club.xena.client;

import club.xena.client.api.cape.CapesManager;
import club.xena.client.api.command.CommandManager;
import club.xena.client.api.event.ForgeEvents;
import club.xena.client.api.friend.FriendsManager;
import club.xena.client.api.module.ModuleManager;
import club.xena.client.api.setting.SettingsManager;
import club.xena.client.api.util.config.ConfigUtil;
import club.xena.client.api.util.render.text.font.CustomFontRenderer;
import club.xena.client.impl.gui.click.clickone.ClickGUIOne;
import club.xena.client.impl.gui.click.clicktwo.ClickGUITwo;
import club.xena.client.impl.gui.editor.component.HudComponentManager;
import club.xena.client.impl.gui.editor.screen.HudEditor;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(name = Xena.NAME, modid = Xena.MOD_ID, version = Xena.VERSION)
public class Xena {
    public static final String NAME = "Xena Utility Mod";
    public static final String MOD_ID = "xena";
    public static final String VERSION = "b1";
    public static final String APP_ID = "754509326902886411";
    public static String NAME_VERSION = NAME + " " + VERSION;

    public static final Logger LOGGER = LogManager.getLogger(NAME_VERSION);

    public static CustomFontRenderer latoFont;
    public static CustomFontRenderer verdanaFont;
    public static CustomFontRenderer arialFont;

    public static EventBus EVENT_BUS;
    public static ForgeEvents forgeEvents;
    public static SettingsManager settingsManager;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static FriendsManager friendsManager;
    public static CapesManager capesManager;
    public static HudComponentManager hudComponentManager;
    public static ClickGUIOne clickGUIOne;
    public static ClickGUITwo clickGUITwo;
    public static HudEditor hudEditor;
    public static ConfigUtil configUtil;

    @Mod.EventHandler
    public void pastPreInitialize(FMLPreInitializationEvent event) {
        Display.setTitle(NAME_VERSION);
    }

    @Mod.EventHandler
    public void pastInitialize(FMLInitializationEvent event) {
        log("Starting Client Initialization!");

        EVENT_BUS = new EventManager();
        log("Event System Initialized!");

        forgeEvents = new ForgeEvents();
        log("Forge Events Initialized!");

        settingsManager = new SettingsManager();
        log("Settings initialized!");

        moduleManager = new ModuleManager();
        log("Modules Initialized!");

        commandManager = new CommandManager();
        log("Commands Initialized!");

        friendsManager = new FriendsManager();
        log("Friends Initialized!");

        latoFont = new CustomFontRenderer(new Font("Lato", 0, 18), true, false);
        verdanaFont = new CustomFontRenderer(new Font("Verdana", 0, 18), true, false);
        arialFont = new CustomFontRenderer(new Font("Arial", 0, 18), true, false);
        log("Custom Fonts Initialized! (Author cumhax)!");

        capesManager = new CapesManager();
        log("Capes Initialized!");

        hudComponentManager = new HudComponentManager();
        log("HUD Components Initialized!");

        clickGUIOne = new ClickGUIOne();
        log("ClickGUI One Initialized!");

        clickGUITwo = new ClickGUITwo();
        log("ClickGUI Two Initialized!");

        hudEditor = new HudEditor();
        log("HUD Editor Initialized!");

        configUtil = new ConfigUtil();
        log("Config Initialized!");

        log("Client has finished initializing!");
    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}