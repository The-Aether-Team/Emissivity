package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.emissivity.EmissivityConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @WrapOperation(method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/Model;ILnet/minecraft/resources/ResourceLocation;)V"))
    private void render(HumanoidArmorLayer<?, ?, ?> instance, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Model model, int color, ResourceLocation texture, Operation<Void> original) {
        if (texture.toString().contains("phoenix_layer") && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            original.call(instance, poseStack, bufferSource, LightTexture.pack(15, 15), model, color, texture);
        } else if (texture.toString().contains("sentry_layer_1") && EmissivityConfig.CLIENT.emissive_sentry_boots.get()) {
            original.call(instance, poseStack, bufferSource, packedLight, model, color, texture);
            original.call(instance, poseStack, bufferSource, LightTexture.pack(15, 15), model, color, ResourceLocation.fromNamespaceAndPath(Emissivity.MODID, "textures/models/armor/sentry_layer_1_overlay.png"));
        } else {
            original.call(instance, poseStack, bufferSource, packedLight, model, color, texture);
        }
    }
}
