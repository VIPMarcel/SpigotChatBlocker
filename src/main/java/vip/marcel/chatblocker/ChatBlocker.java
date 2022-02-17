package vip.marcel.chatblocker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import vip.marcel.chatblocker.handlers.ChatHandler;
import vip.marcel.chatblocker.listeners.PlayerCommandSendListener;
import vip.marcel.chatblocker.managers.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ChatBlocker extends JavaPlugin {

    private ConfigManager configManager;

    private ChatHandler chatHandler;

    private PluginManager pluginManager;

    private String noPermissionsMessage, ignorePermission;
    private List<String> allowedCommands;

    @Override
    public void onEnable() {

        if(getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            this.getLogger().log(Level.SEVERE, "Das Plugin benötigt ProtocolLib um zu laden");
            return;
        }

        this.init();

        this.getLogger().log(Level.INFO, "Das Plugin wurde erfolgreich geladen");
    }

    private void init() {
        this.configManager = new ConfigManager(this);
        this.ignorePermission = this.configManager.getConfiguration().getString("System.NoCheckingPermission");
        this.noPermissionsMessage = this.configManager.getConfiguration().getString("System.NoPermissions");

        this.allowedCommands = new ArrayList<>();
        this.configManager.getConfiguration().getStringList("System.AllowedCommands").forEach(allowedCommand -> {
            this.allowedCommands.add(allowedCommand.toLowerCase());
        });

        this.chatHandler = new ChatHandler(this);

        this.pluginManager = Bukkit.getServer().getPluginManager();
        this.pluginManager.registerEvents(new PlayerCommandSendListener(this), this);

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
