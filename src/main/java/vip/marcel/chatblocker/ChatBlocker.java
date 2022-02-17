package vip.marcel.chatblocker;

import org.bukkit.plugin.java.JavaPlugin;
import vip.marcel.chatblocker.handlers.ChatHandler;
import vip.marcel.chatblocker.handlers.TabCompleteHandler;
import vip.marcel.chatblocker.managers.ConfigManager;
import vip.marcel.chatblocker.utils.TabInterceptor;

import java.util.Vector;
import java.util.logging.Level;

public class ChatBlocker extends JavaPlugin {

    private ConfigManager configManager;

    private ChatHandler chatHandler;

    private TabInterceptor tabInterceptor;

    private String noPermissionsMessage, ignorePermission;
    private Vector<String> allowedCommands;

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

        this.allowedCommands = new Vector<>();
        this.configManager.getConfiguration().getStringList("System.AllowedCommands").forEach(allowedCommand -> {
            this.allowedCommands.add(allowedCommand.toLowerCase());
        });

        this.chatHandler = new ChatHandler(this);

        this.tabInterceptor = new TabCompleteHandler(this);

    }

    @Override
    public void onDisable() {
        if(this.tabInterceptor != null) {
            this.tabInterceptor.close();
            this.tabInterceptor = null;
        }
    }

    public String getNoPermissionsMessage() {
        return this.noPermissionsMessage;
    }

    public String getIgnorePermission() {
        return this.ignorePermission;
    }

    public Vector<String> getAllowedCommands() {
        return this.allowedCommands;
    }

}
