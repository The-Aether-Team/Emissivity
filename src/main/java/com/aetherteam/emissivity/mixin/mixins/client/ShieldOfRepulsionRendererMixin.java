package com.aetherteam.emissivity.mixin.mixins.client;

import com.aetherteam.aether.client.renderer.accessory.ShieldOfRepulsionRenderer;
import com.aetherteam.aether.item.accessories.miscellaneous.ShieldOfRepulsionItem;
import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.emissivity.EmissivityConfig;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

@Mixin(ShieldOfRepulsionRenderer.class)
public class ShieldOfRepulsionRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_BASE = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_accessory.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_INACTIVE_BASE = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_inactive_accessory.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_BASE = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_accessory.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_INACTIVE_BASE = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_inactive_accessory.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_OVERLAY = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_accessory_overlay.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_INACTIVE_OVERLAY = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_inactive_accessory_overlay.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_OVERLAY = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_accessory_overlay.png");
    @Unique
    private static final ResourceLocation SHIELD_OF_REPULSION_SLIM_INACTIVE_OVERLAY = new ResourceLocation(Emissivity.MODID, "textures/models/accessory/shield_of_repulsion/shield_of_repulsion_slim_inactive_accessory_overlay.png");

    @Inject(method = "lambda$render$0(Ltop/theillusivec4/curios/api/SlotResult;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/RenderLayerParent;Ltop/theillusivec4/curios/api/SlotContext;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;ILtop/theillusivec4/curios/api/type/inventory/ICurioStacksHandler;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"), cancellable = true, remap = false)
    private void render(SlotResult slotResult, LivingEntity livingEntity, RenderLayerParent<T, M> renderLayerParent, SlotContext slotContext, MultiBufferSource buffer, PoseStack poseStack, int light, ICurioStacksHandler stacksHandler, CallbackInfo ci, @Local ShieldOfRepulsionItem shield, @Local ResourceLocation texture, @Local HumanoidModel<T> model) {
        if (EmissivityConfig.CLIENT.emissive_shield_of_repulsion.get()) {
            ResourceLocation baseTexture = null;
            ResourceLocation overlayTexture = null;
            if (texture.equals(shield.getShieldOfRepulsionTexture())) {
                baseTexture = SHIELD_OF_REPULSION_BASE;
                overlayTexture = SHIELD_OF_REPULSION_OVERLAY;
            } else if (texture.equals(shield.getShieldOfRepulsionInactiveTexture())) {
                baseTexture = SHIELD_OF_REPULSION_INACTIVE_BASE;
                overlayTexture = SHIELD_OF_REPULSION_INACTIVE_OVERLAY;
            } else if (texture.equals(shield.getShieldOfRepulsionSlimTexture())) {
                baseTexture = SHIELD_OF_REPULSION_SLIM_BASE;
                overlayTexture = SHIELD_OF_REPULSION_SLIM_OVERLAY;
            } else if (texture.equals(shield.getShieldOfRepulsionSlimInactiveTexture())) {
                baseTexture = SHIELD_OF_REPULSION_SLIM_INACTIVE_BASE;
                overlayTexture = SHIELD_OF_REPULSION_SLIM_INACTIVE_OVERLAY;
            }
            if (baseTexture != null) {
                VertexConsumer baseConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(baseTexture), false, false);
                model.renderToBuffer(poseStack, baseConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                VertexConsumer overlayConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.entityTranslucent(overlayTexture), false, false);
                model.renderToBuffer(poseStack, overlayConsumer, LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                ci.cancel();
            }
        }
    }

    @ModifyVariable(method = "setupShieldOnHand(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/model/HumanoidModel;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/world/entity/HumanoidArm;Z)V", at = @At(value = "HEAD"), remap = false, argsOnly = true)
    private int setupShield(int combinedLight) {
        if (EmissivityConfig.CLIENT.emissive_shield_of_repulsion.get()) {
            return LightTexture.pack(15, 15);
        }
        return combinedLight;
    }
}
