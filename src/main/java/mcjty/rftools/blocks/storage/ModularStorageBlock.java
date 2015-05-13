package mcjty.rftools.blocks.storage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcjty.container.GenericContainerBlock;
import mcjty.entity.GenericTileEntity;
import mcjty.rftools.RFTools;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ModularStorageBlock extends GenericContainerBlock {

    public ModularStorageBlock() {
        super(Material.iron, ModularStorageTileEntity.class);
        setBlockName("modularStorageBlock");
        setCreativeTab(RFTools.tabRfTools);
    }

    @Override
    public int getGuiID() {
        return RFTools.GUI_MODULAR_STORAGE;
    }

    @Override
    public String getIdentifyingIconName() {
        return "machineModularStorage";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean whatIsThis) {
        super.addInformation(itemStack, player, list, whatIsThis);

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add(EnumChatFormatting.WHITE + "This modular storage system can store a lot");
            list.add(EnumChatFormatting.WHITE + "of items and allows easy searching and filtering");
        } else {
            list.add(EnumChatFormatting.WHITE + RFTools.SHIFT_MESSAGE);
        }
    }

//    @Override
//    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
//        TileEntity tileEntity = world.getTileEntity(x, y, z);
//
//        if (tileEntity instanceof ModularStorageTileEntity) {
//            ModularStorageTileEntity modularStorageTileEntity = (ModularStorageTileEntity) tileEntity;
//            modularStorageTileEntity.copyToModule();
//            ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
//            NBTTagCompound tagCompound = new NBTTagCompound();
//            ((GenericTileEntity)tileEntity).writeRestorableToNBT(tagCompound);
//
//            stack.setTagCompound(tagCompound);
//            ArrayList<ItemStack> result = new ArrayList<ItemStack>();
//            result.add(stack);
//            return result;
//        } else {
//            return super.getDrops(world, x, y, z, metadata, fortune);
//        }
//    }
//
    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer createClientGui(EntityPlayer entityPlayer, TileEntity tileEntity) {
        ModularStorageTileEntity modularStorageTileEntity = (ModularStorageTileEntity) tileEntity;
        ModularStorageContainer modularStorageContainer = new ModularStorageContainer(entityPlayer, modularStorageTileEntity);
        return new GuiModularStorage(modularStorageTileEntity, modularStorageContainer);
    }

    @Override
    public Container createServerContainer(EntityPlayer entityPlayer, TileEntity tileEntity) {
        return new ModularStorageContainer(entityPlayer, (ModularStorageTileEntity) tileEntity);
    }

}