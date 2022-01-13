package eu.crushedpixel.sponge.packetgate.plugin;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import com.google.inject.Inject;

import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import eu.crushedpixel.sponge.packetgate.plugin.mixin.ConnectionListenerAccessor;
import eu.crushedpixel.sponge.packetgate.plugin.netty.CustomChannelInitializer;
import io.netty.channel.ChannelFuture;
import net.minecraft.server.MinecraftServer;

@Plugin("packetgate")
public class PluginPacketGate {

    @Inject
    private Logger logger;

    public static PacketGate packetGate = new PacketGate();

    @Listener(order = Order.FIRST)
    public void init(final LoadedGameEvent event) {
        ConnectionListenerAccessor connection = (ConnectionListenerAccessor) ((MinecraftServer) Sponge.server()).getConnection();
        List<ChannelFuture> channels = connection.accessor$channels();
        channels.forEach(channelFuture -> {
            channelFuture.channel().pipeline().addFirst(new CustomChannelInitializer(logger, packetGate));
            logger.info("Successfully injected channel initializer into endpoint");
        });
    }
}