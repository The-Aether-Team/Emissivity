package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.emissivity.EmissivityConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;

@Mixin(GlovesRenderer.class)
public class GlovesRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
    @Inject(method = "render(Lnet/minecraft/world/item/ItemStack;Ltop/theillusivec4/curios/api/SlotContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/entity/RenderLayerParent;Lnet/minecraft/client/renderer/MultiBufferSource;IFFFFFF)V", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/client/renderer/accessory/model/GlovesModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), cancellable = true)
    private void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci, @Local GlovesModel model, @Local VertexConsumer vertexConsumer) {
        if (stack.is(AetherItems.PHOENIX_GLOVES.get()) && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            model.renderToBuffer(poseStack, vertexConsumer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            ci.cancel();
        }
    }

    @ModifyVariable(method = "renderFirstPerson", at = @At(value = "HEAD"), remap = false, argsOnly = true)
    private int renderFirstPerson(int combinedLight, @Local ItemStack itemStack) {
        if (itemStack.is(AetherItems.PHOENIX_GLOVES.get()) && EmissivityConfig.CLIENT.emissive_phoenix_armor.get()) {
            return LightTexture.pack(15, 15);
        }
        return combinedLight;
    }
}
