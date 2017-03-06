package eu.crushedpixel.sponge.packetgate.api.event;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

public class PacketEvent {

    public PacketEvent(Packet packet, boolean outgoing) {
        this.packet = packet;
        this.outgoing = outgoing;
    }

    @Getter @Setter
    private Packet packet;

    @Getter @Setter
    private boolean cancelled = false;

    @Getter
    private final boolean outgoing;

}
