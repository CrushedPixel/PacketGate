package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import eu.crushedpixel.sponge.packetgate.plugin.interfaces.IMixinConnectionProtocol;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.PacketFlow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ConnectionProtocol.class)
public abstract class MixinConnectionProtocol implements IMixinConnectionProtocol {

    @Shadow
    @Final
    private Map<PacketFlow, ?> flows;

    @Override
    public Map<PacketFlow, ?> getFlows() {
        return this.flows;
    }
}
