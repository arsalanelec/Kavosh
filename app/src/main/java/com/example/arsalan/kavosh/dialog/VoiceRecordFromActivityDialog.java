package com.example.arsalan.kavosh.dialog;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.DialogVoiceRecordBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VoiceRecordFromActivityDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VoiceRecordFromActivityDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoiceRecordFromActivityDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_RECORD_AUDIO = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    public VoiceRecordFromActivityDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoiceRecordDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static VoiceRecordFromActivityDialog newInstance(String param1, String param2) {
        VoiceRecordFromActivityDialog fragment = new VoiceRecordFromActivityDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yy_MM_dd_HH_mm_ss_SSS");
        audioFile = new File(getContext().getFilesDir(),
                format.format(date) + ".3gp");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DialogVoiceRecordBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_voice_record, container, false);

        binding.btnStop.setEnabled(false);
        binding.btnRecord.setOnClickListener(view -> {
                    if (checkForPermission()) {
                        binding.btnStop.setEnabled(true);
                        binding.btnRecord.setEnabled(false);
                        binding.chronometer.setBase(SystemClock.elapsedRealtime());
                        binding.chronometer.start();
                        mediaRecorder = new MediaRecorder();
                        resetRecorder();
                        mediaRecorder.start();

                    } else {
                        Toast.makeText(getContext(), "no permission!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        binding.btnStop.setOnClickListener(view -> {
            binding.btnStop.setEnabled(false);
            binding.btnRecord.setEnabled(true);
            binding.chronometer.stop();
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
            }
        });
        binding.btnSubmit.setOnClickListener(view -> {
            if (audioFile != null) {
                // Intent intent = new Intent();
                mListener.addVoiceFile(audioFile.getPath(), audioFile.getName());
                // intent.putExtra(MyConst.EXTRA_FILE_PATH, audioFile.getPath());
                // intent.putExtra(MyConst.EXTRA_FILE_TITLE, audioFile.getName());

                // getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            }
            dismiss();
        });
        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void resetRecorder() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioSamplingRate(8000);
        mediaRecorder.setAudioChannels(1);

        mediaRecorder.setOutputFile(audioFile.getAbsolutePath());

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkForPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_RECORD_AUDIO);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addVoiceFile(String path, String fileName);
    }
}
