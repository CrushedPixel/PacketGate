package eu.crushedpixel.sponge.packetgate.api.listener;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;

public interface PacketListener {

    void onPacketRead(PacketEvent packetEvent, PacketConnection connection);

    void onPacketWrite(PacketEvent packetEvent, PacketConnection connection);

    public class PacketListenerData {
        private final PacketListener packetListener;
        private final ListenerPriority priority;

        public PacketListenerData(PacketListener packetListener, ListenerPriority priority) {
            this.packetListener = packetListener;
            this.priority = priority;
        }

        public PacketListener getPacketListener() {
            return packetListener;
        }

        public ListenerPriority getPriority() {
            return priority;
        }
    }

    public enum ListenerPriority {
        FIRST, EARLY, DEFAULT, LATE, LAST
    }

}
