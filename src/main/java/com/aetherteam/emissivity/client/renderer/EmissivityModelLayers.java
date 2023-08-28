package com.aetherteam.emissivity.client.renderer;

import com.aetherteam.emissivity.Emissivity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EmissivityModelLayers {
    public static final ModelLayerLocation PLAYER_INNER_ARMOR_EMISSIVE = registerInnerArmor("player");
    public static final ModelLayerLocation PLAYER_OUTER_ARMOR_EMISSIVE = registerOuterArmor("player");
    public static final ModelLayerLocation PLAYER_SLIM_INNER_ARMOR_EMISSIVE = registerInnerArmor("player_slim");
    public static final ModelLayerLocation PLAYER_SLIM_OUTER_ARMOR_EMISSIVE = registerOuterArmor("player_slim");

    private static ModelLayerLocation registerInnerArmor(String path) {
        return register(path, "inner_armor_emissive");
    }

    private static ModelLayerLocation registerOuterArmor(String path) {
        return register(path, "outer_armor_emissive");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(Emissivity.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
