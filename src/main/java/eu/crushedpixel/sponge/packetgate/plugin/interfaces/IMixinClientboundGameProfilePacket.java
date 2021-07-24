package eu.crushedpixel.sponge.packetgate.plugin.interfaces;

import java.util.UUID;

public interface IMixinClientboundGameProfilePacket {

    UUID uniqueId();
    String username();
}
