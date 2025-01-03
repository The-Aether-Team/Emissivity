package com.aetherteam.emissivity;

import com.aetherteam.aetherfabric.events.AddPackFindersEvent;
import com.mojang.logging.LogUtils;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import fuzs.forgeconfigapiport.fabric.impl.core.NeoForgeConfigRegistryImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Emissivity implements ClientModInitializer {
    public static final String MODID = "aether_emissivity";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        //bus.addListener(this::dataSetup);
        AddPackFindersEvent.EVENT.register(this::packSetup);
        NeoForgeConfigRegistryImpl.INSTANCE.register(MODID, ModConfig.Type.CLIENT, EmissivityConfig.CLIENT_SPEC);

        ConfigScreenFactoryRegistry.INSTANCE.register(MODID, ConfigurationScreen::new);
    }

    //    public void dataSetup(GatherDataEvent event) {
//        DataGenerator generator = event.getGenerator();
//        PackOutput packOutput = generator.getPackOutput();
//
//        // Client Data
//        generator.addProvider(event.includeClient(), new EmissivityLanguageData(packOutput));
//
//        // pack.mcmeta
//        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
//                Component.translatable("pack.aether_emissivity.mod.description"),
//                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
//                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
//    }

    public void packSetup(AddPackFindersEvent event) {
        // Resource Packs
        this.setupRecipeOverridePack(event);
    }

    private void setupRecipeOverridePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = FabricLoader.getInstance().getModContainer(Emissivity.MODID).orElseThrow().findPath("packs/model_override").orElseThrow();
            PackMetadataSection metadata = new PackMetadataSection(Component.literal(""), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), Optional.empty());
            event.addRepositorySource((source) ->
                source.accept(new Pack(
                        new PackLocationInfo("builtin/emissivity_model_override", Component.literal(""), PackSource.BUILT_IN, Optional.empty()),
                        new PathPackResources.PathResourcesSupplier(resourcePath),
                        new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of()),
                        new PackSelectionConfig(true, Pack.Position.TOP, false)
                        )
                )
            );
        }
    }
}
