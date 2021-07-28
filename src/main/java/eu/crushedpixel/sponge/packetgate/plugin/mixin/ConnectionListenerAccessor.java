package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import io.netty.channel.ChannelFuture;
import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerConnectionListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ServerConnectionListener.class)
public interface ConnectionListenerAccessor {

    @Accessor("channels")
    List<ChannelFuture> accessor$channels();

    @Accessor("connections")
    List<Connection> accessor$connections();

}
