package com.usagin.juicecraft.network;

import com.mojang.logging.LogUtils;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import org.slf4j.Logger;

public class ToClientCircleParticlePacket {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final double radius;
    ParticleOptions part;
    private final int id;

    public <T extends ParticleOptions> ToClientCircleParticlePacket(int id, double radius, T part) {
        this.id = id;
        this.part = part;
        this.radius = radius;
    }


    //should be same order as write apparently
    public ToClientCircleParticlePacket(FriendlyByteBuf buffer) {
        var n = buffer.readById(BuiltInRegistries.PARTICLE_TYPE);
        this.id = buffer.readVarInt();
        this.radius = buffer.readDouble();
        this.part = this.readParticle(buffer, n);
    }

    private <T extends ParticleOptions> T readParticle(FriendlyByteBuf pBuffer, ParticleType<T> pParticleType) {
        return pParticleType.getDeserializer().fromNetwork(pParticleType, pBuffer);
    }

    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeId(BuiltInRegistries.PARTICLE_TYPE, this.part.getType());
        pBuffer.writeVarInt(this.id);
        pBuffer.writeDouble(this.radius);
    }

    public void handle(CustomPayloadEvent.Context context) {
        if (context.getConnection().getPacketListener() instanceof ClientPacketListener listener) {
            Level level = listener.getLevel();
            LivingEntity source = decodeBuffer(level, this.id);
            if (source != null) {
                for (int i = 0; i < 360; i++) {
                    if (i % 10 == 0) {
                        level.addParticle(this.part, source.getX(), source.getY(), source.getZ(), radius * Math.cos(Math.toRadians(i)), 0.1F, radius * Math.sin(Math.toRadians(i)));
                    }
                }
            }
        }
    }

    public static LivingEntity decodeBuffer(Level level, int i) {
        return level.getEntity(i) instanceof LivingEntity friend ? friend : null;
    }
}
