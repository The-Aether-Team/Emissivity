package com.aetherteam.emissivity.client.renderer;

import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.emissivity.client.renderer.player.layer.EmissiveArmorLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Emissivity.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EmissivityRenderers {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LayerDefinition outer = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.025F)), 64, 32);
        LayerDefinition inner = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.525F)), 64, 32);
        event.registerLayerDefinition(EmissivityModelLayers.PLAYER_INNER_ARMOR_EMISSIVE, () -> inner);
        event.registerLayerDefinition(EmissivityModelLayers.PLAYER_OUTER_ARMOR_EMISSIVE, () -> outer);
        event.registerLayerDefinition(EmissivityModelLayers.PLAYER_SLIM_INNER_ARMOR_EMISSIVE, () -> inner);
        event.registerLayerDefinition(EmissivityModelLayers.PLAYER_SLIM_OUTER_ARMOR_EMISSIVE, () -> outer);
        event.registerLayerDefinition(EmissivityModelLayers.ENTITY_INNER_ARMOR_EMISSIVE, () -> inner);
        event.registerLayerDefinition(EmissivityModelLayers.ENTITY_OUTER_ARMOR_EMISSIVE, () -> outer);
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet entityModelSet = event.getEntityModels();
        Set<PlayerSkin.Model> types = event.getSkins();
        for (PlayerSkin.Model type : types) {
            PlayerRenderer playerRenderer = event.getSkin(type);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new EmissiveArmorLayer<>(
                        playerRenderer,
                        new HumanoidArmorModel<>(entityModelSet.bakeLayer(type == PlayerSkin.Model.SLIM ? EmissivityModelLayers.PLAYER_SLIM_INNER_ARMOR_EMISSIVE : EmissivityModelLayers.PLAYER_INNER_ARMOR_EMISSIVE)),
                        new HumanoidArmorModel<>(entityModelSet.bakeLayer(type == PlayerSkin.Model.SLIM ? EmissivityModelLayers.PLAYER_SLIM_OUTER_ARMOR_EMISSIVE : EmissivityModelLayers.PLAYER_OUTER_ARMOR_EMISSIVE)),
                        Minecraft.getInstance().getModelManager()));
            }
        }
        List<EntityType<? extends LivingEntity>> entities = List.of(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.SKELETON, EntityType.STRAY, EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.ARMOR_STAND);
        for (EntityType<? extends LivingEntity> entityType : entities) {
            LivingEntityRenderer<LivingEntity, HumanoidArmorModel<LivingEntity>> renderer = event.getRenderer(entityType);
            if (renderer != null) {
                renderer.addLayer(new EmissiveArmorLayer<>(
                        renderer,
                        new HumanoidArmorModel<>(entityModelSet.bakeLayer(EmissivityModelLayers.ENTITY_INNER_ARMOR_EMISSIVE)),
                        new HumanoidArmorModel<>(entityModelSet.bakeLayer(EmissivityModelLayers.ENTITY_OUTER_ARMOR_EMISSIVE)),
                        Minecraft.getInstance().getModelManager()));
            }
        }
    }
}
