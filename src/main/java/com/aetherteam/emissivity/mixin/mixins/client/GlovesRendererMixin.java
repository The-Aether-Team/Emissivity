package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.emissivity.EmissivityConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlovesRenderer.class)
public class GlovesRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
    @WrapOperation(method = "render(Lnet/minecraft/world/item/ItemStack;Lio/wispforest/accessories/api/slot/SlotReference;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/client/renderer/MultiBufferSource;IFFFFFF)V", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/client/renderer/accessory/model/GlovesModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void render(GlovesModel instance, PoseStack poseStack, VertexConsumer consumer, int light, int overlay, int color, Operation<Void> original, @Local(argsOnly = true) ItemStack itemStack) {
        if (itemStack.is(AetherItems.PHOENIX_GLOVES.get()) && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            original.call(instance, poseStack, consumer, LightTexture.pack(15, 15), overlay, color);
        } else {
            original.call(instance, poseStack, consumer, light, overlay, color);
        }
    }

    @WrapOperation(method = "renderFirstPerson(Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/world/entity/HumanoidArm;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void renderFirstPerson(ModelPart instance, PoseStack poseStack, VertexConsumer consumer, int light, int overlay, int color, Operation<Void> original, @Local(argsOnly = true) ItemStack itemStack) {
        if (itemStack.is(AetherItems.PHOENIX_GLOVES.get()) && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            original.call(instance, poseStack, consumer, LightTexture.pack(15, 15), overlay, color);
        } else {
            original.call(instance, poseStack, consumer, light, overlay, color);
        }
    }
}
