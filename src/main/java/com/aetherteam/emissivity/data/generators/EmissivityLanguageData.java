package com.aetherteam.emissivity.data.generators;

import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class EmissivityLanguageData extends NitrogenLanguageProvider {
    public EmissivityLanguageData(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void addTranslations() {
        this.addConfig("title", "Emissivity Configuration");
        this.addConfig("section.aether_emissivity.client.toml", "Client Settings");
        this.addConfig("section.aether_emissivity.client.toml.title", "Emissivity Client Configuration");

        this.addConfig("Emissives", "Emissives");
        this.addConfig("Emissives.tooltip", "Config options for emissives");

        this.addClientConfig("emissives", "emissive_sentry_boots", "Enables emissivity for Sentry Boots");
        this.addClientConfig("emissives", "emissive_phoenix_armor", "Enables emissivity for Phoenix Armor");
        this.addClientConfig("emissives", "emissive_shield_of_repulsion", "Enables emissivity for the Shield of Repulsion");

        this.addPackDescription("mod", "The Aether: Emissivity Resources");
    }
}
