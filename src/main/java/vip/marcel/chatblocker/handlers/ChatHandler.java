package vip.marcel.chatblocker.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import vip.marcel.chatblocker.ChatBlocker;

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
                final Player player = event.getPlayer();

                String command = event.getPacket().getStrings().read(0);
                String[] commandArray = command.split(" ");

                if(!player.hasPermission(ChatHandler.this.plugin.getIgnorePermission()) && command.startsWith("/")) {

                    ChatHandler.this.plugin.getAllowedCommands().forEach(allowedCommands -> {

                        if(!allowedCommands.contains(commandArray[0])) {

                            String noPermissions = ChatColor.translateAlternateColorCodes('&', ChatHandler.this.plugin.getNoPermissionsMessage()).replace("{command}", commandArray[0]);
                            player.sendMessage(noPermissions);

                            event.setCancelled(true);
                        }

                    });

                }

            }
        });

    }

}
