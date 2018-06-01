package com.mrcrayfish.vehicle.init;

import com.mrcrayfish.vehicle.block.BlockTrafficCone;
import com.mrcrayfish.vehicle.block.BlockVehicleCreator;
import com.mrcrayfish.vehicle.item.ItemTrafficCone;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 * Author: MrCrayfish
 */
public class ModBlocks
{
    public static final Block TRAFFIC_CONE, VEHICLE_CREATOR;

    static
    {
        TRAFFIC_CONE = new BlockTrafficCone();
        VEHICLE_CREATOR = new BlockVehicleCreator();
    }

    public static void register()
    {
        registerBlock(TRAFFIC_CONE, new ItemTrafficCone(TRAFFIC_CONE));
        registerBlock(VEHICLE_CREATOR);
    }

    private static void registerBlock(Block block)
    {
        registerBlock(block, new ItemBlock(block));
    }

    private static void registerBlock(Block block, ItemBlock item)
    {
        if(block.getRegistryName() == null)
            throw new IllegalArgumentException("A block being registered does not have a registry name and could be successfully registered.");

        RegistrationHandler.Blocks.add(block);
        item.setRegistryName(block.getRegistryName());
        RegistrationHandler.Items.add(item);
    }
}
