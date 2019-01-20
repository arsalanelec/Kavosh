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
import com.example.arsalan.kavosh.databinding.ActivitySurFoundDetailBinding;
import com.example.arsalan.kavosh.databinding.ItemAudioListBinding;
import com.example.arsalan.kavosh.databinding.ItemGalleryBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.PhotoReviewFromActivityDialog;
import com.example.arsalan.kavosh.dialog.VoiceRecordFromActivityDialog;
import com.example.arsalan.kavosh.fragment.ArchitectureFragment;
import com.example.arsalan.kavosh.fragment.BlankFragment;
import com.example.arsalan.kavosh.fragment.PotteryFragment;
import com.example.arsalan.kavosh.fragment.StonyToolFragment;
import com.example.arsalan.kavosh.interfaces.AudioMemoEventListener;
import com.example.arsalan.kavosh.interfaces.PhotoItemClickListener;
import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.room.AudioDao;
import com.example.arsalan.kavosh.room.PhotoDao;
import com.example.arsalan.kavosh.room.SurFoundDao;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.AudioUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.PhotoUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.SurFoundDeleteWorker;
import com.example.arsalan.kavosh.wokrmanager.SurFoundUploadWorker;
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
import androidx.lifecycle.LiveData;
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

public class SurFoundDetailActivity extends AppCompatActivity implements Injectable, HasSupportFragmentInjector
        , ArchitectureFragment.OnFragmentInteractionListener
        , PotteryFragment.OnFragmentInteractionListener
        , StonyToolFragment.OnFragmentInteractionListener
        , PhotoReviewFromActivityDialog.OnFragmentInteractionListener
        , VoiceRecordFromActivityDialog.OnFragmentInteractionListener {
    private static final String TAG = "SurFoundDetailActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    SurFoundDao mSurFoundDao;
    SurFound mSurFound;
    @Inject
    AudioDao mAudioDao;
    @Inject
    PhotoDao mPhotoDao;
    @Inject
    MyViewModelFactory mFactory;
    private PhotoAdapter mGalleryAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();
    private LLAudioAdapter mAudioAdapter;
    private List<AudioMemo> mAudioLst = new ArrayList<>();
    private String mPhotoLocalPath;
    private Context mContext;

    public SurFoundDetailActivity() {
        mContext = this;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySurFoundDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sur_found_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LiveData<SurFound> surFoundLiveData = mSurFoundDao.loadById(getIntent().getExtras().getString(MyConst.EXTRA_ID));
        surFoundLiveData.observe(SurFoundDetailActivity.this, surFound -> {
            Log.d(TAG, "surFoundLiveData.observe: ");
            if (surFound != null) {
                mSurFound = surFound;
            } else {
                mSurFound = new SurFound();
            }
            setupPhotoAndAudioRecycler();
            Fragment fragment;
            switch (mSurFound.getType()) {
                case 0:
                    fragment = ArchitectureFragment.newInstance(mSurFound.getContentJson());
                    setTitle("یافته معماری");
                    break;
                case 1:
                    fragment = PotteryFragment.newInstance(mSurFound.getContentJson());
                    setTitle("یافته سفال");
                    break;
                case 2:
                    fragment = StonyToolFragment.newInstance(mSurFound.getContentJson());
                    setTitle("یافته ابزار سنگی");
                    break;
                default:
                    fragment = new BlankFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            surFoundLiveData.removeObservers(SurFoundDetailActivity.this);
        });

        mAudioAdapter = new LLAudioAdapter(mAudioLst, binding.llAudioList);

        binding.rvGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mGalleryAdapter = new PhotoAdapter(mPhotoList);
        binding.rvGallery.setAdapter(mGalleryAdapter);

        binding.btnAddAudio.setOnClickListener(view -> {
            VoiceRecordFromActivityDialog dialog = new VoiceRecordFromActivityDialog();
            dialog.show(getSupportFragmentManager(), "");
        });
        binding.btnAddPhoto.setOnClickListener(v -> {
            Log.d(TAG, "onAddNewPhotoClicked: ");
            Toast.makeText(this, "onAddNewPhotoClicked", Toast.LENGTH_SHORT).show();

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
                                File f = new File(this.getCacheDir(), imgFile.getName());
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
                                mPhotoLocalPath = this.getCacheDir() + "/" + imgFile.getName();
                                Log.d(TAG, "onAddNewPhotoClicked: localpath:" + mPhotoLocalPath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            PhotoReviewFromActivityDialog dialog = PhotoReviewFromActivityDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.show(getSupportFragmentManager(), "");
                        } else {
                            Toast.makeText(this, "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).setOnPickCancel(() -> {
            }).show(getSupportFragmentManager());
        });
    }

    private void setupPhotoAndAudioRecycler() {
        AudioListViewModel audioVM = ViewModelProviders.of(SurFoundDetailActivity.this, mFactory).get(AudioListViewModel.class);
        audioVM.initial(mSurFound.getId());
        audioVM.getAudioMemoList().observe(SurFoundDetailActivity.this, audioMemoList -> {
            mAudioLst.removeAll(mAudioLst);
            mAudioLst.addAll(audioMemoList);
            mAudioAdapter.notifyDataSetChanged();
        });

        PhotoListViewModel photoListViewModel = ViewModelProviders.of(SurFoundDetailActivity.this, mFactory).get(PhotoListViewModel.class);
        photoListViewModel.initial(mSurFound.getId());
        photoListViewModel.getPhotoList().observe(SurFoundDetailActivity.this, photos -> {
            Log.d(TAG, "PhotoListViewModel onChanged: photos:" + photos.size());
            if (photos != null) {
                mPhotoList.removeAll(mPhotoList);
                mPhotoList.addAll(photos);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onUpdateSurFound(String json) {
        mSurFound.setContentJson(json);
        mSurFoundDao.save(mSurFound);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", mSurFound.getId())
                .putString("content_json", mSurFound.getContentJson())
                .build();

        OneTimeWorkRequest uploadFound = new OneTimeWorkRequest.Builder(SurFoundUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance().enqueue(uploadFound);
    }

    @Override
    public void onPhotoTitleAdded(String title) {
        Photo photo = new Photo();
        photo.setLocalPath(mPhotoLocalPath);
        photo.setPhotoableId(mSurFound.getId());
        photo.setTitle(title);
        photo.setPhotoableType("App\\SurFound");
        mPhotoDao.save(photo);
        uploadPhoto(photo);
        Toast.makeText(mContext, "عکس ذخیره شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addVoiceFile(String path, String fileName) {
        AudioMemo audio = new AudioMemo();
        audio.setAudioable_id(mSurFound.getId());
        audio.setAudioable_type("App\\SurFound");
        audio.setTitle(fileName);
        audio.setLocalPath(path);
        mAudioDao.save(audio);
        uploadAudio(audio);
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
                .observe(SurFoundDetailActivity.this, workStatus -> {
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
                .observe(SurFoundDetailActivity.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "uploadWorker: finished!");
                    } else if (workStatus != null) {
                        Log.d(TAG, "status:" + workStatus);
                    }
                });
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
                            deleteSurvey(mSurFound.getId());
                            finish();
                        })
                        .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create().show();
                return true;
        }
        return false;
    }

    private void deleteSurvey(String id) {
        mSurFoundDao.delete(id);

        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString(MyConst.EXTRA_ID, id).build();

        OneTimeWorkRequest Work = new OneTimeWorkRequest.Builder(SurFoundDeleteWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(Work);

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
            intent.setClass(SurFoundDetailActivity.this, GalleryActivity.class);
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
            FileViewModel viewModel = ViewModelProviders.of(SurFoundDetailActivity.this, mFactory).get(FileViewModel.class);
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
