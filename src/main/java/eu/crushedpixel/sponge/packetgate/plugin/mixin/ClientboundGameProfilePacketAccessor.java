package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientboundGameProfilePacket.class)
public interface ClientboundGameProfilePacketAccessor {

    @Accessor("gameProfile") GameProfile accessor$gameProfile();

}
