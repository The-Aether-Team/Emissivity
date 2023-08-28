package com.aetherteam.emissivity.data.generators;

import com.aetherteam.emissivity.Emissivity;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EmissivityLanguageData extends LanguageProvider {
    public EmissivityLanguageData(PackOutput output) {
        super(output, Emissivity.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addPackDescription("mod", "The Aether: Emissivity Resources");
    }

    public void addPackDescription(String packName, String description) {
        this.add("pack." + Emissivity.MODID + "." + packName + ".description", description);
    }
}
