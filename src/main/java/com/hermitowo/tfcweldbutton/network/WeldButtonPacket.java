package com.hermitowo.tfcweldbutton.network;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

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
                container.getBlockEntity().weld(player);
        }
    }
}
