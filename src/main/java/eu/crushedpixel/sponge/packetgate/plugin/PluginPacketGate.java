package eu.crushedpixel.sponge.packetgate.plugin;

import com.google.inject.Inject;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import eu.crushedpixel.sponge.packetgate.plugin.netty.CustomChannelInitializer;
import io.netty.channel.ChannelFuture;
import net.minecraft.network.NetworkSystem;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.List;

@Plugin(id = PluginPacketGate.ID, version = PluginPacketGate.VERSION)
public class PluginPacketGate {

    static final String ID = "packetgate";
    static final String VERSION = "0.1.1";

    @Inject
    private Logger logger;

    @Listener(order = Order.FIRST)
    public void init(GameInitializationEvent event) {
        PacketGate packetGate = new PacketGate();
        Sponge.getServiceManager().setProvider(this, PacketGate.class, packetGate);

        NetworkSystem networkSystem = ((MinecraftServer) Sponge.getServer()).getNetworkSystem();
        try {
            Field f = networkSystem.getClass().getDeclaredField("field_151274_e");
            f.setAccessible(true);
            List<ChannelFuture> endpoints = (List<ChannelFuture>) f.get(networkSystem);
            endpoints.forEach(channelFuture -> {
                channelFuture.channel().pipeline().addFirst(new CustomChannelInitializer(logger, packetGate));
                logger.info("Successfully injected channel initializer into endpoint");
            });
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }

}