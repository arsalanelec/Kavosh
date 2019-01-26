package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.FeatureActivity;
import com.example.arsalan.kavosh.activities.LayerActivity;
import com.example.arsalan.kavosh.databinding.FragmentFeatureLayerListBinding;
import com.example.arsalan.kavosh.databinding.ItemLayerFeatureRecyclerBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddFeatureDialog;
import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.ContextureDao;
import com.example.arsalan.kavosh.room.FeatureDao;
import com.example.arsalan.kavosh.room.LayerDao;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.example.arsalan.kavosh.viewModel.LayerFeatureViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.ContextureUploadWorker;
import com.example.arsalan.kavosh.wokrmanager.FeatureCreateUpdateWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerFeatureSaveWorker;
import com.example.arsalan.kavosh.wokrmanager.LayerUploadWorker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LayerFeatureListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LayerFeatureListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LayerFeatureListFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "FeatureLayerListFragmen";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";
    private static final int REQ_ADD_FEATURE = 1;
    @Inject
    MyViewModelFactory factory;
    @Inject
    LayerFeatureDao mLayerFeatureDao;
    @Inject
    LayerDao mLayerDao;
    @Inject
    FeatureDao mFeatureDao;
    @Inject
    ContextureDao mContextureDao;
    @Inject
    Token mToken;
    private OnFragmentInteractionListener mListener;
    private List<LayerFeature> mLayerFeatureList = new ArrayList<>();
    private LayerFeatureAdapter mAdapter;
    private String mItemId;

    private String mItemCoding;
    private String mRegistrationCoding;
    private FragmentFeatureLayerListBinding mBinding;
    private String mLastLayerFeature;

    public LayerFeatureListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LayerFeatureListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LayerFeatureListFragment newInstance(String itemId, String itemLayerCoding, String registrationCoding) {
        LayerFeatureListFragment fragment = new LayerFeatureListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, itemId);
        args.putString(ARG_PARAM2, itemLayerCoding);
        args.putString(ARG_PARAM3, registrationCoding);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getString(ARG_PARAM1);
            mItemCoding = getArguments().getString(ARG_PARAM2);
            mRegistrationCoding = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feature_layer_list, container, false);
        mAdapter = new LayerFeatureAdapter(mLayerFeatureList);
        mBinding.rvLayerFeature.setAdapter(mAdapter);
        mBinding.rvLayerFeature.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mBinding.rvLayerFeature.setLayoutManager(new LinearLayoutManager(getContext()));

        mBinding.btnAddLayer.setOnClickListener(view -> {
            mBinding.btnAddLayer.setEnabled(false);
            mBinding.btnAddFeature.setEnabled(false);
            addNewExcavationLayerWeb(increaseCode(mItemCoding, mLayerFeatureList.size()), mItemId);
        });

        mBinding.btnAddFeature.setOnClickListener(view -> {
            // mBinding.btnAddLayer.setEnabled(false);
            // mBinding.btnAddFeature.setEnabled(false);
            AddFeatureDialog dialog = new AddFeatureDialog();
            dialog.setTargetFragment(LayerFeatureListFragment.this, REQ_ADD_FEATURE);
            dialog.show(getFragmentManager(), "");

        });
        View view = mBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ADD_FEATURE:
                if (resultCode == RESULT_OK) {
                    int structureIndex = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    CreateNewFeature(increaseCode(mItemCoding, mLayerFeatureList.size()), mItemId, structureIndex);
                }
                break;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayerFeatureViewModel viewModel = ViewModelProviders.of(LayerFeatureListFragment.this, factory).get(LayerFeatureViewModel.class);
        viewModel.initial(mItemId);
        viewModel.getLayerFeatureList().observe(LayerFeatureListFragment.this, layerFeatures -> {
            Log.d("onActivityCreated", "observe: photoList:" + layerFeatures);
            mLayerFeatureList.removeAll(mLayerFeatureList);
            mLayerFeatureList.addAll(layerFeatures);
            mAdapter.notifyDataSetChanged();
            mBinding.btnAddFeature.setEnabled(true);
            mBinding.btnAddLayer.setEnabled(true);
            //  waitingV.setVisibility(View.GONE);
            //    Log.d(TAG, "onActivityCreated: projectList:"+projects.toString());
        });

        Log.d(getClass().getSimpleName(), "onActivityCreated: ");
    }

    private String increaseCode(String codingStart, int count) {

        if (codingStart.length() > 0) {
            Character lastChar = codingStart.charAt(codingStart.length() - 1);
            String sample = codingStart;
            String pattern = sample.substring(0, codingStart.length() - 1);

            int cnt2 = 0;
            for (int cnt = 1; cnt <= count; cnt++) {
                if ((lastChar >= 65 && lastChar < 90)) {
                    lastChar++;
                    sample = pattern + (char) (lastChar);


                } else if (lastChar <= 57) {
                    cnt2++;
                    sample = pattern + Integer.valueOf(lastChar - 48 + cnt2);

                } else {
                    pattern += (char) (lastChar - 1);
                    sample = pattern + "1";
                    lastChar = '1';
                }
            }
            return sample;
        }
        return null;

    }

    private void addNewExcavationLayerWeb(String name, String itemId) {
        Layer layer = new Layer();
        layer.setName(name);
        LayerFeature layerFeature = new LayerFeature();
        layerFeature.setName(name);
        layerFeature.setType(1);
        layerFeature.setChildrenId(layer.getId());
        layerFeature.setChildrenType("App\\Layer");
        layerFeature.setExcavationItemId(itemId);
        layerFeature.setStatus(1);

        mLayerFeatureDao.save(layerFeature);
        mLayerDao.save(layer);
        mAdapter.onLayerFeatureClick(layerFeature);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", layerFeature.getId())
                .putString("name", name)
                .putString("excavation_item_id", itemId)
                .putString("type", String.valueOf(1))
                .putString("status", "1")
                .putString("children_id", layerFeature.getChildrenId())
                .putString("children_type", layerFeature.getChildrenType())
                .build();


        Data inputDataLayer = new Data.Builder()
                .putString("id", layer.getId())
                .putString("name", name)
                .build();

        OneTimeWorkRequest LayerFeatureUploadWork = new OneTimeWorkRequest.Builder(LayerFeatureSaveWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();

        OneTimeWorkRequest layerUploadWork = new OneTimeWorkRequest.Builder(LayerUploadWorker.class)
                .setConstraints(constraints).setInputData(inputDataLayer).build();

        WorkContinuation continuation = WorkManager.getInstance().beginWith(LayerFeatureUploadWork);
        continuation = continuation.then(layerUploadWork);


        Contexture contexture = new Contexture();
        contexture.setLayerId(layerFeature.getId());
        mContextureDao.save(contexture);

        Data inputData2 = new Data.Builder()
                .putString("id", contexture.getId())
                .putString("layer_id", layer.getId())
                .build();
        OneTimeWorkRequest uploadContexture = new OneTimeWorkRequest.Builder(ContextureUploadWorker.class)
                .setConstraints(constraints).setInputData(inputData2).build();
        continuation = continuation.then(uploadContexture);
        continuation.enqueue();

        WorkManager.getInstance().getWorkInfoByIdLiveData(LayerFeatureUploadWork.getId())
                .observe(LayerFeatureListFragment.this, workStatus -> {
                    // Do something with the status
                    if (workStatus != null && workStatus.getState().isFinished()) {

                        Log.d(TAG, "addNewExcavationLayerWeb: finished!");
                    }

                });

    }

    private void CreateNewFeature(String name, String itemId, int structureIndex) {
        Feature feature = new Feature();
        feature.setName(feature.getName());
        feature.setStructureIndex(structureIndex);
        LayerFeature layerFeature = new LayerFeature();
        layerFeature.setName(name);
        layerFeature.setType(2);
        layerFeature.setChildrenId(feature.getId());
        layerFeature.setChildrenType("App\\Feature");
        layerFeature.setExcavationItemId(itemId);
        layerFeature.setStatus(1);
        mLayerFeatureDao.save(layerFeature);
        mFeatureDao.save(feature);
        mAdapter.onLayerFeatureClick(layerFeature);
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        Data inputData = new Data.Builder()
                .putString("id", layerFeature.getId())
                .putString("name", name)
                .putString("excavation_item_id", itemId)
                .putString("type", String.valueOf(2))
                .putString("status", "1")
                .putString("children_id", layerFeature.getChildrenId())
                .putString("children_type", layerFeature.getChildrenType())
                .build();
        Data inputDataLayer = new Data.Builder()
                /*'id','name','structure','content_json','structure_index'*/

                .putString("id", feature.getId())
                .putString("name", name)
                .putString("structure", feature.getStructureName())
                .putString("structure_index", String.valueOf(feature.getStructureIndex()))
                .putString("content_json", feature.getContentJson())
                .build();

        OneTimeWorkRequest LayerFeatureUploadWork = new OneTimeWorkRequest.Builder(LayerFeatureSaveWorker.class)
                .setConstraints(constraints).setInputData(inputData).build();

        OneTimeWorkRequest featureUploadWork = new OneTimeWorkRequest.Builder(FeatureCreateUpdateWorker.class)
                .setConstraints(constraints).setInputData(inputDataLayer).build();

        WorkContinuation continuation = WorkManager.getInstance().beginWith(LayerFeatureUploadWork);
        continuation = continuation.then(featureUploadWork);
        continuation.enqueue();


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

    public interface LayerFeatureClickListener {
        void onLayerFeatureClick(LayerFeature layerFeature);
    }

    private class LayerFeatureAdapter extends RecyclerView.Adapter<LayerFeatureAdapter.ViewHolder> implements LayerFeatureClickListener {
        List<LayerFeature> layerFeatures;

        public LayerFeatureAdapter(List<LayerFeature> layerFeatures) {
            this.layerFeatures = layerFeatures;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemLayerFeatureRecyclerBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_layer_feature_recycler, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(binding);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            viewHolder.binding.setLayerFeature(mLayerFeatureList.get(i));
            viewHolder.binding.setItemClickListener(this);
        }


        @Override
        public int getItemCount() {
            return layerFeatures.size();
        }

        @Override
        public void onLayerFeatureClick(LayerFeature layerFeature) {
            if (layerFeature.getType() == 1) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LayerActivity.class);
                intent.putExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_NAME, layerFeature.getName());
                intent.putExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_ID, layerFeature.getChildrenId());
                intent.putExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID, layerFeature.getExcavationItemId());
                intent.putExtra(MyConst.EXTRA_REGISTRATION_CODING, mRegistrationCoding);
                intent.putExtra(MyConst.EXTRA_CAN_DELETE, layerFeature.getId() == mAdapter.layerFeatures.get(mAdapter.getItemCount() - 1).getId());
                startActivity(intent);
            } else if (layerFeature.getType() == 2) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FeatureActivity.class);
                intent.putExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_NAME, layerFeature.getName());
                intent.putExtra(MyConst.EXTRA_EXCAVATION_LAYER_FEATURE_ID, layerFeature.getChildrenId());
                intent.putExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID, layerFeature.getExcavationItemId());
                intent.putExtra(MyConst.EXTRA_REGISTRATION_CODING, mRegistrationCoding);
                intent.putExtra(MyConst.EXTRA_CAN_DELETE, layerFeature.getId() == mAdapter.layerFeatures.get(mAdapter.getItemCount() - 1).getId());
                startActivity(intent);
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ItemLayerFeatureRecyclerBinding binding;

            public ViewHolder(ItemLayerFeatureRecyclerBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
