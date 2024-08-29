package com.hermitowo.tfcweldbutton.network;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import com.hermitowo.tfcweldbutton.TFCWeldButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.mutable.MutableInt;

public class PacketHandler
{
    private static final String VERSION = ModList.get().getModFileById(TFCWeldButton.MOD_ID).versionString();
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(TFCWeldButton.MOD_ID, "network"), () -> VERSION, VERSION::equals, VERSION::equals);
    private static final MutableInt ID = new MutableInt(0);

    public static void send(PacketDistributor.PacketTarget target, Object message)
    {
        CHANNEL.send(target, message);
    }

    public static void init()
    {
        register(WeldButtonPacket.class, WeldButtonPacket::new, WeldButtonPacket::handle);
    }

    private static <T> void register(Class<T> cls, Supplier<T> factory, BiConsumer<T, ServerPlayer> handler)
    {
        CHANNEL.registerMessage(ID.getAndIncrement(), cls, (packet, buffer) -> {}, buffer -> factory.get(), (packet, context) -> {
            context.get().setPacketHandled(true);
            context.get().enqueueWork(() -> handler.accept(packet, context.get().getSender()));
        });
    }
}
