package com.ayydxn.bettersubtitles.core;

public record Subtitle(String text, long startTime)
{
    public static final int DISPLAY_DURATION = 3000;
}
