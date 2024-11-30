package com.hermitowo.tfcweldbutton.network;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.AnvilContainer;
import net.dries007.tfc.util.Helpers;

public class WeldButtonPacket
{
    public void handle(@Nullable ServerPlayer player)
    {
        if (player != null && player.containerMenu instanceof AnvilContainer container)
        {
            AnvilBlockEntity anvil = container.getBlockEntity();
            if (Helpers.isItem(anvil.getCapability(Capabilities.ITEM).map(cap -> cap.getStackInSlot(AnvilBlockEntity.SLOT_HAMMER)).orElse(ItemStack.EMPTY), TFCTags.Items.HAMMERS)
                || Helpers.isItem(player.getMainHandItem(), TFCTags.Items.HAMMERS))
            {
                final InteractionResult weldResult = anvil.weld(player);
                if (weldResult == InteractionResult.SUCCESS)
                {
                    final Level level = player.level();
                    final BlockPos pos = anvil.getBlockPos();
                    // Welding occurred
                    if (level instanceof ServerLevel server)
                    {
                        final double x = pos.getX() + Mth.nextDouble(level.random, 0.2, 0.8);
                        final double z = pos.getZ() + Mth.nextDouble(level.random, 0.2, 0.8);
                        final double y = pos.getY() + Mth.nextDouble(level.random, 0.8, 1.0);
                        server.sendParticles(TFCParticles.SPARK.get(), x, y, z, 8, 0, 0, 0, 0.2f);
                    }
                    level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 0.6f, 1.0f);
                }
            }
        }
    }
}
