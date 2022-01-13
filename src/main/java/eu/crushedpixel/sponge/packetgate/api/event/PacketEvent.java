package eu.crushedpixel.sponge.packetgate.api.event;


import net.minecraft.network.protocol.Packet;

public class PacketEvent {

    public PacketEvent(Packet<?> packet, boolean outgoing) {
        this.packet = packet;
        this.outgoing = outgoing;
    }

    private Packet<?> packet;

    private boolean cancelled = false;

    private final boolean outgoing;

    public Packet<?> packet() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isOutgoing() {
        return outgoing;
    }
}
