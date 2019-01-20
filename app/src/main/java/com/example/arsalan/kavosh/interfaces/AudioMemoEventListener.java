package com.example.arsalan.kavosh.interfaces;

import android.view.View;
import android.widget.CompoundButton;

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.FileDownloaded;

public class AudioMemoEventListener {

    public interface AudioItemClickListener {
        void onPlayPauseChanged(CompoundButton compoundButton, boolean b, FileDownloaded fileDownloaded);

        void onAddNewAudioClicked(View view);

        void onAudioItemClicked(AudioMemo audioMemo);
    }
}
