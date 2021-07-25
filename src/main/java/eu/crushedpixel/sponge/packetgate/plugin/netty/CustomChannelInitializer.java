package eu.crushedpixel.sponge.packetgate.plugin.netty;

import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.logging.log4j.Logger;

public class CustomChannelInitializer extends ChannelInboundHandlerAdapter {

    private final Logger logger;
    private final PacketGate packetGate;

    public CustomChannelInitializer(Logger logger, PacketGate packetGate) {
        this.logger = logger;
        this.packetGate = packetGate;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = (Channel)msg;
        PacketConnection packetConnection = new PacketConnection(logger, channel);
        channel.pipeline().addLast(new ConnectionHandler(packetGate, packetConnection));
        super.channelRead(ctx, msg);
    }

}
