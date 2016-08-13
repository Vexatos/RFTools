package mcjty.rftools.varia;

import mcjty.lib.varia.BlockPosTools;
import mcjty.lib.varia.Logging;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameData;
import org.apache.commons.lang3.StringUtils;

public class RFToolsTools {

    public static void logDebug(String name, BlockPos pos, String info) {
        if (Logging.debugMode) {
            Logging.logDebug(BlockPosTools.toString(pos) + ": " + name + " -> " + info);
        }
    }

    public static boolean safeEquals(Object a, Object b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public static boolean chunkLoaded(World world, BlockPos pos) {
        if (world == null || pos == null) {
            return false;
        }
        return world.getChunkProvider().getLoadedChunk(pos.getX() >> 4, pos.getZ() >> 4) != null && world.getChunkFromBlockCoords(pos).isLoaded();
    }

    public static StringBuffer appendIndent(StringBuffer buffer, int indent) {
        return buffer.append(StringUtils.repeat(' ', indent));
    }

    public static void convertNBTtoJson(StringBuffer buffer, NBTTagList tagList, int indent) {
        for (int i = 0 ; i < tagList.tagCount() ; i++) {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            appendIndent(buffer, indent).append("{\n");
            convertNBTtoJson(buffer, compound, indent + 4);
            appendIndent(buffer, indent).append("},\n");
        }
    }

    public static void convertNBTtoJson(StringBuffer buffer, NBTTagCompound tagCompound, int indent) {
        boolean first = true;
        for (Object o : tagCompound.getKeySet()) {
            if (!first) {
                buffer.append(",\n");
            }
            first = false;

            String key = (String) o;
            NBTBase tag = tagCompound.getTag(key);
            appendIndent(buffer, indent).append(key).append(':');
            if (tag instanceof NBTTagCompound) {
                NBTTagCompound compound = (NBTTagCompound) tag;
                buffer.append("{\n");
                convertNBTtoJson(buffer, compound, indent + 4);
                appendIndent(buffer, indent).append('}');
            } else if (tag instanceof NBTTagList) {
                NBTTagList list = (NBTTagList) tag;
                buffer.append("[\n");
                convertNBTtoJson(buffer, list, indent + 4);
                appendIndent(buffer, indent).append(']');
            } else {
                buffer.append(tag);
            }
        }
        if (!first) {
            buffer.append("\n");
        }
    }

    public static String getModidForBlock(Block block) {
        ResourceLocation nameForObject = GameData.getBlockRegistry().getNameForObject(block);
        if (nameForObject == null) {
            return "?";
        }
        return nameForObject.getResourceDomain();
    }

    public static boolean hasModuleTarget(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return false;
        }
        return stack.getTagCompound().hasKey("monitorx");
    }

    public static int getDimensionFromModule(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return 0;
        }
        return stack.getTagCompound().getInteger("monitordim");
    }

    public static void setPositionInModule(ItemStack stack, Integer dimension, BlockPos pos, String name) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (dimension != null) {
            stack.getTagCompound().setInteger("monitordim", dimension);
        }
        if (name != null) {
            stack.getTagCompound().setString("monitorname", name);
        }
        stack.getTagCompound().setInteger("monitorx", pos.getX());
        stack.getTagCompound().setInteger("monitory", pos.getY());
        stack.getTagCompound().setInteger("monitorz", pos.getZ());
    }

    public static void clearPositionInModule(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = stack.getTagCompound();
        tagCompound.removeTag("monitordim");
        tagCompound.removeTag("monitorx");
        tagCompound.removeTag("monitory");
        tagCompound.removeTag("monitorz");
        tagCompound.removeTag("monitorname");
    }

    public static BlockPos getPositionFromModule(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return null;
        }
        NBTTagCompound tagCompound = stack.getTagCompound();
        int monitorx = tagCompound.getInteger("monitorx");
        int monitory = tagCompound.getInteger("monitory");
        int monitorz = tagCompound.getInteger("monitorz");
        return new BlockPos(monitorx, monitory, monitorz);
    }
}
