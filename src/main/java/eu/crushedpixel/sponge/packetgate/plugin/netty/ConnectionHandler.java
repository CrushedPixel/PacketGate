package eu.crushedpixel.sponge.packetgate.plugin.netty;

import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketGate;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.login.server.SPacketLoginSuccess;

@ChannelHandler.Sharable
class ConnectionHandler extends ChannelDuplexHandler {

    private final PacketGate packetGate;
    private final PacketConnection connection;

    private boolean injected = false;

    public ConnectionHandler(PacketGate packetGate, PacketConnection connection) {
        this.packetGate = packetGate;
        this.connection = connection;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!injected) {
            IncomingPacketHandler incomingPacketHandler = new IncomingPacketHandler(connection);
            OutgoingPacketHandler outgoingPacketHandler = new OutgoingPacketHandler(connection);

            ctx.pipeline().remove(this).addBefore("packet_handler", "packetgate_listener", this);
            ctx.pipeline().addAfter("decoder", "packetgate_incoming", incomingPacketHandler);
            ctx.pipeline().addAfter("packet_handler", "packetgate_outgoing", outgoingPacketHandler);

            packetGate.registerConnection(connection);

            injected = true;
        }
        super.channelActive(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof SPacketLoginSuccess) {
            SPacketLoginSuccess packetLoginSuccess = (SPacketLoginSuccess)msg;
            connection.setPlayerUUID(packetLoginSuccess.profile.getId());
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (injected) {
            packetGate.unregisterConnection(connection);
        }
        super.channelInactive(ctx);
    }
}
