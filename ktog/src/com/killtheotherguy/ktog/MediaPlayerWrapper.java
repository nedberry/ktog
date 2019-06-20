package com.killtheotherguy.ktog;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerWrapper
{
    static MediaPlayer _player;

    public static void play(Context context, int id)
    {
        stop();
        _player = MediaPlayer.create(context, id);
        _player.start();
    }

    public static void stop()
    {
        if (_player != null)
        {
            _player.reset();
            _player.release();
            _player = null;
        }
    }
}