package eu.crushedpixel.sponge.packetgate.plugin.mixin;

import com.mojang.authlib.GameProfile;
import eu.crushedpixel.sponge.packetgate.plugin.interfaces.IMixinClientboundGameProfilePacket;
import net.minecraft.network.protocol.login.ClientboundGameProfilePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(ClientboundGameProfilePacket.class)
public abstract class MixinClientboundGameProfilePacket implements IMixinClientboundGameProfilePacket {

    @Shadow
    private GameProfile gameProfile;

    @Override
    public UUID uniqueId() {
        return this.gameProfile.getId();
    }

    @Override
    public String username() {
        return this.gameProfile.getName();
    }
}
