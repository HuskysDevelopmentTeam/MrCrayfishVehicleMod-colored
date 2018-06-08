package com.mrcrayfish.vehicle.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.vehicle.common.CommonEvents;
import com.mrcrayfish.vehicle.entity.EntityMotorcycle;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import com.mrcrayfish.vehicle.entity.vehicle.*;
import net.hdt.hva.entity.vehicle.EntityHighBoosterBoard;
import net.hdt.hva.entity.vehicle.EntitySantaSleight;
import net.hdt.hva.entity.vehicle.EntitySleight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Author: MrCrayfish
 */
public class ClientEvents
{
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
        {
            Minecraft mc = Minecraft.getMinecraft();
            if(mc.inGameHasFocus)
            {
                EntityPlayer player = mc.player;
                if(player != null)
                {
                    Entity entity = player.getRidingEntity();
                    if(entity instanceof EntityVehicle)
                    {
                        String speed = new DecimalFormat("0.0").format(((EntityVehicle) entity).getKilometersPreHour());
                        mc.fontRenderer.drawStringWithShadow(TextFormatting.BOLD + "BPS: " + TextFormatting.YELLOW + speed, 10, 10, Color.WHITE.getRGB());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPreRender(ModelPlayerEvent.Render.Pre event)
    {
        Entity ridingEntity = event.getEntityPlayer().getRidingEntity();
        if(ridingEntity instanceof EntityMotorcycle)
        {
            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            double offset = vehicle.getMountedYOffset() * 3 - 3 * 0.0625;
            GlStateManager.translate(0, offset, 0);
            float currentSpeedNormal = (vehicle.prevCurrentSpeed + (vehicle.currentSpeed - vehicle.prevCurrentSpeed) * event.getPartialTicks()) / vehicle.getMaxSpeed();
            float turnAngleNormal = (vehicle.prevTurnAngle + (vehicle.turnAngle - vehicle.prevTurnAngle) * event.getPartialTicks()) / 45F;
            GlStateManager.rotate(turnAngleNormal * currentSpeedNormal * 20F, 0, 0, 1);
            GlStateManager.translate(0, -offset, 0);
        }

        if(ridingEntity instanceof EntityJetSki)
        {
            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            double offset = vehicle.getMountedYOffset() * 3 - 3 * 0.0625;
            GlStateManager.translate(0, offset, 0);
            float currentSpeedNormal = (vehicle.prevCurrentSpeed + (vehicle.currentSpeed - vehicle.prevCurrentSpeed) * event.getPartialTicks()) / vehicle.getMaxSpeed();
            float turnAngleNormal = (vehicle.prevTurnAngle + (vehicle.turnAngle - vehicle.prevTurnAngle) * event.getPartialTicks()) / 45F;
            GlStateManager.rotate(turnAngleNormal * currentSpeedNormal * 15F, 0, 0, 1);
            GlStateManager.rotate(-8F * Math.min(1.0F, currentSpeedNormal), 1, 0, 0);
            GlStateManager.translate(0, -offset, 0);
        }

        if(ridingEntity instanceof EntitySpeedBoat)
        {
            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            double offset = vehicle.getMountedYOffset();
            GlStateManager.translate(0, offset + ridingEntity.getEyeHeight() + 0.25, 0);
            float currentSpeedNormal = (vehicle.prevCurrentSpeed + (vehicle.currentSpeed - vehicle.prevCurrentSpeed) * event.getPartialTicks()) / vehicle.getMaxSpeed();
            float turnAngleNormal = (vehicle.prevTurnAngle + (vehicle.turnAngle - vehicle.prevTurnAngle) * event.getPartialTicks()) / 45F;
            GlStateManager.rotate(turnAngleNormal * currentSpeedNormal * 15F, 0, 0, 1);
            GlStateManager.rotate(-8F * Math.min(1.0F, currentSpeedNormal), 1, 0, 0);
            GlStateManager.translate(0, -(offset + ridingEntity.getEyeHeight()) - 0.25, 0);
        }
    }

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post event)
    {
        EntityPlayer player = event.getEntityPlayer();

        if(player.equals(Minecraft.getMinecraft().player) && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
            return;

        Entity ridingEntity = player.getRidingEntity();
        ModelPlayer model = event.getModelPlayer();

        if(player.getDataManager().get(CommonEvents.PUSHING_CART))
        {
            player.renderYawOffset = player.rotationYawHead;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(5F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(-5F);
            return;
        }

        if(ridingEntity instanceof EntitySpeedBoat)
        {
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(20F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-20F);

            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            float wheelAngle = vehicle.prevWheelAngle + (vehicle.wheelAngle - vehicle.prevWheelAngle) * event.getPartialTicks();
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 6F;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-65F - turnRotation);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-7F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-65F + turnRotation);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(7F);
            return;
        }

        if (ridingEntity instanceof EntityMiniBike)
        {
            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            float wheelAngle = vehicle.prevWheelAngle + (vehicle.wheelAngle - vehicle.prevWheelAngle) * event.getPartialTicks();
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 8F;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-55F - turnRotation);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-55F + turnRotation);
        }

        if(ridingEntity instanceof EntityCouch)
        {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-55F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(25F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-55F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(-25F);
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(15F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-15F);
            return;
        }

        if(ridingEntity instanceof EntityShoppingCart)
        {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-70F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(5F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-70F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(-5F);
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(15F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-15F);
            return;
        }

        if(ridingEntity instanceof EntitySleight)
        {
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(15F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-15F);
            return;
        }

        if(ridingEntity instanceof EntityHighBoosterBoard)
        {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(0F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(0F);
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(0F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(0F);
            return;
        }

        if(ridingEntity instanceof EntitySantaSleight)
        {
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(15F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-15F);
            return;
        }

        if(ridingEntity instanceof EntityGoKart || ridingEntity instanceof EntityBumperCar || ridingEntity instanceof EntitySpeedBoat)
        {
            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-85F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(10F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-85F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-10F);

            EntityVehicle vehicle = (EntityVehicle) ridingEntity;
            float wheelAngle = vehicle.prevWheelAngle + (vehicle.wheelAngle - vehicle.prevWheelAngle) * event.getPartialTicks();
            float wheelAngleNormal = wheelAngle / 45F;
            float turnRotation = wheelAngleNormal * 6F;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-65F - turnRotation);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(-7F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-65F + turnRotation);
            model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(7F);
            return;
        }

        if(ridingEntity instanceof EntityVehicle)
        {
            EntityVehicle vehicle = (EntityVehicle) ridingEntity;

            float wheelAngle = vehicle.prevWheelAngle + (vehicle.wheelAngle - vehicle.prevWheelAngle) * event.getPartialTicks();
            float wheelAngleNormal = wheelAngle / 45F;

            if(ridingEntity instanceof EntityATV || ridingEntity instanceof EntityJetSki)
            {
                float turnRotation = wheelAngleNormal * 12F;
                model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-65F - turnRotation);
                model.bipedRightArm.rotateAngleY = (float) Math.toRadians(15F);
                model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-65F + turnRotation);
                model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(-15F);

                if(ridingEntity instanceof EntityJetSki && ridingEntity.getControllingPassenger() != player)
                {
                    model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-55F);
                    model.bipedRightArm.rotateAngleY = (float) Math.toRadians(0F);
                    model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-55F);
                    model.bipedLeftArm.rotateAngleY = (float) Math.toRadians(0F);
                }
            }
            else if (ridingEntity instanceof EntityDuneBuggy)
            {
                float turnRotation = wheelAngleNormal * 8F;
                model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-50F - turnRotation);
                model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-50F + turnRotation);
            }

            model.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-65F);
            model.bipedRightLeg.rotateAngleY = (float) Math.toRadians(30F);
            model.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-65F);
            model.bipedLeftLeg.rotateAngleY = (float) Math.toRadians(-30F);
        }
    }
}
