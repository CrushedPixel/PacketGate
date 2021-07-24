package eu.crushedpixel.sponge.packetgate.api;

import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import eu.crushedpixel.sponge.packetgate.plugin.PluginPacketGate;

public class PacketGateAPI {

    /**
     * Get {@link PacketGate}
     *
     * @return PacketGate instance to use
     */
    public PacketGate get() {
        return PluginPacketGate.packetGate;
    }

}
