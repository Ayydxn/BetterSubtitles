package com.ayydxn.bettersubtitles.options;

import com.ayydxn.bettersubtitles.BetterSubtitlesClientMod;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class BetterSubtitlesGameOptions
{
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("better-subtitles-settings.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();

    public boolean enableLegacySubtitles = false;

    public static BetterSubtitlesGameOptions defaults()
    {
        return new BetterSubtitlesGameOptions();
    }

    public static BetterSubtitlesGameOptions load()
    {
        if (Files.exists(CONFIG_FILE))
        {
            StringBuilder configFileContents = new StringBuilder();

            try
            {
                configFileContents.append(FileUtils.readFileToString(CONFIG_FILE.toFile(), StandardCharsets.UTF_8));
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
            }

            BetterSubtitlesGameOptions gameOptions = null;

            try
            {
                gameOptions = GSON.fromJson(configFileContents.toString(), BetterSubtitlesGameOptions.class);
            }
            catch (JsonSyntaxException exception) // If the config file is corrupted on disk.
            {
                exception.printStackTrace();
            }

            return gameOptions;
        }
        else
        {
            BetterSubtitlesClientMod.LOGGER.warn("Failed to load Better Subtitles' options! Loading defaults...");

            BetterSubtitlesGameOptions defaultGameOptions = BetterSubtitlesGameOptions.defaults();
            defaultGameOptions.write();

            return defaultGameOptions;
        }
    }

    public void write()
    {
        try
        {
            FileUtils.writeStringToFile(CONFIG_FILE.toFile(), GSON.toJson(this), StandardCharsets.UTF_8);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
