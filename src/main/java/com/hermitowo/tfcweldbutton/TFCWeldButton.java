package com.hermitowo.tfcweldbutton;

import com.hermitowo.tfcweldbutton.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;

@Mod(TFCWeldButton.MOD_ID)
public class TFCWeldButton
{
    public static final String MOD_ID = "tfcweldbutton";

    public TFCWeldButton()
    {
        PacketHandler.init();
    }
}
