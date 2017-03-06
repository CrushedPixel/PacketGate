package eu.crushedpixel.sponge.packetgate.plugin.netty;

import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.Packet;

@RequiredArgsConstructor
class IncomingPacketHandler extends ChannelInboundHandlerAdapter {

    private final PacketConnection packetConnection;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            PacketEvent event = new PacketEvent((Packet)msg, false);

            try {
                packetConnection.handlePacketEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (event.isCancelled()) return;
            msg = event.getPacket();
        }

        super.channelRead(ctx, msg);
    }
}
