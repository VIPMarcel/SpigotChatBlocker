package vip.marcel.chatblocker;

import jdk.jfr.internal.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ChatBlocker extends JavaPlugin {

    @Override
    public void onEnable() {
        init();

        this.getLogger().log(Level.INFO, "Das Plugin wurde erfolgreich geladen");
    }

    @Override
    public void onDisable() {

    }

    private void init() {

    }

}
