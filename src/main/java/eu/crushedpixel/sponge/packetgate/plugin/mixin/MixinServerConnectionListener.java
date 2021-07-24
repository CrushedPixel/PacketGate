package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import eu.crushedpixel.sponge.packetgate.plugin.interfaces.IMixinServerConnectionListener;
import io.netty.channel.ChannelFuture;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerConnectionListener;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(ServerConnectionListener.class)
public abstract class MixinServerConnectionListener implements IMixinServerConnectionListener {

    @Shadow
    @Final
    private List<ChannelFuture> channels;

    @Shadow
    @Final
    private List<Connection> connections;

    public List<ChannelFuture> getChannels() {
        return this.channels;
    }

    public List<Connection> getConnections() {
        return this.connections;
    }
}
