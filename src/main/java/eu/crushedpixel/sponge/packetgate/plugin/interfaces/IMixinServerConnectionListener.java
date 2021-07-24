package eu.crushedpixel.sponge.packetgate.plugin.interfaces;

import io.netty.channel.ChannelFuture;
import net.minecraft.network.Connection;

import java.util.List;

public interface IMixinServerConnectionListener {

    List<ChannelFuture> getChannels();
    List<Connection> getConnections();

}
