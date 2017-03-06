package eu.crushedpixel.sponge.packetgate.api.registry;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener;
import eu.crushedpixel.sponge.packetgate.api.listener.PacketListener.PacketListenerData;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.network.Packet;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class PacketConnection extends ListenerOwner {

    private final Logger logger;

    @Getter
    private final Channel channel;

    @Getter @Setter
    private UUID playerUUID;

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

}
