package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ConnectionProtocol.class)
public interface ConnectionProtocolFlowAccessor {

    @Accessor("flows") Map<PacketFlow, ?> accessor$flows();

}
