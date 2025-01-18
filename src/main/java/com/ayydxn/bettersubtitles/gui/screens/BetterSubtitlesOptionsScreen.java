package com.ayydxn.bettersubtitles.gui.screens;

import com.ayydxn.bettersubtitles.BetterSubtitlesClientMod;
import com.ayydxn.bettersubtitles.options.BetterSubtitlesGameOptions;
import com.google.common.collect.Lists;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class BetterSubtitlesOptionsScreen
{
    private final BetterSubtitlesGameOptions gameOptions;
    private final Screen previousScreen;

    public BetterSubtitlesOptionsScreen(Screen previousScreen)
    {
        this.gameOptions = BetterSubtitlesClientMod.getInstance().getGameOptions();
        this.previousScreen = previousScreen;
    }

    public Screen getHandle()
    {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("better-subtitles.options.gui_title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("better-subtitles.options.category.better_subtitles"))
                        .groups(Lists.newArrayList(this.getMiscOptions()))
                        .build())
                .save(() ->
                {
                    MinecraftClient.getInstance().options.write();
                    this.gameOptions.write();
                })
                .build()
                .generateScreen(this.previousScreen);
    }

    private OptionGroup getMiscOptions()
    {
        Option<Boolean> enableLegacySubtitlesOption = Option.<Boolean>createBuilder()
                .name(Text.translatable("better-subtitles.options.miscellaneous.enable_legacy_subtitles"))
                .description(OptionDescription.of(Text.translatable("better-subtitles.options.miscellaneous.enable_legacy_subtitles.description")))
                .binding(BetterSubtitlesGameOptions.defaults().enableLegacySubtitles, () -> this.gameOptions.enableLegacySubtitles, newValue -> this.gameOptions.enableLegacySubtitles = newValue)
                .customController(BooleanController::new)
                .build();

        return OptionGroup.createBuilder()
                .name(Text.translatable("better-subtitles.options.group.miscellaneous"))
                .options(Lists.newArrayList(enableLegacySubtitlesOption))
                .build();
    }
}
