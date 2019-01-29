package com.example.arsalan.kavosh.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.GalleryActivity;
import com.example.arsalan.kavosh.databinding.FragmentWallBinding;
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
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.WallFeature;
import com.example.arsalan.kavosh.viewModel.AudioListViewModel;
import com.example.arsalan.kavosh.viewModel.FileViewModel;
import com.example.arsalan.kavosh.viewModel.PhotoListViewModel;
import com.example.arsalan.kavosh.viewModel.WallViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FtrWallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FtrWallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FtrWallFragment extends androidx.fragment.app.Fragment implements Injectable {
    private static final String TAG = "FtrWallFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_COMPOSITION = 1;
    private static final int REQ_RECORD_AUDIO = 2;
    private static final int REQ_ADD_SUBTITLE_PHOTO = 3;
    private static final int REQ_EDIT_COMPOSITION = 4;
    private static final int REQ_REMOVE_COMPOSITION = 5;
    @Inject
    MyViewModelFactory factory;
    private String mLayerName;

    private List<Composition> mCompositionList;
    private LLCompositionAdapter mLlCompositionAdapter;
    private PhotoAdapter mGalleryAdapter;
    private ArrayList<Photo> mPhotoList = new ArrayList<Photo>();
    private LLAudioAdapter mAudioAdapter;
    private List<AudioMemo> mAudioLst = new ArrayList<>();
    private String mPhotoLocalPath;
    private OnFragmentInteractionListener mListener;
    private WallViewModel mWallViewModel;

    public FtrWallFragment() {
        // Required empty public constructor
    }
    private String mFeatureId;
    private String mFeatureContent;
    public static FtrWallFragment newInstance(String featureId, String contentJson) {
        FtrWallFragment fragment = new FtrWallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, featureId);
        args.putString(ARG_PARAM2, contentJson);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFeatureId = getArguments().getString(ARG_PARAM1);
            mFeatureContent = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentWallBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wall, container, false);

        mCompositionList = new ArrayList<>();
        mLlCompositionAdapter = new LLCompositionAdapter(mCompositionList, binding.linearLayout);

        WallFeature wall;
        if (mFeatureContent != null && !mFeatureContent.isEmpty()) {
            Gson gson = new Gson();
            wall = gson.fromJson(mFeatureContent, WallFeature.class);
        } else {
            wall = new WallFeature();
        }

        try {
            Gson gson = new Gson();
            List<Composition> list = gson.fromJson(wall.getCompositionJson(), new TypeToken<List<Composition>>() {
            }.getType());
            mCompositionList.removeAll(mCompositionList);
            mCompositionList.addAll(list);
            mLlCompositionAdapter.notifyDataSetChanged();

        } catch (Exception e) {

        }
        binding.rvGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mGalleryAdapter = new PhotoAdapter(mPhotoList);
        binding.rvGallery.setAdapter(mGalleryAdapter);

        mAudioAdapter = new LLAudioAdapter(mAudioLst, binding.llAudioList);

        AudioListViewModel audioVM = ViewModelProviders.of(FtrWallFragment.this, factory).get(AudioListViewModel.class);
        audioVM.initial(mFeatureId);
        audioVM.getAudioMemoList().observe(FtrWallFragment.this, audioMemoList -> {
            mAudioLst.removeAll(mAudioLst);
            mAudioLst.addAll(audioMemoList);
            mAudioAdapter.notifyDataSetChanged();
        });
        setupPhotoRecycler();

        mWallViewModel = new WallViewModel(wall);
        //mWallViewModel.setWall(wall);
        binding.setWall(mWallViewModel);

        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_soil_color));
        binding.txtSoilColor.setAdapter(textAdapter1);
        binding.txtCoatingColor.setAdapter(textAdapter1);
        binding.txtMortarColor.setAdapter(textAdapter1);

        mWallViewModel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d(TAG, "onPropertyChanged: sender:mWallViewModel wall:" + mWallViewModel.getWall());
                try {
                    Gson gson = new Gson();
                    String contentJson = gson.toJson(mWallViewModel.getWall());

                    List<Composition> list = gson.fromJson(mWallViewModel.getWallComposition(), new TypeToken<List<Composition>>() {
                    }.getType());
                    mCompositionList.removeAll(mCompositionList);
                    mCompositionList.addAll(list);
                    mLlCompositionAdapter.notifyDataSetChanged();
                    mListener.onUpdateFeature(contentJson);

                } catch (Exception e) {

                }
            }
        });
        binding.btnAddAudio.setOnClickListener(view -> {
            VoiceRecordDialog dialog = new VoiceRecordDialog();
            dialog.setTargetFragment(FtrWallFragment.this, REQ_RECORD_AUDIO);
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

                            PhotoReviewDialog dialog = PhotoReviewDialog.newInstance(String.valueOf(r.getUri()));
                            dialog.setTargetFragment(FtrWallFragment.this, REQ_ADD_SUBTITLE_PHOTO);
                            dialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getContext(), "خطا:" + r.getError().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).setOnPickCancel(() -> {
                //TODO: do what you have to if user clicked cancel
            }).show(getFragmentManager());
        });

        binding.btnAddComposition.setOnClickListener(view -> {
            AddCompositionDialog dialog = new AddCompositionDialog();
            dialog.setTargetFragment(FtrWallFragment.this, REQ_ADD_COMPOSITION);
            dialog.show(getFragmentManager(), "");
        });


        return binding.getRoot();
    }

    private void setupPhotoRecycler() {
        PhotoListViewModel photoListViewModel = ViewModelProviders.of(FtrWallFragment.this, factory).get(PhotoListViewModel.class);
        photoListViewModel.initial(mFeatureId);
        photoListViewModel.getPhotoList().observe(FtrWallFragment.this, photos -> {
            Log.d(TAG, "PhotoListViewModel onChanged: ");
            if (photos != null) {
                mPhotoList.removeAll(mPhotoList);
                mPhotoList.addAll(photos);
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ADD_COMPOSITION) {
            if (resultCode == RESULT_OK) {
                Composition composition = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                mCompositionList.add(composition);
                mLlCompositionAdapter.notifyDataSetChanged();
                Gson gson = new Gson();
                String string = gson.toJson(mCompositionList);
                Log.d(TAG, "onActivityResult: gson:" + string);
                mWallViewModel.setWallComposition(string);
            }
        }
        if (requestCode == REQ_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                AudioMemo audio = new AudioMemo();
                audio.setAudioable_id(mFeatureId);
                audio.setAudioable_type("App\\FEATURE");
                audio.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                audio.setLocalPath(data.getStringExtra(MyConst.EXTRA_FILE_PATH));
                mListener.onUploadAudio(audio);
            }
        }
        if (requestCode == REQ_ADD_SUBTITLE_PHOTO) {
            if (resultCode == RESULT_OK) {

                Photo photo = new Photo();
                photo.setLocalPath(mPhotoLocalPath);
                photo.setPhotoableId(mFeatureId);
                photo.setTitle(data.getStringExtra(MyConst.EXTRA_FILE_TITLE));
                photo.setPhotoableType("App\\FEATURE");
                mListener.onUploadPhoto(photo);
                Toast.makeText(getContext(), "عکس ذخیره شد", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQ_EDIT_COMPOSITION) {
            mCompositionList.remove(data.getIntExtra(MyConst.EXTRA_INDEX, 0));
            mCompositionList.add(data.getParcelableExtra(MyConst.EXTRA_MODEL));
            mLlCompositionAdapter.notifyDataSetChanged();
            Gson gson = new Gson();
            String string = gson.toJson(mCompositionList);
            Log.d(TAG, "onActivityResult: gson:" + string);
            mWallViewModel.setWallComposition(string);

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
        void onUpdateFeature(String  contentJson);

        void onUploadAudio(AudioMemo audio);

        void onUploadPhoto(Photo photo);
    }

    public interface CompositionEventListener {
        void onCompositionClicked(Composition composition, View view, int index);


    }

    private class LLCompositionAdapter implements CompositionEventListener {
        LinearLayout linearLayout;
        List<Composition> arrayList;

        public LLCompositionAdapter(List<Composition> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            if (arrayList.size() > 0) {
                View header = LayoutInflater.from(getContext()).inflate(R.layout.header_composition, null);
                linearLayout.addView(header);
            }
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
                //add divider
                View divider = new View(getContext());
                divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.item_divider_height)));
                divider.setBackgroundColor(getResources().getColor(R.color.gray));
                if (i + 1 < getCount()) linearLayout.addView(divider);

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
            binding.setEventListener(LLCompositionAdapter.this);
            binding.setThisView(binding.textViewOptions);
            binding.setIndex(position);
            return binding.getRoot();

        }

        @Override
        public void onCompositionClicked(Composition composition, View view, int index) {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(getContext(), view);
            //inflating menu from xml resource

            popup.inflate(R.menu.menu_delete_edit);

            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        TextView titleTV = new TextView(getContext());
                        titleTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        titleTV.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        titleTV.setText("حذف");
                        int dip = 8;
                        int px = (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                dip,
                                getResources().getDisplayMetrics()
                        );
                        titleTV.setPadding(px, px, px, px);
                        new AlertDialog.Builder(getContext())
                                .setCustomTitle(titleTV)
                                .setMessage("آیا مایلید این مورد حذف شود؟")
                                .setPositiveButton("بلی", (dialogInterface, i) -> {
                                    mCompositionList.remove(index);
                                    notifyDataSetChanged();
                                    Gson gson = new Gson();
                                    String string = gson.toJson(mCompositionList);
                                    Log.d(TAG, "onActivityResult: gson:" + string);
                                    mWallViewModel.setWallComposition(string);
                                    dialogInterface.dismiss();
                                })
                                .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                                .create().show();
                        return true;
                    case R.id.menu_edit:
                        AddCompositionDialog dialog = AddCompositionDialog.newInstance(composition, index);
                        dialog.setTargetFragment(FtrWallFragment.this, REQ_EDIT_COMPOSITION);
                        dialog.show(getFragmentManager(), "");
                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popup.show();

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
            FileViewModel viewModel = ViewModelProviders.of(FtrWallFragment.this, factory).get(FileViewModel.class);
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
