package vip.marcel.chatblocker;

import jdk.jfr.internal.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vip.marcel.chatblocker.handlers.ChatHandler;
import vip.marcel.chatblocker.managers.ConfigManager;

import java.util.List;
import java.util.logging.Level;

public class ChatBlocker extends JavaPlugin {

    private ConfigManager configManager;

    private ChatHandler chatHandler;

    private String noPermissionsMessage, ignorePermission;
    private List<String> allowedCommands;

    @Override
    public void onEnable() {
        this.init();

        this.getLogger().log(Level.INFO, "Das Plugin wurde erfolgreich geladen");
    }

    private void init() {
        this.configManager = new ConfigManager(this);
        this.ignorePermission = this.configManager.getConfiguration().getString("System.NoCheckingPermission");
        this.noPermissionsMessage = this.configManager.getConfiguration().getString("System.NoPermissions");
        this.allowedCommands = this.configManager.getConfiguration().getStringList("System.AllowedCommands");

        this.chatHandler = new ChatHandler(this);
    }

    public String getNoPermissionsMessage() {
        return this.noPermissionsMessage;
    }

    public String getIgnorePermission() {
        return this.ignorePermission;
    }

    public List<String> getAllowedCommands() {
        return this.allowedCommands;
    }

}
