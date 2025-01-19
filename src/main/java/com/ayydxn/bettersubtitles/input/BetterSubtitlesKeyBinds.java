package com.ayydxn.bettersubtitles.input;

import com.ayydxn.bettersubtitles.BetterSubtitlesClientMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BetterSubtitlesKeyBinds
{
    public static final String KEY_CATEGORY_DEBUG = "key.category.better-subtitles.debug";
    public static final String KEY_DISPLAY_TEST_SUBTITLE = "key.better-subtitles.display_test_subtitle";

    public static KeyBinding DISPLAY_TEST_SUBTITLE;

    public static void registerKeyBinds()
    {
        BetterSubtitlesKeyBinds.registerDebugKeyBinds();
        BetterSubtitlesKeyBinds.registerKeyInputs();
    }

    private static void registerDebugKeyBinds()
    {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment())
            return;

        DISPLAY_TEST_SUBTITLE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DISPLAY_TEST_SUBTITLE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                KEY_CATEGORY_DEBUG
        ));
    }

    private static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            if (DISPLAY_TEST_SUBTITLE.wasPressed())
                BetterSubtitlesClientMod.getInstance().getSubtitleManager().addSubtitle("Lorem ipsum dolor sit amet");
        });
    }
}
