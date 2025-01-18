package com.ayydxn.bettersubtitles.mixin.client;

import com.ayydxn.bettersubtitles.gui.screens.BetterSubtitlesOptionsScreen;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundOptionsScreenMixin extends GameOptionsScreen
{
    public SoundOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title)
    {
        super(parent, gameOptions, title);
    }

    @Inject(method = "addOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addAll([Lnet/minecraft/client/option/SimpleOption;)V", ordinal = 1), cancellable = true)
    public void addSubtitlesOptionsButton(CallbackInfo ci)
    {
        Preconditions.checkNotNull(this.client);
        Preconditions.checkNotNull(this.body);

        ButtonWidget subtitlesOptionButton = ButtonWidget.builder(Text.translatable("better-subtitles.options.gui_title"), button -> this.client.setScreen(new BetterSubtitlesOptionsScreen(this.parent).getHandle()))
                .build();

        this.body.addAll(ImmutableList.of(subtitlesOptionButton, this.gameOptions.getDirectionalAudio().createWidget(this.gameOptions)));

        ci.cancel();
    }
}

