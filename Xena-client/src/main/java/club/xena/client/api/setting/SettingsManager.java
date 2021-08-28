package club.xena.client.api.setting;

import club.xena.client.Xena;
import club.xena.client.api.module.Module;
import club.xena.client.impl.gui.editor.component.HudComponent;

import java.util.ArrayList;

public class SettingsManager {
    private final ArrayList<Setting> settings;

    public SettingsManager() {
        this.settings = new ArrayList<>();
    }

    public void registerSetting(Setting args) {
        this.settings.add(args);
    }

    public ArrayList<Setting> getSettings() {
        return this.settings;
    }

    public Setting getSettingName(String name) {
        for (Setting setting : getSettings()) {
            if (setting.getName() == name) {
                return setting;
            }
        }
        Xena.log("Setting error, setting `" + name + "` NOT found!");
        return null;
    }

    public Setting getSettingID(String id) {
        for (Setting setting : getSettings()) {
            if (setting.getId() == id) {
                return setting;
            }
        }
        Xena.log("Setting error, setting id `" + id + "` NOT found!");
        return null;
    }

    public ArrayList<Setting> getSettingsModule(Module module) {
        ArrayList<Setting> list = new ArrayList<>();
        for (Setting setting : getSettings()) {
            if (setting.getParent() == module) {
                list.add(setting);
            }
        }
        return list;
    }

    public ArrayList<Setting> getSettingsHudComponent(HudComponent hudComponent) {
        ArrayList<Setting> list = new ArrayList<>();
        for (Setting setting : getSettings()) {
            if (setting.getHudParent() == hudComponent) {
                list.add(setting);
            }
        }
        return list;
    }
}
