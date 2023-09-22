package com.aetherteam.emissivity.data.generators;

import com.aetherteam.emissivity.Emissivity;
import com.aetherteam.nitrogen.data.providers.NitrogenLanguageProvider;
import net.minecraft.data.PackOutput;

public class EmissivityLanguageData extends NitrogenLanguageProvider {
    public EmissivityLanguageData(PackOutput output) {
        super(output, Emissivity.MODID);
    }

    @Override
    protected void addTranslations() {
        this.addClientConfig("emissives", "emissive_sentry_boots", "Enables emissivity for Sentry Boots");
        this.addClientConfig("emissives", "emissive_phoenix_armor", "Enables emissivity for Phoenix Armor");
        this.addClientConfig("emissives", "emissive_shield_of_repulsion", "Enables emissivity for the Shield of Repulsion");

        this.addPackDescription("mod", "The Aether: Emissivity Resources");
    }
}
