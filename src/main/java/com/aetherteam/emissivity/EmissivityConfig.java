package com.aetherteam.emissivity;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class EmissivityConfig {
    public static class Client {
        public final ForgeConfigSpec.ConfigValue<Boolean> emissive_sentry_boots;
        public final ForgeConfigSpec.ConfigValue<Boolean> emissive_phoenix_armor;
        public final ForgeConfigSpec.ConfigValue<Boolean> emissive_shield_of_repulsion;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("Emissives");
            emissive_sentry_boots = builder
                    .comment("Enables emissivity for Sentry Boots")
                    .translation("config.emissivity.client.emissives.emissive_sentry_boots")
                    .define("Sentry Boots emissivity", true);
            emissive_phoenix_armor = builder
                    .comment("Enables emissivity for Phoenix Armor")
                    .translation("config.emissivity.client.emissives.emissive_phoenix_armor")
                    .define("Phoenix Armor emissivity", true);
            emissive_shield_of_repulsion = builder
                    .comment("Enables emissivity for the Shield of Repulsion")
                    .translation("config.emissivity.client.emissives.emissive_shield_of_repulsion")
                    .define("Shield of Repulsion emissivity", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
