package com.aetherteam.emissivity.mixin.mixins.client.accessor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HumanoidArmorLayer.class)
public interface HumanoidArmorLayerAccessor<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
    @Invoker
    void callRenderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, T pLivingEntity, EquipmentSlot pSlot, int pPackedLight, A pModel);

    @Invoker
    A callGetArmorModel(EquipmentSlot pSlot);
}
