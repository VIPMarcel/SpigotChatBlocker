package vip.marcel.chatblocker.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import vip.marcel.chatblocker.ChatBlocker;

import java.util.logging.Level;

public class ChatHandler {

    private final ChatBlocker plugin;

    public ChatHandler(ChatBlocker plugin) {
        this.plugin = plugin;
        this.chatListening();
    }

    private void chatListening() {

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Client.CHAT) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(event.getPacketType() == PacketType.Play.Client.CHAT) {
                    final Player player = event.getPlayer();

                    String message = event.getPacket().getStrings().read(0);
                    String[] commandArray = message.split(" ");

                    String commandPermission = ChatHandler.this.plugin.getIgnorePermission() + "." + commandArray[0].toLowerCase().replaceFirst("/", "");

                    if(commandArray[0].startsWith("/")) {

                        if(player.hasPermission(ChatHandler.this.plugin.getIgnorePermission()))
                            return;

                        if(ChatHandler.this.plugin.getAllowedCommands().contains(commandArray[0].toLowerCase())) {

                            if(player.hasPermission(commandPermission))
                                return;

                            String noPermissions = ChatColor.translateAlternateColorCodes('&', ChatHandler.this.plugin.getNoPermissionsMessage()).replace("{command}", commandArray[0]);
                            player.sendMessage(noPermissions);

                            plugin.getLogger().log(Level.INFO, player.getName() + " tried server command: " + message);

                            event.setCancelled(true);

                        }
                        else {

                            String noPermissions = ChatColor.translateAlternateColorCodes('&', ChatHandler.this.plugin.getNoPermissionsMessage()).replace("{command}", commandArray[0]);
                            player.sendMessage(noPermissions);

                            plugin.getLogger().log(Level.INFO, player.getName() + " tried server command: " + message);

                            event.setCancelled(true);

                        }

                    }

                }
            }
        });

    }

}
