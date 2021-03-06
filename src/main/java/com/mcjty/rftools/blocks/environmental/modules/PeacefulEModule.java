package com.mcjty.rftools.blocks.environmental.modules;

import com.mcjty.rftools.PlayerBuff;
import com.mcjty.rftools.blocks.environmental.PeacefulAreaManager;
import com.mcjty.varia.Coordinate;
import com.mcjty.varia.GlobalCoordinate;
import net.minecraft.world.World;

public class PeacefulEModule extends BuffEModule {
    public static final float RFPERTICK = 0.001f;

    public PeacefulEModule() {
        super(PlayerBuff.BUFF_PEACEFUL);
    }

    @Override
    public float getRfPerTick() {
        return RFPERTICK;
    }

    @Override
    public void tick(World world, int x, int y, int z, int radius, int miny, int maxy) {
        if (!isActive()) {
            return;
        }

        super.tick(world, x, y, z, radius, miny, maxy);

        PeacefulAreaManager.markArea(new GlobalCoordinate(new Coordinate(x, y, z), world.provider.dimensionId), radius, miny, maxy);
    }
}
