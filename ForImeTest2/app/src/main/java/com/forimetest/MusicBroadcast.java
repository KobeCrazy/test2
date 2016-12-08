package com.forimetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by admin on 2016/12/7.
 */
public class MusicBroadcast extends BroadcastReceiver {
    private static final String TAG = "MusicBroadcast";
    Uri s = Uri.parse("/data/local/tmp/Innocence.mp3");
    public static MediaPlayer mediaPlayer;


    private final String ACTION_PLAY = "PlayMusic";
    private final String ACTION_STOP = "StopMusic";
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean st = mediaPlayer == null;
        Log.d(TAG, "Mediaplayer Is Null :" + String.valueOf(st));
        if (st) {
            mediaPlayer = MediaPlayer.create(context,s);
        }


        Log.e("test", "--------------start MainActivity player---------------");


        if (intent.getBooleanExtra("PlayerVoice", true)) {
            if (MainActivity.player != null) {
                MainActivity.player.start();
                Toast.makeText(context,"PlayerVoice:"+intent.getBooleanExtra("PlayerVoice",true)+"\n Player start",Toast.LENGTH_LONG).show();
            }
        } else if (!intent.getBooleanExtra("PlayerVoice", true)) {
            if (MainActivity.player.isPlaying()) {

                //MainActivity.player.stop();
                MainActivity.player.pause();
                Toast.makeText(context,"PlayerVoice:"+intent.getBooleanExtra("PlayerVoice",true)+"\n Player pause",Toast.LENGTH_LONG).show();


            }
        }
        Log.e("test", "--------------End MainActivity player---------------");
        if (intent.getAction().equals(ACTION_PLAY)){
            if (!st) {
                boolean status = true;
                try {
                    status = mediaPlayer.isPlaying();
                    Log.d(TAG, "Mediaplayer Is Playing :" + String.valueOf(status));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (status) {
                    return;
                }
                else{
                    mediaPlayer.start();
                }
            }
            else{
                mediaPlayer.start();
            }





            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//播出完毕事件
                @Override public void onCompletion(MediaPlayer arg0) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {// 错误处理事件
                @Override public boolean onError(MediaPlayer player, int arg1, int arg2) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                    return false;
                }
            });



        }else if(intent.getAction().equals(ACTION_STOP)) {
            if (st) {
                return;
            }
            boolean status = false;
            try {
                status = mediaPlayer.isPlaying();
                Log.d(TAG, "Mediaplayer Is Playing :" + String.valueOf(status));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mediaPlayer.release();
                mediaPlayer = null;
                Log.d(TAG, "After Release The mediaplayer Status: " + String.valueOf(mediaPlayer == null));
            }

        }
    }

}
