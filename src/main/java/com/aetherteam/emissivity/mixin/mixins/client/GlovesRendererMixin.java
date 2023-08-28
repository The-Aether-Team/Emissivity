package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.accessory.GlovesRenderer;
import com.aetherteam.aether.client.renderer.accessory.model.GlovesModel;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.gloves.GlovesItem;
import com.aetherteam.aether.mixin.mixins.client.accessor.PlayerModelAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mixin(GlovesRenderer.class)
public class GlovesRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
    @Final
    @Shadow(remap = false)
    private GlovesModel glovesModel;
    @Final
    @Shadow(remap = false)
    private GlovesModel glovesModelSlim;
    @Final
    @Shadow(remap = false)
    private GlovesModel glovesFirstPerson;

    @Inject(method = "render(Lnet/minecraft/world/item/ItemStack;Ltop/theillusivec4/curios/api/SlotContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/entity/RenderLayerParent;Lnet/minecraft/client/renderer/MultiBufferSource;IFFFFFF)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (stack.is(AetherItems.PHOENIX_GLOVES.get())) {
            GlovesItem glovesItem = (GlovesItem)stack.getItem();
            GlovesModel model = this.glovesModel;
            ResourceLocation texture = glovesItem.getGlovesTexture();
            if (renderLayerParent.getModel() instanceof PlayerModel playerModel) {
                PlayerModelAccessor playerModelAccessor = (PlayerModelAccessor) playerModel;
                model = playerModelAccessor.aether$getSlim() ? this.glovesModelSlim : this.glovesModel;
            }
            ICurioRenderer.followBodyRotations(slotContext.entity(), model);
            float red = glovesItem.getColors(stack).getLeft();
            float green = glovesItem.getColors(stack).getMiddle();
            float blue = glovesItem.getColors(stack).getRight();
            VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, stack.isEnchanted());
            model.renderToBuffer(poseStack, vertexConsumer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
            ci.cancel();
        }
    }

    @Inject(method = "renderFirstPerson(Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/world/entity/HumanoidArm;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void renderFirstPerson(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, HumanoidArm arm, CallbackInfo ci) {
        if (stack.is(AetherItems.PHOENIX_GLOVES.get())) {
            GlovesModel model = this.glovesFirstPerson;
            model.setAllVisible(false);
            model.attackTime = 0.0F;
            model.crouching = false;
            model.swimAmount = 0.0F;
            model.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            GlovesItem glovesItem = (GlovesItem)stack.getItem();
            VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(glovesItem.getGlovesTexture()), false, stack.isEnchanted());
            float red = glovesItem.getColors(stack).getLeft();
            float green = glovesItem.getColors(stack).getMiddle();
            float blue = glovesItem.getColors(stack).getRight();
            if (arm == HumanoidArm.RIGHT) {
                this.renderGlove(model.rightArm, poseStack, LightTexture.pack(15, 15), consumer, red, green, blue);
            } else if (arm == HumanoidArm.LEFT) {
                this.renderGlove(model.leftArm, poseStack, LightTexture.pack(15, 15), consumer, red, green, blue);
            }
            ci.cancel();
        }
    }

    private void renderGlove(ModelPart gloveArm, PoseStack poseStack, int combinedLight, VertexConsumer consumer, float red, float green, float blue) {
        gloveArm.visible = true;
        gloveArm.xRot = 0.0F;
        gloveArm.render(poseStack, consumer, combinedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
