package com.mrcrayfish.vehicle.block;

import com.mrcrayfish.vehicle.VehicleMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import static com.mrcrayfish.vehicle.Reference.MOD_ID;

/**
 * Author: MrCrayfish
 */
public class BlockObject extends Block
{
    public BlockObject(Material material, String id)
    {
        this(material, material.getMaterialMapColor(), id);
    }

    public BlockObject(Material material, MapColor mapColor, String id)
    {
        super(material, mapColor);
        this.setUnlocalizedName(id);
        this.setRegistryName(MOD_ID, id);
        this.setCreativeTab(VehicleMod.CREATIVE_TAB);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.MIDDLE_POLE;
    }
}
