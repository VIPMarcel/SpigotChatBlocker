package vip.marcel.chatblocker.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import vip.marcel.chatblocker.ChatBlocker;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigManager {

    private final ChatBlocker plugin;

    private final File path = new File("plugins/ChatBlocker");
    private final File file = new File(this.path, "config.yml");

    private final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);

    public ConfigManager(ChatBlocker plugin) {
        this.plugin = plugin;
        this.initConfig();
    }

    private void initConfig() {

        if(!this.path.exists())
            this.path.mkdir();

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.configuration.options().copyDefaults(true);
                this.configuration.addDefault("System.NoCheckingPermission", "chatblocker.pass");
                this.configuration.addDefault("System.NoPermissions", "&cDer Befehl &e\"{command}\" &cwurde nicht gefunden.");
                this.configuration.addDefault("System.AllowedCommands", Arrays.asList("/spawn", "/help"));
                this.configuration.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadConfigurations() {

    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

}
