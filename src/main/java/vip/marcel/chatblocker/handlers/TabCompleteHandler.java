package vip.marcel.chatblocker.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.entity.Player;
import vip.marcel.chatblocker.ChatBlocker;
import vip.marcel.chatblocker.utils.TabInterceptor;

public class TabCompleteHandler extends TabInterceptor {

    private final ChatBlocker plugin;

    private PacketListener listener;

    public TabCompleteHandler(ChatBlocker plugin) {
        this.plugin = plugin;
        this.tabCompleteListening();
    }

    private void tabCompleteListening() {

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        manager.addPacketListener(
                listener = new PacketAdapter(this.plugin, PacketType.Play.Server.TAB_COMPLETE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if(event.getPacketType() == PacketType.Play.Server.TAB_COMPLETE) {
                    final Player player = event.getPlayer();

                    event.setCancelled(isCompletionCancelled(player, TabCompleteHandler.this.plugin.getIgnorePermission(), event.getPacket().getStrings().read(0)));

                }
            }
        });

    }

    @Override
    public void close() {
        if(listener != null) {
            ProtocolLibrary.getProtocolManager().removePacketListener(listener);
            listener = null;
        }
    }

}
