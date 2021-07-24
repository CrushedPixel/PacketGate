package eu.crushedpixel.sponge.packetgate.plugin;

import com.google.inject.Inject;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import eu.crushedpixel.sponge.packetgate.plugin.interfaces.IMixinServerConnectionListener;
import eu.crushedpixel.sponge.packetgate.plugin.netty.CustomChannelInitializer;
import io.netty.channel.ChannelFuture;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.plugin.jvm.Plugin;

import java.util.List;
import java.util.logging.Logger;

@Plugin(PluginPacketGate.ID)
public class PluginPacketGate {

    static final String ID = "packetgate";

    @Inject
    private Logger logger;

    private PacketGate packetGate;

    @Listener(order = Order.FIRST)
    public void init(final LoadedGameEvent event) {
        packetGate = new PacketGate();

        IMixinServerConnectionListener connection = (IMixinServerConnectionListener) ((MinecraftServer) Sponge.server()).getConnection();

        List<ChannelFuture> channels = connection.getChannels();
        channels.forEach(channelFuture -> {
            channelFuture.channel().pipeline().addFirst(new CustomChannelInitializer(logger, packetGate));
            logger.info("Successfully injected channel initializer into endpoint");
        });
    }
}