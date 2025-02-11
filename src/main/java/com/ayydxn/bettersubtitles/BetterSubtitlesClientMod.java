package com.ayydxn.bettersubtitles;

import com.ayydxn.bettersubtitles.client.SubtitleHudOverlay;
import com.ayydxn.bettersubtitles.core.SubtitleManager;
import com.ayydxn.bettersubtitles.input.BetterSubtitlesKeyBinds;
import com.ayydxn.bettersubtitles.options.BetterSubtitlesGameOptions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

@Environment(EnvType.CLIENT)
public class BetterSubtitlesClientMod implements ClientModInitializer
{
    private static BetterSubtitlesClientMod INSTANCE;

    public static final Logger LOGGER = (Logger) LogManager.getLogger("Better Subtitles");
    public static final String MOD_ID = "better-subtitles";

    private BetterSubtitlesGameOptions options;
    private SubtitleManager subtitleManager;

    @Override
    public void onInitializeClient()
    {
        INSTANCE = this;

        LOGGER.info("Initializing Better Subtitles... (Version: {})", FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow()
                .getMetadata().getVersion().getFriendlyString());

        this.options = BetterSubtitlesGameOptions.load();
        this.subtitleManager = new SubtitleManager();

        BetterSubtitlesKeyBinds.registerKeyBinds();

        HudRenderCallback.EVENT.register(new SubtitleHudOverlay(this.options, this.subtitleManager));
    }

    public static BetterSubtitlesClientMod getInstance()
    {
        if (INSTANCE == null)
            throw new IllegalStateException("Tried to access an instance of Better Subtitles when one wasn't available!");

        return INSTANCE;
    }

    public BetterSubtitlesGameOptions getGameOptions()
    {
        return this.options;
    }

    public SubtitleManager getSubtitleManager()
    {
        return this.subtitleManager;
    }
}
