package com.ayydxn.bettersubtitles.client;

import com.ayydxn.bettersubtitles.core.Subtitle;
import com.ayydxn.bettersubtitles.core.SubtitleManager;
import com.ayydxn.bettersubtitles.options.BetterSubtitlesGameOptions;
import me.x150.renderer.render.Renderer2d;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Util;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SubtitleHudOverlay implements HudRenderCallback
{
    private final BetterSubtitlesGameOptions gameOptions;
    private final SubtitleManager subtitleManager;

    public SubtitleHudOverlay(BetterSubtitlesGameOptions gameOptions, SubtitleManager subtitleManager)
    {
        this.gameOptions = gameOptions;
        this.subtitleManager = subtitleManager;
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter)
    {
        if (this.gameOptions.enableLegacySubtitles)
            return;

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        AtomicInteger subtitleIndex = new AtomicInteger();

        int scaledWindowWidth = drawContext.getScaledWindowWidth();
        int scaledWindowHeight = drawContext.getScaledWindowHeight();
        long currentTime = Util.getMeasuringTimeMs();

        this.subtitleManager.getSubtitleQueue().removeIf(subtitle ->
        {
            long timeElapsed = currentTime - subtitle.startTime();
            if (timeElapsed > Subtitle.DISPLAY_DURATION)
                return true;

            int yPosition = (scaledWindowHeight - 31 - 4) - (subtitleIndex.get() * 12);
            int xPosition = (scaledWindowWidth - textRenderer.getWidth(subtitle.text())) / 2;

            this.renderSubtitleBackground(drawContext, client.textRenderer, subtitle.text(), xPosition, yPosition);

            drawContext.drawText(textRenderer, subtitle.text(), xPosition, yPosition, Color.WHITE.getRGB(), true);

            subtitleIndex.getAndIncrement();
            return false;
        });
    }

    private void renderSubtitleBackground(DrawContext drawContext, TextRenderer textRenderer, String subtitleText, int x, int y)
    {
        int textWidth = textRenderer.getWidth(subtitleText);
        int textHeight = textRenderer.fontHeight;

        // Change these to adjust the dimensions of the background
        int horizontalPadding = 4;
        int verticalPadding = 2;

        int backgroundWidth = textWidth + 2 * horizontalPadding;
        int backgroundHeight = textHeight + 2 * verticalPadding;

        int backgroundX = x - horizontalPadding;
        int backgroundY = y - verticalPadding;

        Renderer2d.renderQuad(drawContext.getMatrices(), new Color(0, 0, 0, 128), backgroundX, backgroundY, backgroundX + backgroundWidth,
                backgroundY + backgroundHeight);
    }
}
