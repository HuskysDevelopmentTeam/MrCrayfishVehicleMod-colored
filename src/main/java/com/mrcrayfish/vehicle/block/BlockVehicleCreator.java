package com.mrcrayfish.vehicle.block;

import com.mrcrayfish.vehicle.VehicleMod;
import com.mrcrayfish.vehicle.init.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockVehicleCreator extends BlockObject {

    public BlockVehicleCreator() {
        super(Material.IRON, "vehicle_creator");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote && !playerIn.isSneaking()) {
            playerIn.openGui(VehicleMod.instance, GuiHandler.VEHICLE_CREATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return false;
    }
}
