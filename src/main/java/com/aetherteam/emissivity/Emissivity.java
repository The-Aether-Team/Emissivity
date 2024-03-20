package com.aetherteam.emissivity;

import com.aetherteam.emissivity.data.generators.EmissivityLanguageData;
import com.mojang.logging.LogUtils;
import net.minecraft.SharedConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Mod(Emissivity.MODID)
public class Emissivity {
    public static final String MODID = "aether_emissivity";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Emissivity(IEventBus bus, Dist dist) {
        bus.addListener(this::dataSetup);
        bus.addListener(this::packSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EmissivityConfig.CLIENT_SPEC);
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new EmissivityLanguageData(packOutput));

        // pack.mcmeta
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.translatable("pack.aether_emissivity.mod.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }

    public void packSetup(AddPackFindersEvent event) {
        // Resource Packs
        this.setupRecipeOverridePack(event);
    }

    private void setupRecipeOverridePack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Emissivity.MODID).getFile().findResource("packs/model_override");
            PackMetadataSection metadata = new PackMetadataSection(Component.literal(""), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                    "builtin/emissivity_model_override",
                    Component.literal(""),
                    true,
                    new PathPackResources.PathResourcesSupplier(resourcePath, true),
                    new Pack.Info(metadata.description(), metadata.packFormat(PackType.SERVER_DATA), metadata.packFormat(PackType.CLIENT_RESOURCES), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true),
                    Pack.Position.TOP,
                    false,
                    PackSource.BUILT_IN)
                )
            );
        }
    }
}
