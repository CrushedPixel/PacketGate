package eu.crushedpixel.sponge.packetgate.plugin.interfaces;

import net.minecraft.network.protocol.PacketFlow;

import java.util.Map;

public interface IMixinConnectionProtocol {

    Map<PacketFlow, ?> getFlows();

}
