package eu.crushedpixel.sponge.packetgate.api.listener;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;

public abstract class PacketListenerAdapter implements PacketListener {

    @Override
    public void onPacketRead(PacketEvent packetEvent, PacketConnection connection) {}

    @Override
    public void onPacketWrite(PacketEvent packetEvent, PacketConnection connection) {}

}
