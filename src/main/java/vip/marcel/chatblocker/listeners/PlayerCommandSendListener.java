package vip.marcel.chatblocker.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import vip.marcel.chatblocker.ChatBlocker;

public class PlayerCommandSendListener implements Listener {

    private final ChatBlocker plugin;

    public PlayerCommandSendListener(ChatBlocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) {
        final Player player = event.getPlayer();

        if(!player.hasPermission(this.plugin.getIgnorePermission())) {

            event.getCommands().removeAll(event.getCommands());

            for(String allowedCommand : this.plugin.getAllowedCommands()) {
                event.getCommands().add(allowedCommand.replaceFirst("/", "").toLowerCase());
            }

        }

    }

}
