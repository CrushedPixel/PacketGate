package eu.crushedpixel.sponge.packetgate.api.listener;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import lombok.Data;

public interface PacketListener {

    void onPacketRead(PacketEvent packetEvent, PacketConnection connection);

    void onPacketWrite(PacketEvent packetEvent, PacketConnection connection);

    @Data
    public class PacketListenerData {
        private final PacketListener packetListener;
        private final ListenerPriority priority;
    }

    public enum ListenerPriority {
        FIRST, EARLY, DEFAULT, LATE, LAST
    }

}
