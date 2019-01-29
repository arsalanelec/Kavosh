package com.example.arsalan.kavosh.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.ActivityFoundBinding;
import com.example.arsalan.kavosh.databinding.ItemAudioListBinding;
import com.example.arsalan.kavosh.databinding.ItemCompositionBinding;
import com.example.arsalan.kavosh.databinding.ItemGalleryBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.PhotoReviewDialog;
import com.example.arsalan.kavosh.dialog.PhotoReviewFromActivityDialog;
import com.example.arsalan.kavosh.dialog.VoiceRecordFromActivityDialog;
import com.example.arsalan.kavosh.interfaces.AudioMemoEventListener;
import com.example.arsalan.kavosh.interfaces.PhotoItemClickListener;
import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Composition;
import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.FoundDetail;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.FoundDao;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.FoundDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.FoundUploadWorker;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

public class FoundActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector
        , PhotoReviewFromActivityDialog.OnFragmentInteractionListener
        , VoiceRecordFromActivityDialog.OnFragmentInteractionListener {
    private static final String TAG = "FoundActivity";
    private static final int REQ_ADD_SUBTITLE_PHOTO = 1;
    @Inject
    FoundDao mFoundDao;
    @Inject
    Token mToken;
    @Inject
    PhotoDao mPhotoDao;
    @Inject
    MyViewModelFactory factory;
    @Inject
    AudioDao mAudioDao;
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private String mLayerName = "";
    private int mFoundType = 0;
    private String mRegistrationNum;
    private boolean mEditMode = false;
    private String mExcavationItemId;
    private PhotoAdapter mGalleryAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();
    private List<Composition> mCompositionList;
    private LLAudioAdapter mAudioAdapter;
    private List<AudioMemo> mAudioLst = new ArrayList<>();
    private String mPhotoLocalPath;
    private Context mContext;
    private Found mFound;
    private FoundDetail mFoundDetail;


    public FoundActivity() {
        mContext = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFoundBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_found);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGalleryAdapter = new PhotoAdapter(mPhotoList);
        binding.rvGallery.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        binding.rvGallery.setAdapter(mGalleryAdapter);
        mAudioAdapter = new LLAudioAdapter(mAudioLst, binding.llAudioList);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
           /* mLayerName = bundle.getString(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_NAME);
            mFoundType = bundle.getInt(MyConst.EXTRA_TYPE);
            mRegistrationNum = bundle.getString(MyConst.EXTRA_REGISTRATION_CODING);
            mExcavationItemId = bundle.getString(MyConst.EXTRA_EXCAVATION_ITEM_ID);
            mEditMode = bundle.getBoolean(MyConst.EXTRA_EDITABLE, false);*/
            mFound = bundle.getParcelable(MyConst.EXTRA_MODEL);
            Gson gson = new Gson();
            mFoundDetail = gson.fromJson(mFound.getContentJson(), FoundDetail.class);
            binding.txtRegistrationNum.setText(mFoundDetail.getRegisterNum());
            binding.etName.setText(mFoundDetail.getName());
            binding.etDescription.setText(mFoundDetail.getDescription());
            binding.etQuantity.setText(String.valueOf(mFoundDetail.getQuantity()));
            binding.etWeight.setText(String.valueOf(mFoundDetail.getWeight()));
            binding.etX.setText(String.valueOf(mFoundDetail.getX()));
            binding.etY.setText(String.valueOf(mFoundDetail.getY()));
            binding.etZ.setText(String.valueOf(mFoundDetail.getZ()));
            if (mFoundDetail.getContainerType() > 1)
                binding.spnContainerType.setSelection(mFoundDetail.getContainerType() - 1);
            if (mFoundDetail.getFoundType() > 1)
                binding.spnFoundType.setSelection(mFoundDetail.getFoundType() - 1);
            Log.d(TAG, "onCreate: getRegisterNum:" + mFoundDetail.getRegisterNum());
            setupPhotoAndAudioRecycler();
        } else {
            throw new RuntimeException(getClass().getSimpleName()
                    + "bundle is Null");
           /* Log.d(TAG, "onCreate: Bundle is Null");

            mFound = new Found();
            mFoundDetail = new FoundDetail();*/
        }

        binding.btnSubmit.setOnClickListener(view -> {
            mFoundDetail.setName(binding.etName.getText().toString());
            mFoundDetail.setDescription(binding.etDescription.getText().toString());
            mFoundDetail.setX(binding.etX.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etX.getText().toString()));
            mFoundDetail.setY(binding.etY.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etY.getText().toString()));
            mFoundDetail.setZ(binding.etZ.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etZ.getText().toString()));
            mFoundDetail.setQuantity(binding.etQuantity.getText().toString().isEmpty() ? 0 : Integer.valueOf(binding.etQuantity.getText().toString()));
            mFoundDetail.setWeight(binding.etWeight.getText().toString().isEmpty() ? 0 : Double.parseDouble(binding.etWeight.getText().toString()));
            mFoundDetail.setFoundType(binding.spnFoundType.getSelectedItemPosition() + 1);
            mFoundDetail.setContainerType(binding.spnContainerType.getSelectedItemPosition() + 1);
            // mFoundDetail.setType(mFoundType);
            //mFoundDetail.setLayerName(mLayerName);
            Gson gson = new Gson();
            String foundJson = gson.toJson(mFoundDetail);
            mFound.setContentJson(foundJson);
            mFoundDao.save(mFound);
            createUpdateFound(mFound);
            /* *//*Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_MODEL, foundJson);
            intent.putExtra(MyConst.EXTRA_TYPE, mFoundType);
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            dismiss();*//*

            Found found = new Found();
            found.setContentJson(foundJson);
            found.setType(mFoundType);
            found.setSurvey_id(mExcavationItemId);

            createUpdateFound(found);*/
            onBackPressed();
            // foundDetail.setRegisterNum(mListener.getNewRegistrationCode());
        });
        binding.btnCancel.setOnClickListener(b -> onBackPressed());
        binding.btnAddAudio.setOnClickListener(view -> {
            VoiceRecordFromActivityDialog dialog = new VoiceRecordFromActivityDialog();
            dialog.show(getSupportFragmentManager(), "");
        });
        binding.btnAddPhoto.setOnClickListener(v -> {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(mContext, "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();
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
                                File f = new File(mContext.getCacheDir(), imgFile.getName());
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
                                mPhotoLocalPath = mContext.getCacheDir() + "/" + imgFile.getName();
                                Log.d(TAG, "onAddNewPhotoClicked: localpath:" + mPhotoLocalPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PhotoReviewFromActivityDialog dialog = PhotoReviewFromActivityDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.show(getSupportFragmentManager(), "");
                        } else {
                            Toast.makeText(mContext, "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).setOnPickCancel(() -> {
                //TODO: do what you have to if user clicked cancel
            }).show(getSupportFragmentManager());
        });
    }

    private void setupPhotoAndAudioRecycler() {
        PhotoListViewModel photoListViewModel = ViewModelProviders.of(FoundActivity.this, factory).get(PhotoListViewModel.class);
        photoListViewModel.initial(mFound.getId());
        photoListViewModel.getPhotoList().observe(FoundActivity.this, photos -> {
            Log.d(TAG, "PhotoListViewModel onChanged: ");
            if (photos != null) {
                mPhotoList.removeAll(mPhotoList);
                mPhotoList.addAll(photos);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
        AudioListViewModel audioVM = ViewModelProviders.of(FoundActivity.this, factory).get(AudioListViewModel.class);
        audioVM.initial(mFound.getId());
        audioVM.getAudioMemoList().observe(FoundActivity.this, audioMemoList -> {
            mAudioLst.removeAll(mAudioLst);
            mAudioLst.addAll(audioMemoList);
            mAudioAdapter.notifyDataSetChanged();
        });
    }

    private void createUpdateFound(Found found) {


        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        mFoundDao.save(found);
        Data inputData = new Data.Builder()
                .putString("token", mToken.getAccessToken())
                .putString("id", found.getId())
                .putString("excavation_item_id", found.getExcavationItemId())
                .putString("content_json", found.getContentJson())
                .putString("type", String.valueOf(found.getType()))
                .putString("layer_feature_id", found.getLayerFeatureId())
                .build();
        OneTimeWorkRequest uploadFound = new OneTimeWorkRequest.Builder(FoundUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance().enqueue(uploadFound);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                TextView titleTV = new TextView(this);
                titleTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                int dip = 8;
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        getResources().getDisplayMetrics()
                );
                titleTV.setPadding(px, px, px, px);
                titleTV.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                titleTV.setText("حذف");
                new AlertDialog.Builder(this)
                        .setCustomTitle(titleTV)
                        .setMessage("آیا مایلید این مورد حذف شود؟")
                        .setPositiveButton("بلی", (dialogInterface, i) -> {
                            deleteFound(mFound.getId());
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }

    private void deleteFound(String id) {
        mFoundDao.delete(id);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_ID, id).build();


        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(FoundDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onPhotoTitleAdded(String title) {
        Photo photo = new Photo();
        photo.setLocalPath(mPhotoLocalPath);
        photo.setPhotoableId(mFound.getId());
        photo.setTitle(title);
        photo.setPhotoableType("App\\Found");
        mPhotoDao.save(photo);
        uploadPhoto(photo);
        Toast.makeText(mContext, "عکس ذخیره شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addVoiceFile(String path, String fileName) {
        AudioMemo audio = new AudioMemo();
        audio.setAudioable_id(mFound.getId());
        audio.setAudioable_type("App\\Found");
        audio.setTitle(fileName);
        audio.setLocalPath(path);
        mAudioDao.save(audio);
        uploadAudio(audio);
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
                .observe(this, workStatus -> {
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
                .observe(this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "uploadWorker: finished!");
                    } else if (workStatus != null) {
                        Log.d(TAG, "status:" + workStatus);
                    }
                });
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
        public PhotoAdapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            ItemGalleryBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_gallery, viewGroup, false);

            return new PhotoAdapter.ViewHolderItem(binding);


        }

        @Override
        public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolderItem viewHolderItem, int i) {
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
            intent.setClass(mContext, GalleryActivity.class);
            intent.putExtra(MyConst.EXTRA_INDEX, photoList.indexOf(photo));
            intent.putParcelableArrayListExtra(MyConst.EXTRA_MODEL_List, mPhotoList);
            startActivity(intent);
        }

        @Override
        public void onAddNewPhotoClicked(View view) {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(mContext, "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();
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
                                File f = new File(mContext.getCacheDir(), imgFile.getName());
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
                                mPhotoLocalPath = mContext.getCacheDir() + "/" + imgFile.getName();
                                Log.d(TAG, "onAddNewPhotoClicked: localpath:" + mPhotoLocalPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            PhotoReviewDialog dialog = PhotoReviewDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.show(getSupportFragmentManager(), "");
                        } else {
                            Toast.makeText(mContext, "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setOnPickCancel(() -> {
                        //TODO: do what you have to if user clicked cancel
                    }).show(getSupportFragmentManager());

        }

        class ViewHolderItem extends RecyclerView.ViewHolder {
            public ItemGalleryBinding binding;

            ViewHolderItem(ItemGalleryBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
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
            View header = LayoutInflater.from(mContext).inflate(R.layout.header_composition, null);
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
            FileViewModel viewModel = ViewModelProviders.of(FoundActivity.this, factory).get(FileViewModel.class);
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
