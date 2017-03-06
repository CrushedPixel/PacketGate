package eu.crushedpixel.sponge.packetgate.api.listener;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import lombok.Data;
import net.minecraft.network.Packet;

import java.util.List;

public interface PacketListener {

    void onPacketRead(PacketEvent packetEvent, PacketConnection connection);

    void onPacketWrite(PacketEvent packetEvent, PacketConnection connection);

    @Data
    public class PacketListenerData {
        private final PacketListener packetListener;
        private final ListenerPriority priority;
        private final List<Class<? extends Packet>> classes;
    }

    public enum ListenerPriority {
        FIRST, EARLY, DEFAULT, LATE, LATEST
    }

}
