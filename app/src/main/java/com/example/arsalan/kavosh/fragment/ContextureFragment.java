package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.GalleryActivity;
import com.example.arsalan.kavosh.databinding.FragmentContextureBinding;
import com.example.arsalan.kavosh.databinding.ItemAudioListBinding;
import com.example.arsalan.kavosh.databinding.ItemCompositionBinding;
import com.example.arsalan.kavosh.databinding.ItemGalleryBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddCompositionDialog;
import com.example.arsalan.kavosh.dialog.PhotoReviewDialog;
import com.example.arsalan.kavosh.dialog.VoiceRecordDialog;
import com.example.arsalan.kavosh.interfaces.AudioMemoEventListener;
import com.example.arsalan.kavosh.interfaces.PhotoItemClickListener;
import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Composition;
import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.ContextureDao;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.ContextureViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.ContextureUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.PhotoUploadWorker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContextureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContextureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContextureFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_COMPOSITION = 100;
    private static final int REQ_RECORD_AUDIO = 200;
    private static final int REQ_ADD_SUBTITLE_PHOTO = 102;


    private final String TAG = "ContextureFragment";
    @Inject
    MyViewModelFactory factory;
    @Inject
    Token mToken;
    @Inject
    PhotoDao mPhotoDao;
    @Inject
    AudioDao mAudioDao;
    @Inject
    ContextureDao mContextureDao;
    Contexture mContexture;
    // TODO: Rename and change types of parameters
    private String mLayerName;
    private String mLayerId;
    private OnFragmentInteractionListener mListener;
    private FragmentContextureBinding binding;
    private int mContextType;

    private List<Composition> mCompositionList;
    private LLCompositionAdapter LLCompositionAdapter;

    private PhotoAdapter mGalleryAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();

    private LLAudioAdapter mAudioAdapter;
    private List<AudioMemo> mAudioLst = new ArrayList<>();

    private String mPhotoLocalPath;

    public ContextureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContextureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContextureFragment newInstance(String param1, String param2) {
        ContextureFragment fragment = new ContextureFragment();
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
            mLayerName = getArguments().getString(ARG_PARAM1);
            mLayerId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ContextureViewModel viewModel = ViewModelProviders.of(ContextureFragment.this, factory).get(ContextureViewModel.class);
        viewModel.initial(mLayerId);
        viewModel.getContexture().observe(ContextureFragment.this, new Observer<Contexture>() {
            @Override
            public void onChanged(@Nullable Contexture contexture) {
                if (contexture != null) {
                    mContexture = contexture;

                    binding.txtSoilColor.setText(contexture.getSoilColor());
                    binding.txtTools.setText(contexture.getTools());
                    binding.etDescription.setText(contexture.getDescription());
                    binding.spnAccumulation.setSelection(contexture.getAccumulation());
                    binding.spnLayerContexture.setSelection(contexture.getLayerContexture());
                    binding.etLayerDepth.setText(String.valueOf(contexture.getSampleDepth()));
                    switch (contexture.getType()) {
                        case 1:
                            binding.rbType1.setChecked(true);
                            break;
                        case 2:
                            binding.rbType2.setChecked(true);
                            break;
                        case 3:
                            binding.rbType3.setChecked(true);
                            break;
                        default:
                            binding.rbType1.setChecked(true);
                    }
                    mCompositionList.removeAll(mCompositionList);
                    Gson gson = new Gson();
                    try {
                        List<Composition> list = gson.fromJson(contexture.getComposition(), new TypeToken<List<Composition>>() {
                        }.getType());
                        mCompositionList.addAll(list);
                        LLCompositionAdapter.notifyDataSetChanged();

                    } catch (Exception e) {

                    }
                    setupPhotoAndAudioRecycler();
                    viewModel.getContexture().removeObserver(this);
                    setViewListeners();
                    binding.flWaiting.setVisibility(View.GONE);
                } else {
                    mContexture = new Contexture();
                    mContexture.setLayerId(mLayerId);
                    mContextureDao.save(mContexture);
                    setViewListeners();
                }


            }
        });


        Log.d(getClass().getSimpleName(), "onActivityCreated: ");
    }

    private void setupPhotoAndAudioRecycler() {
        AudioListViewModel audioVM = ViewModelProviders.of(ContextureFragment.this, factory).get(AudioListViewModel.class);
        audioVM.initial(mContexture.getId());
        audioVM.getAudioMemoList().observe(ContextureFragment.this, audioMemoList -> {
            mAudioLst.removeAll(mAudioLst);
            mAudioLst.addAll(audioMemoList);
            mAudioAdapter.notifyDataSetChanged();
        });

        PhotoListViewModel photoListViewModel = ViewModelProviders.of(ContextureFragment.this, factory).get(PhotoListViewModel.class);
        photoListViewModel.initial(mContexture.getId());
        photoListViewModel.getPhotoList().observe(ContextureFragment.this, photos -> {
            Log.d(TAG, "PhotoListViewModel onChanged: ");
            if (photos != null) {
                mPhotoList.removeAll(mPhotoList);
                mPhotoList.addAll(photos);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setViewListeners() {
        binding.btnAddAudio.setOnClickListener(view -> {
            VoiceRecordDialog dialog = new VoiceRecordDialog();
            dialog.setTargetFragment(ContextureFragment.this, REQ_RECORD_AUDIO);
            dialog.show(getFragmentManager(), "");
        });
        binding.btnAddPhoto.setOnClickListener(v -> {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(getContext(), "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();
            PickSetup setup = new PickSetup()
                    .setTitle("گرفتن عکس")
                    // .setTitleColor(yourColor)
                    // .setBackgroundColor(yourColor)
                    //  .setProgressText(yourText)
                    //  .setProgressTextColor(yourColor)
                    .setCancelText("انصراف")
                    //  .setCancelTextColor(yourColor)
                    //  .setButtonTextColor(yourColor)
                    //  .setDimAmount(yourFloat)
                    // .setFlip(true)
                    .setMaxSize(10)
                    .setWidth(1600).setHeight(1200)
                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                    .setCameraButtonText("دوربین")
                    .setGalleryButtonText("گالری")
                    .setIconGravity(Gravity.LEFT)
                    .setButtonOrientation(LinearLayout.VERTICAL)
                    .setSystemDialog(false);
            //.setGalleryIcon(yourIcon)
            //.setCameraIcon(yourIcon);
            PickImageDialog.build(setup)
                    .setOnPickResult(r -> {
                        if (r.getError() == null) {
                            Log.d(TAG, "onAddNewPhotoClicked: " + r.getBitmap().getWidth());
                            mPhotoLocalPath = r.getUri().toString();


                            try {
                                File imgFile = new File(r.getPath());
                                //create a file to write bitmap data
                                File f = new File(getContext().getCacheDir(), imgFile.getName());
                                f.createNewFile();

//Convert bitmap to byte array
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.JPEG, 60, bos);
                                byte[] bitmapData = bos.toByteArray();

//write the bytes in file
                                FileOutputStream fos = null;
                                fos = new FileOutputStream(f);
                                fos.write(bitmapData);
                                fos.flush();
                                fos.close();
                                mPhotoLocalPath = getContext().getCacheDir() + "/" + imgFile.getName();
                                Log.d(TAG, "onAddNewPhotoClicked: localpath:" + mPhotoLocalPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PhotoReviewDialog dialog = PhotoReviewDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.setTargetFragment(ContextureFragment.this, REQ_ADD_SUBTITLE_PHOTO);
                            dialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getContext(), "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).setOnPickCancel(() -> {
                //TODO: do what you have to if user clicked cancel
            }).show(getFragmentManager());
        });
        binding.txtSoilColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mContexture != null && (mContexture.getSoilColor() == null || !mContexture.getSoilColor().equals(editable.toString()))) {
                    mContexture.setSoilColor(editable.toString());
                    createUpdateContexture();
                }
            }
        });
        binding.txtTools.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (mContexture != null && (mContexture.getTools() == null || !mContexture.getTools().equals(editable.toString()))) {
                    mContexture.setTools(editable.toString());
                    createUpdateContexture();
                }
            }
        });
        binding.etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mContexture != null && (mContexture.getDescription() == null || !mContexture.getDescription().equals(editable.toString()))) {
                    mContexture.setDescription(binding.etDescription.getText().toString());
                    createUpdateContexture();
                }
            }
        });
        binding.etLayerDepth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (mContexture != null && mContexture.getSampleDepth() != Integer.parseInt(editable.toString())) {
                        mContexture.setSampleDepth(Integer.parseInt(editable.toString()));
                        createUpdateContexture();
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        });
        binding.rgType.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rbType1:
                    mContextType = 1;
                    mContexture.setType(1);
                    break;
                case R.id.rbType2:
                    mContextType = 2;
                    mContexture.setType(2);
                    break;
                case R.id.rbType3:
                    mContextType = 3;
                    mContexture.setType(3);

                    break;
            }
            if (mContexture != null) {
                mContexture.setType(mContextType);
                createUpdateContexture();
            }
        });
        binding.spnLayerContexture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mContexture != null) {
                    mContexture.setLayerContexture(i);
                    createUpdateContexture();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spnAccumulation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mContexture != null) {
                    mContexture.setAccumulation(i);
                    createUpdateContexture();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnAddComposition.setOnClickListener(view -> {
            AddCompositionDialog dialog = new AddCompositionDialog();
            dialog.setTargetFragment(ContextureFragment.this, REQ_ADD_COMPOSITION);
            dialog.show(getFragmentManager(), "");
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_contexture, container, false);
        View v = binding.getRoot();
        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_soil_color));
        binding.rvGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mGalleryAdapter = new PhotoAdapter(mPhotoList);
        binding.rvGallery.setAdapter(mGalleryAdapter);
        binding.txtSoilColor.setAdapter(textAdapter1);
        mAudioAdapter = new LLAudioAdapter(mAudioLst, binding.llAudioList);


        ArrayAdapter<String> textAdapter2 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_tools));
        binding.txtTools.setAdapter(textAdapter2);


        binding.txtHeader.setText("لایه شماره " + mLayerName);
        mCompositionList = new ArrayList<>();
        LLCompositionAdapter = new LLCompositionAdapter(mCompositionList, binding.linearLayout);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ADD_COMPOSITION) {
            if (resultCode == RESULT_OK) {
                Composition composition = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                mCompositionList.add(composition);
                LLCompositionAdapter.notifyDataSetChanged();
                Gson gson = new Gson();
                String string = gson.toJson(mCompositionList);
                Log.d(TAG, "onActivityResult: gson:" + string);
                mContexture.setComposition(string);
                createUpdateContexture();
            }
        }
        if (requestCode == REQ_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                AudioMemo audio = new AudioMemo();
                audio.setAudioable_id(mContexture.getId());
                audio.setAudioable_type("App\\Contexture");
                audio.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                audio.setLocalPath(data.getStringExtra(MyConst.EXTRA_FILE_PATH));
                mAudioDao.save(audio);
                uploadAudio(audio);

            }
        }
        if (requestCode == REQ_ADD_SUBTITLE_PHOTO) {
            if (resultCode == RESULT_OK) {

                Photo photo = new Photo();
                photo.setLocalPath(mPhotoLocalPath);
                photo.setPhotoableId(mContexture.getId());
                photo.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                photo.setPhotoableType("App\\Contexture");
                mPhotoDao.save(photo);
                uploadPhoto(photo);
                Toast.makeText(getContext(), "عکس ذخیره شد", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void createUpdateContexture() {


        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        mContextureDao.save(mContexture);
        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", mContexture.getId())
                .putString("layer_id", mContexture.getLayerId())
                .putString("accumulation", (String.valueOf(mContexture.getAccumulation())))
                .putString("layer_contexture", String.valueOf(mContexture.getLayerContexture()))
                .putString("soil_color", mContexture.getSoilColor())
                .putString("composition", mContexture.getComposition())
                .putString("tools", mContexture.getTools())
                .putString("sample_depth", String.valueOf(mContexture.getSampleDepth()))
                .putString("type", String.valueOf(mContexture.getType()))
                .putString("description", mContexture.getDescription())
                .build();

        OneTimeWorkRequest uploadContexture = new OneTimeWorkRequest.Builder(ContextureUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();

        WorkManager.getInstance().beginUniqueWork(mContexture.getId(), ExistingWorkPolicy.REPLACE, uploadContexture).enqueue();
    }

    private void uploadPhoto(Photo photo) {

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", photo.getId())
                .putString("title", photo.getTitle())
                .putString("image", photo.getLocalPath())
                .putString("photoable_type", photo.getPhotoableType())
                .putString("photoable_id", photo.getPhotoableId())

                .build();

        OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest.Builder(PhotoUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData).build();


        WorkManager.getInstance().enqueue(uploadWorker);

        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWorker.getId())
                .observe(ContextureFragment.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "uploadWorker: finished!");
                    } else if (workStatus != null) {
                        Log.d(TAG, "status:" + workStatus);
                    }
                });
    }

    private void uploadAudio(AudioMemo audioMemo) {

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", audioMemo.getId())
                .putString("title", audioMemo.getTitle())
                .putString("audio", audioMemo.getLocalPath())
                .putString("audioable_type", audioMemo.getAudioable_type())
                .putString("audioable_id", audioMemo.getAudioable_id())
                .build();

        OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest.Builder(AudioUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData).build();


        WorkManager.getInstance().enqueue(uploadWorker);

        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWorker.getId())
                .observe(ContextureFragment.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "uploadWorker: finished!");
                    } else if (workStatus != null) {
                        Log.d(TAG, "status:" + workStatus);
                    }
                });
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
        void onFragmentInteraction(Uri uri);
    }

    private class LLCompositionAdapter {
        LinearLayout linearLayout;
        List<Composition> arrayList;

        public LLCompositionAdapter(List<Composition> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.header_composition, null);
            linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Composition getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemCompositionBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_composition, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemCompositionBinding) convertView.getTag();
            }
            binding.setComposition(getItem(position));
            return binding.getRoot();

        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolderItem> implements PhotoItemClickListener {
        private final int VIEW_TYPE_ADD_BUTTON = 1;
        private final int VIEW_TYPE_ITEM = 2;
        private List<Photo> photoList;

        public PhotoAdapter(List<Photo> photoList) {
            this.photoList = photoList;
        }

        @Override
        public int getItemViewType(int position) {
            //   if (position == 0) return VIEW_TYPE_ADD_BUTTON;
            return VIEW_TYPE_ITEM;
        }

        @NonNull
        @Override
        public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            ItemGalleryBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_gallery, viewGroup, false);

            return new ViewHolderItem(binding);


        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderItem viewHolderItem, int i) {
            if (getItemViewType(i) == VIEW_TYPE_ADD_BUTTON) {
                viewHolderItem.binding.txt.setVisibility(View.VISIBLE);

            } else {
                //     viewHolderItem.binding.setPhoto(photoList.get(i - 1));
                viewHolderItem.binding.setPhoto(photoList.get(i));

            }
            viewHolderItem.binding.setItemClickListener(this);

        }


        /*  @Override
          public int getItemCount() {
              return photoList.size() + 1;
          }
  */
        @Override
        public int getItemCount() {
            return photoList.size();
        }

        @Override
        public void onPhotoClicked(Photo photo) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), GalleryActivity.class);
            intent.putExtra(MyConst.EXTRA_INDEX, photoList.indexOf(photo));
            intent.putParcelableArrayListExtra(MyConst.EXTRA_MODEL_List, mPhotoList);

            startActivity(intent);
        }

        @Override
        public void onAddNewPhotoClicked(View view) {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(getContext(), "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();
            PickSetup setup = new PickSetup()
                    .setTitle("گرفتن عکس")
                    // .setTitleColor(yourColor)
                    // .setBackgroundColor(yourColor)
                    //  .setProgressText(yourText)
                    //  .setProgressTextColor(yourColor)
                    .setCancelText("انصراف")
                    //  .setCancelTextColor(yourColor)
                    //  .setButtonTextColor(yourColor)
                    //  .setDimAmount(yourFloat)
                    // .setFlip(true)
                    .setMaxSize(10)
                    .setWidth(1600).setHeight(1200)
                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                    .setCameraButtonText("دوربین")
                    .setGalleryButtonText("گالری")
                    .setIconGravity(Gravity.LEFT)
                    .setButtonOrientation(LinearLayout.VERTICAL)
                    .setSystemDialog(false);
            //.setGalleryIcon(yourIcon)
            //.setCameraIcon(yourIcon);
            PickImageDialog.build(setup)
                    .setOnPickResult(r -> {
                        if (r.getError() == null) {
                            Log.d(TAG, "onAddNewPhotoClicked: " + r.getBitmap().getWidth());
                            mPhotoLocalPath = r.getUri().toString();


                            try {
                                File imgFile = new File(r.getPath());
                                //create a file to write bitmap data
                                File f = new File(getContext().getCacheDir(), imgFile.getName());
                                f.createNewFile();

//Convert bitmap to byte array
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.JPEG, 60, bos);
                                byte[] bitmapData = bos.toByteArray();

//write the bytes in file
                                FileOutputStream fos = null;
                                fos = new FileOutputStream(f);
                                fos.write(bitmapData);
                                fos.flush();
                                fos.close();
                                mPhotoLocalPath = getContext().getCacheDir() + "/" + imgFile.getName();
                                Log.d(TAG, "onAddNewPhotoClicked: localpath:" + mPhotoLocalPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PhotoReviewDialog dialog = PhotoReviewDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.setTargetFragment(ContextureFragment.this, REQ_ADD_SUBTITLE_PHOTO);
                            dialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getContext(), "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setOnPickCancel(() -> {
                        //TODO: do what you have to if user clicked cancel
                    }).show(getFragmentManager());

        }

        class ViewHolderItem extends RecyclerView.ViewHolder {
            public ItemGalleryBinding binding;

            ViewHolderItem(ItemGalleryBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    public class LLAudioAdapter implements AudioMemoEventListener.AudioItemClickListener {
        private final MediaPlayer mediaPlayer;
        LinearLayout linearLayout;
        List<AudioMemo> arrayList;

        public LLAudioAdapter(List<AudioMemo> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
            mediaPlayer = new MediaPlayer();
        }

        public void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public AudioMemo getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemAudioListBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_list, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemAudioListBinding) convertView.getTag();
            }

            binding.setAudio(getItem(position));
            binding.setOnAudioClickListener(this);
            FileViewModel viewModel = ViewModelProviders.of(ContextureFragment.this, factory).get(FileViewModel.class);
            viewModel.initial(linearLayout.getContext(), BASE_URL + getItem(position).getUrl(), getItem(position).getTitle());

            binding.setFile(viewModel.getFileDownloaded());
            //
            return binding.getRoot();

        }

        public void onAudioItemClicked(AudioMemo audioMemo) {

        }

        @Override
        public void onPlayPauseChanged(CompoundButton compoundButton, boolean b, FileDownloaded fileDownloaded) {
            Toast.makeText(linearLayout.getContext(), "checked change:" + b, Toast.LENGTH_SHORT).show();
            if (b) {
                try {
                    if (fileDownloaded.getLocalPath() != null && !fileDownloaded.getLocalPath().isEmpty())
                        mediaPlayer.setDataSource(fileDownloaded.getLocalPath());

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(
                            mediaPlayer2 -> {
                                compoundButton.setChecked(false);
                                mediaPlayer.reset();
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        }

        public void onAddNewAudioClicked(View view) {

        }

    }

}
