package com.hermitowo.tfcweldbutton.mixin.client;

import com.hermitowo.tfcweldbutton.client.screen.button.AnvilWeldButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.dries007.tfc.client.screen.AnvilScreen;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.common.blockentities.AnvilBlockEntity;
import net.dries007.tfc.common.container.AnvilContainer;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends BlockEntityScreen<AnvilBlockEntity, AnvilContainer>
{
    public AnvilScreenMixin(AnvilContainer container, Inventory playerInventory, Component name, ResourceLocation texture)
    {
        super(container, playerInventory, name, texture);
    }

    @Inject(method = "init", at = @At(value = "TAIL"))
    protected void init(CallbackInfo ci)
    {
        addRenderableWidget(new AnvilWeldButton(getGuiLeft(), getGuiTop()));
    }
}
