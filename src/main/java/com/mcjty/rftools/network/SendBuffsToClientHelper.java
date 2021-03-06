package com.mcjty.rftools.network;

import com.mcjty.rftools.PlayerBuff;
import com.mcjty.rftools.RenderGameOverlayEventHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class SendBuffsToClientHelper {

    public static void setBuffs(PacketSendBuffsToClient buffs) {
        RenderGameOverlayEventHandler.buffs = new ArrayList<PlayerBuff>(buffs.getBuffs());
    }
}
