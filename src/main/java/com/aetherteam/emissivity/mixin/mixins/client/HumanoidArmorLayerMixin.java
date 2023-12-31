package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.emissivity.EmissivityConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @Inject(method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, ArmorItem armorItem, Model model, boolean withGlint, float red, float green, float blue, ResourceLocation armorResource, CallbackInfo ci) {
        if (armorResource.toString().contains("phoenix_layer") && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(armorResource), false, withGlint);
            model.renderToBuffer(poseStack, vertexconsumer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            ci.cancel();
        }
    }
}
