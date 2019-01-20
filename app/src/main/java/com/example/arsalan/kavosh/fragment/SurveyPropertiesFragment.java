package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.GalleryActivity;
import com.example.arsalan.kavosh.databinding.FragmentSurveyPropertiesBinding;
import com.example.arsalan.kavosh.databinding.ItemAroundFeatureBinding;
import com.example.arsalan.kavosh.databinding.ItemAudioListBinding;
import com.example.arsalan.kavosh.databinding.ItemGalleryBinding;
import com.example.arsalan.kavosh.databinding.ItemSurveyPropertiesBinding;
import com.example.arsalan.kavosh.databinding.ItemVegetationBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddAroundFeatureDialog;
import com.example.arsalan.kavosh.dialog.AddGeoFeatureDialog;
import com.example.arsalan.kavosh.dialog.AddVegetationDialog;
import com.example.arsalan.kavosh.dialog.PhotoReviewDialog;
import com.example.arsalan.kavosh.dialog.VoiceRecordDialog;
import com.example.arsalan.kavosh.interfaces.AudioMemoEventListener;
import com.example.arsalan.kavosh.interfaces.PhotoItemClickListener;
import com.example.arsalan.kavosh.model.AroundFeature;
import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.GeoFeature;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.SurveyDetail;
import com.example.arsalan.kavosh.model.Vegetation;
import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.PhotoUploadWorker;
import com.google.gson.Gson;
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
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyPropertiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyPropertiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyPropertiesFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_GEO_FEATUEE = 1;
    private static final String TAG = "SurveyPropertiesFragmen";
    private static final int REQ_ADD_Vegetation = 2;
    private static final int REQ_ADD_AroundFeature = 3;
    private static final int REQ_RECORD_AUDIO = 4;
    private static final int REQ_ADD_SUBTITLE_PHOTO = 5;
    @Inject
    MyViewModelFactory mFactory;
    @Inject
    AudioDao mAudioDao;
    @Inject
    PhotoDao mPhotoDao;
    // TODO: Rename and change types of parameters
    private String String;
    private OnFragmentInteractionListener mListener;
    private SurveyDetail mSurveyDetail;
    private List<GeoFeature> mGeoFeatureList;
    private LLGeoFeatureAdapter mGeoFeatureAdapter;
    private List<Vegetation> mVegetationList;
    private LLVegetationAdapter mVegetationAdapter;
    private PhotoAdapter mGalleryAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();
    private LLAudioAdapter mAudioAdapter;
    private List<AudioMemo> mAudioLst = new ArrayList<>();
    private List<AroundFeature> mAroundFeatureList;
    private LLAroundFeatureAdapter mAroundFeatureAdapter;
    private String mSurveyId;
    private String mPhotoLocalPath;

    public SurveyPropertiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param jsonContent Parameter 1.
     * @return A new instance of fragment SurveyPropertiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyPropertiesFragment newInstance(String jsonContent, String id) {
        SurveyPropertiesFragment fragment = new SurveyPropertiesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, jsonContent);
        args.putString(ARG_PARAM2, id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String surveyDetailJson = getArguments().getString(ARG_PARAM1);
            Gson gson = new Gson();
            mSurveyDetail = gson.fromJson(surveyDetailJson, SurveyDetail.class);
            mSurveyId = getArguments().getString(ARG_PARAM2);
        }
        if (mSurveyDetail == null) {
            mSurveyDetail = new SurveyDetail();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSurveyPropertiesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_properties, container, false);

        mGeoFeatureList = new ArrayList<>();
        mGeoFeatureList.addAll(mSurveyDetail.getGeoFeatureList());
        mGeoFeatureAdapter = new LLGeoFeatureAdapter(mGeoFeatureList, binding.linearLayoutGeoFeature);
        binding.btnAddGeoFeature.setOnClickListener(b -> {
            AddGeoFeatureDialog dialog = new AddGeoFeatureDialog();
            dialog.setTargetFragment(SurveyPropertiesFragment.this, REQ_ADD_GEO_FEATUEE);
            dialog.show(getFragmentManager(), "");
        });

        if (mSurveyDetail.getDescription() != null)
            binding.etDescription.setText(mSurveyDetail.getDescription());
        binding.etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mSurveyDetail.getDescription() != null && !mSurveyDetail.getDescription().equals(binding.etDescription.getText().toString())) {
                    mSurveyDetail.setDescription(binding.etDescription.getText().toString());
                    mListener.onSurveyDetailUpdate(mSurveyDetail);
                }
            }
        });

        mVegetationList = new ArrayList<>();
        mVegetationList.addAll(mSurveyDetail.getVegetationList());
        mVegetationAdapter = new LLVegetationAdapter(mVegetationList, binding.llVegetation);
        binding.btnAddVegetation.setOnClickListener(b -> {
            AddVegetationDialog dialog = new AddVegetationDialog();
            dialog.setTargetFragment(SurveyPropertiesFragment.this, REQ_ADD_Vegetation);
            dialog.show(getFragmentManager(), "");
        });

        mAroundFeatureList = new ArrayList<>();
        mAroundFeatureList.addAll(mSurveyDetail.getAroundFeatureList());
        mAroundFeatureAdapter = new LLAroundFeatureAdapter(mAroundFeatureList, binding.llAroundFeature);
        binding.btnAddAroundFeature.setOnClickListener(b -> {
            AddAroundFeatureDialog dialog = new AddAroundFeatureDialog();
            dialog.setTargetFragment(SurveyPropertiesFragment.this, REQ_ADD_AroundFeature);
            dialog.show(getFragmentManager(), "");
        });
        binding.setSurveyDetail(mSurveyDetail);
        mSurveyDetail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                mListener.onSurveyDetailUpdate(mSurveyDetail);
                Log.d(TAG, "onPropertyChanged: ");
            }
        });

        mAudioAdapter = new LLAudioAdapter(mAudioLst, binding.llAudioList);

        binding.rvGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mGalleryAdapter = new PhotoAdapter(mPhotoList);
        binding.rvGallery.setAdapter(mGalleryAdapter);

        binding.btnAddAudio.setOnClickListener(view -> {
            VoiceRecordDialog dialog = new VoiceRecordDialog();
            dialog.setTargetFragment(SurveyPropertiesFragment.this, REQ_RECORD_AUDIO);
            dialog.show(getFragmentManager(), "");
        });
        binding.btnAddPhoto.setOnClickListener(v -> {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(getContext(), "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();
            PickSetup setup = new PickSetup()
                    .setTitle("گرفتن عکس")
                    .setCancelText("انصراف")
                    .setMaxSize(10)
                    .setWidth(1600).setHeight(1200)
                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                    .setCameraButtonText("دوربین")
                    .setGalleryButtonText("گالری")
                    .setIconGravity(Gravity.LEFT)
                    .setButtonOrientation(LinearLayout.VERTICAL)
                    .setSystemDialog(false);
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

                            PhotoReviewDialog dialog = PhotoReviewDialog.newInstance(java.lang.String.valueOf(r.getUri()));
                            dialog.setTargetFragment(SurveyPropertiesFragment.this, REQ_ADD_SUBTITLE_PHOTO);
                            dialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getContext(), "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).setOnPickCancel(() -> {
                //TODO: do what you have to if user clicked cancel
            }).show(getFragmentManager());
        });
        setupPhotoAndAudioRecycler();

        return binding.getRoot();
    }

    private void setupPhotoAndAudioRecycler() {
        AudioListViewModel audioVM = ViewModelProviders.of(SurveyPropertiesFragment.this, mFactory).get(AudioListViewModel.class);
        audioVM.initial(mSurveyId);
        audioVM.getAudioMemoList().observe(SurveyPropertiesFragment.this, audioMemoList -> {
            mAudioLst.removeAll(mAudioLst);
            mAudioLst.addAll(audioMemoList);
            mAudioAdapter.notifyDataSetChanged();
        });

        PhotoListViewModel photoListViewModel = ViewModelProviders.of(SurveyPropertiesFragment.this, mFactory).get(PhotoListViewModel.class);
        photoListViewModel.initial(mSurveyId);
        photoListViewModel.getPhotoList().observe(SurveyPropertiesFragment.this, photos -> {
            Log.d(TAG, "PhotoListViewModel onChanged: ");
            if (photos != null) {
                mPhotoList.removeAll(mPhotoList);
                mPhotoList.addAll(photos);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_ADD_GEO_FEATUEE:
                if (resultCode == RESULT_OK) {
                    GeoFeature geoFeature = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mGeoFeatureList.add(geoFeature);
                    mGeoFeatureAdapter.notifyDataSetChanged();
                    mSurveyDetail.setGeoFeatureList(mGeoFeatureList);
                }
                break;
            case REQ_ADD_Vegetation:
                if (resultCode == RESULT_OK) {
                    Vegetation vegetation = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mVegetationList.add(vegetation);
                    mVegetationAdapter.notifyDataSetChanged();
                    mSurveyDetail.setVegetationList(mVegetationList);
                }
                break;
            case REQ_ADD_AroundFeature:
                if (resultCode == RESULT_OK) {
                    AroundFeature aroundFeature = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mAroundFeatureList.add(aroundFeature);
                    mAroundFeatureAdapter.notifyDataSetChanged();
                    mSurveyDetail.setAroundFeatureList(mAroundFeatureList);
                }
                break;
            case REQ_RECORD_AUDIO: {
                if (resultCode == RESULT_OK) {
                    AudioMemo audio = new AudioMemo();
                    audio.setAudioable_id(mSurveyId);
                    audio.setAudioable_type("App\\Survey");
                    audio.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                    audio.setLocalPath(data.getStringExtra(MyConst.EXTRA_FILE_PATH));
                    mAudioDao.save(audio);
                    uploadAudio(audio);

                }
            }
            break;
            case REQ_ADD_SUBTITLE_PHOTO: {
                if (resultCode == RESULT_OK) {

                    Photo photo = new Photo();
                    photo.setLocalPath(mPhotoLocalPath);
                    photo.setPhotoableId(mSurveyId);
                    photo.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                    photo.setPhotoableType("App\\Survey");
                    mPhotoDao.save(photo);
                    uploadPhoto(photo);
                    Toast.makeText(getContext(), "عکس ذخیره شد", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void uploadPhoto(Photo photo) {

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
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
                .observe(SurveyPropertiesFragment.this, workStatus -> {
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
                .observe(SurveyPropertiesFragment.this, workStatus -> {
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
        void onSurveyDetailUpdate(SurveyDetail surveyDetail);
    }

    private class LLGeoFeatureAdapter {
        LinearLayout linearLayout;
        List<GeoFeature> arrayList;

        public LLGeoFeatureAdapter(List<GeoFeature> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            // View header = LayoutInflater.from(getContext()).inflate(R.layout.header_composition, null);
            // linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public GeoFeature getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemSurveyPropertiesBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_properties, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemSurveyPropertiesBinding) convertView.getTag();
            }
            binding.setGeoFeature(getItem(position));
            return binding.getRoot();

        }
    }

    private class LLVegetationAdapter {
        LinearLayout linearLayout;
        List<Vegetation> arrayList;

        public LLVegetationAdapter(List<Vegetation> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.header_vegetation, null);
            linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Vegetation getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemVegetationBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vegetation, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemVegetationBinding) convertView.getTag();
            }
            binding.setVegetation(getItem(position));
            return binding.getRoot();

        }
    }

    private class LLAroundFeatureAdapter {
        LinearLayout linearLayout;
        List<AroundFeature> arrayList;

        public LLAroundFeatureAdapter(List<AroundFeature> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.header_around_feature, null);
            linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public AroundFeature getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemAroundFeatureBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_around_feature, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemAroundFeatureBinding) convertView.getTag();
            }
            binding.setAroundFeature(getItem(position));
            return binding.getRoot();

        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolderItem> implements PhotoItemClickListener {
        private static final int REQ_ADD_SUBTITLE_PHOTO = 1;
        private final int VIEW_TYPE_ADD_BUTTON = 1;
        private final int VIEW_TYPE_ITEM = 2;
        private ArrayList<Photo> photoList;

        public PhotoAdapter(ArrayList<Photo> photoList) {
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
            intent.putParcelableArrayListExtra(MyConst.EXTRA_MODEL_List, photoList);

            startActivity(intent);
        }

        @Override
        public void onAddNewPhotoClicked(View view) {
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
            FileViewModel viewModel = ViewModelProviders.of(SurveyPropertiesFragment.this, mFactory).get(FileViewModel.class);
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
