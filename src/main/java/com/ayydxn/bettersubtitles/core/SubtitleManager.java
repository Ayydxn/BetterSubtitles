package com.ayydxn.bettersubtitles.core;

import net.minecraft.util.Util;

import java.util.LinkedList;
import java.util.Queue;

public class SubtitleManager
{
    private static final int MAX_SUBTITLES_ON_SCREEN = 3;

    private final Queue<Subtitle> subtitleQueue;

    public SubtitleManager()
    {
        this.subtitleQueue = new LinkedList<Subtitle>();
    }

    public void addSubtitle(String text)
    {
        if (this.subtitleQueue.size() >= MAX_SUBTITLES_ON_SCREEN)
            this.subtitleQueue.poll();

        this.subtitleQueue.add(new Subtitle(text, Util.getMeasuringTimeMs()));
    }

    public Queue<Subtitle> getSubtitleQueue()
    {
        return this.subtitleQueue;
    }
}
