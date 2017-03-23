package eu.crushedpixel.sponge.packetgate.api.registry;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener.PacketListenerData;
import io.netty.channel.Channel;
import net.minecraft.network.Packet;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

public class PacketConnection extends ListenerOwner {

    private final Logger logger;

    private final Channel channel;

    private UUID playerUUID;

    public PacketConnection(Logger logger, Channel channel) {
        this.logger = logger;
        this.channel = channel;
    }

    public void sendPacket(Packet packet) {
        channel.write(packet);
    }

    public void handlePacketEvent(PacketEvent event) {
        List<PacketListenerData> list = packetListeners.get(event.getPacket().getClass());
        if (list == null) return;

        for (PacketListenerData data : list) {
            PacketListener packetListener = data.getPacketListener();

            if (event.isOutgoing()) {
                packetListener.onPacketWrite(event, this);
            } else {
                packetListener.onPacketRead(event, this);
            }
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
