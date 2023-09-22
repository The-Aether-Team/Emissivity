package com.aetherteam.emissivity.client.renderer.player.layer;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.emissivity.EmissivityConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class EmissiveArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    public EmissiveArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(AetherItems.SENTRY_BOOTS.get()) && EmissivityConfig.CLIENT.emissive_sentry_boots.get()) {
            this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.FEET, LightTexture.pack(15, 15), this.getArmorModel(EquipmentSlot.FEET));
        }
    }

    @Override
    public ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        if (stack.is(AetherItems.SENTRY_BOOTS.get()) && EmissivityConfig.CLIENT.emissive_sentry_boots.get()) {
            return new ResourceLocation(Emissivity.MODID, "textures/models/armor/sentry_layer_1_overlay.png");
        }
        return new ResourceLocation("");
    }
}
