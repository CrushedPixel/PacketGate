package eu.crushedpixel.sponge.packetgate.plugin.interfaces;

import java.util.UUID;

public interface IMixingClientboundGameProfilePacket {

    UUID uniqueId();
    String username();
}
