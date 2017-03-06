package eu.crushedpixel.sponge.packetgate.plugin.netty;

import eu.crushedpixel.sponge.packetgate.api.registry.PacketConnection;
import eu.crushedpixel.sponge.packetgate.api.event.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.Packet;

@RequiredArgsConstructor
class OutgoingPacketHandler extends ChannelOutboundHandlerAdapter {

    private final PacketConnection packetConnection;

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Packet) {
            PacketEvent event = new PacketEvent((Packet)msg, true);

            try {
                packetConnection.handlePacketEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (event.isCancelled()) return;
            msg = event.getPacket();
        }

        super.write(ctx, msg, promise);
    }
}
