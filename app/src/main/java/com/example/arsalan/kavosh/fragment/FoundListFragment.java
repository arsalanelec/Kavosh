package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.FoundActivity;
import com.example.arsalan.kavosh.databinding.FragmentFoundListBinding;
import com.example.arsalan.kavosh.databinding.ItemFoundBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddNewFoundDialog;
import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.room.FoundDao;
import com.example.arsalan.kavosh.viewModel.FoundViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.FoundUploadWorker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoundListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoundListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundListFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_FOUND = 100;
    private static final String ARG_PARAM3 = "ARG_PARAM3";
    private static final String ARG_PARAM4 = "ARG_PARAM4";
    @Inject
    MyViewModelFactory factory;
    @Inject
    FoundDao mFoundDao;
    @Inject
    Token mToken;
    // TODO: Rename and change types of parameters
    private String mLayerName;
    private String mLayerId;
    private List<Found> mFoundList = new ArrayList<>();
    private List<Found> mFoundIndex = new ArrayList<>();
    private List<Found> mFoundSample = new ArrayList<>();
    private RecyclerAdapter mAdapterFound;
    private AdapterExpandable mAdapterExpandable;
    private OnFragmentInteractionListener mListener;
    private String mExcavationItemId;

    public FoundListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1            Parameter 1.
     * @param param2            Parameter 2.
     * @param mExcavationItemId
     * @return A new instance of fragment FoundListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoundListFragment newInstance(String param1, String param2, String mExcavationItemId) {
        FoundListFragment fragment = new FoundListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, mExcavationItemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLayerName = getArguments().getString(ARG_PARAM1);
            mLayerId = getArguments().getString(ARG_PARAM2);
            mExcavationItemId = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFoundListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_found_list, container, false);
        mAdapterFound = new RecyclerAdapter(mFoundList);

        /*binding.rvFound.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvFound.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.rvFound.setAdapter(mAdapterFound);
*/

        mAdapterExpandable = new AdapterExpandable(mFoundList, mFoundSample, mFoundIndex);
        binding.expFound.setAdapter(mAdapterExpandable);

        return binding.getRoot();
        // Inflate the layout for this fragment
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FoundViewModel viewModelFound = ViewModelProviders.of(FoundListFragment.this, factory).get(FoundViewModel.class);
        viewModelFound.initial(mExcavationItemId,mLayerId, 1);
        viewModelFound.getFounds().observe(FoundListFragment.this, founds -> {
            mFoundList.removeAll(mFoundList);
            mFoundList.addAll(founds);
            mAdapterExpandable.notifyDataSetChanged();
            // mAdapterFound.notifyDataSetChanged();
        });

        FoundViewModel viewModelFoundSample = ViewModelProviders.of(FoundListFragment.this, factory).get(FoundViewModel.class);
        viewModelFoundSample.initial(mExcavationItemId,mLayerId, 2);
        viewModelFoundSample.getFounds().observe(FoundListFragment.this, founds -> {
            mFoundSample.removeAll(mFoundSample);
            mFoundSample.addAll(founds);
            mAdapterExpandable.notifyDataSetChanged();
            // mAdapterFound.notifyDataSetChanged();
        });

        FoundViewModel viewModelFoundIndex = ViewModelProviders.of(FoundListFragment.this, factory).get(FoundViewModel.class);
        viewModelFoundIndex.initial(mExcavationItemId,mLayerId, 3);
        viewModelFoundIndex.getFounds().observe(FoundListFragment.this, founds -> {
            mFoundIndex.removeAll(mFoundIndex);
            mFoundIndex.addAll(founds);
            mAdapterExpandable.notifyDataSetChanged();
            // mAdapterFound.notifyDataSetChanged();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ADD_FOUND)
            if (resultCode == RESULT_OK) {
                Found found = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                mFoundDao.save(found);
                Log.d(getClass().getSimpleName(), "onActinewInstancevityResult: mExcavationItemId:" + mExcavationItemId);
                createUpdateFound(found);
            }
    }

    private void createUpdateFound(Found found) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        mFoundDao.save(found);
        Data inputData = new Data.Builder()
                .putString("id", found.getId())
                .putString("excavation_item_id", found.getExcavationItemId())
                .putString("content_json", found.getContentJson())
                .putString("layer_feature_id", found.getLayerFeatureId())
                .putString("type", String.valueOf(found.getType()))
                .build();

        OneTimeWorkRequest uploadFound = new OneTimeWorkRequest.Builder(FoundUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance().enqueue(uploadFound);
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
        String getNewRegistrationCode();
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private List<Found> foundList;

        public RecyclerAdapter(List<Found> foundList) {
            this.foundList = foundList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemFoundBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_found, viewGroup, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
            vh.binding.setFound(foundList.get(i));
        }

        @Override
        public int getItemCount() {
            return foundList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ItemFoundBinding binding;

            ViewHolder(ItemFoundBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private class AdapterExpandable extends BaseExpandableListAdapter {
        private final int ITEM_TYPE_NORMAL = 0;
        private final int ITEM_TYPE_ADD_BUTTON = 1;

        private String[] groupTitles = new String[]{"یافته", "نمونه آزمایشگاهی", "یافته شاخص"};
        private List<Found> foundList;
        private List<Found> foundTestList;
        private List<Found> foundIndexList;

        public AdapterExpandable(List<Found> foundList, List<Found> foundTestList, List<Found> foundIndexList) {
            this.foundList = foundList;
            this.foundTestList = foundTestList;
            this.foundIndexList = foundIndexList;
        }

        @Override
        public int getGroupCount() {
            return groupTitles.length;
        }

        @Override
        public int getChildrenCount(int i) {
            switch (i) {
                case 0:
                    return foundList.size() + 2;
                case 1:
                    return foundTestList.size() + 2;
                case 2:
                    return foundIndexList.size() + 2;
            }
            return 2;
        }

        @Override
        public String getGroup(int i) {
            return groupTitles[i];
        }

        @Override
        public Found getChild(int i, int i1) {
            if (i1 == 0) return null;

            switch (i) {
                case 0:
                    return foundList.size() >= i1 ? foundList.get(i1 - 1) : null;
                case 1:
                    return foundTestList.size() >= i1 ? foundTestList.get(i1 - 1) : null;
                case 2:
                    return foundIndexList.size() >= i1 ? foundIndexList.get(i1 - 1) : null;
            }
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if (b)
                view = getLayoutInflater().inflate(R.layout.item_group_collapsed, viewGroup, false);
            else
                view = getLayoutInflater().inflate(R.layout.item_group_expanded, viewGroup, false);
            TextView titleTV = view.findViewById(R.id.text);
            titleTV.setText(getGroup(i));
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            Log.d(getClass().getSimpleName(), "getChildView: " + i1);
            if (i1 == 0) {
                View header = getLayoutInflater().inflate(R.layout.item_header_expanded, null);
                return header;
            }
            if (getChildType(i, i1) == ITEM_TYPE_ADD_BUTTON) {
                View addLayout = getLayoutInflater().inflate(R.layout.item_add_found_btn, null);
                FloatingActionButton addButton = addLayout.findViewById(R.id.button);
                addButton.setOnClickListener(view1 -> {
                    AddNewFoundDialog dialog = AddNewFoundDialog.newInstance(mExcavationItemId,mLayerId, mLayerName, mListener.getNewRegistrationCode(), i + 1);
                    dialog.setTargetFragment(FoundListFragment.this, REQ_ADD_FOUND);
                    dialog.show(getFragmentManager(), "");
                    /*switch (i) {
                        case 0:
                            dialog = AddFoundDialog.newInstance(mLayerName, 1);

                            break;
                        case 1:
                            dialog = AddFoundDialog.newInstance(mLayerName, 2);

                            break;
                        case 2:
                            dialog = AddFoundDialog.newInstance(mLayerName, 3);

                            break;
                        default:
                            dialog = AddFoundDialog.newInstance(mLayerName, 1);
                    }
                    dialog.setTargetFragment(FoundListFragment.this, REQ_ADD_FOUND);
                    dialog.show(getFragmentManager(), "");*/
                });
                return addLayout;
            } else {

            }

            ItemFoundBinding binding;
            if (view == null || !(view.getTag() instanceof ItemFoundBinding)) {
                binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_found, viewGroup, false);
                view = binding.getRoot();
            } else {
                binding = (ItemFoundBinding) view.getTag();
            }
            binding.setFound(getChild(i, i1));
            view.setTag(binding);
            view.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FoundActivity.class);
                intent.putExtra(MyConst.EXTRA_MODEL, getChild(i, i1));
                startActivity(intent);
            });
            return view;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        @Override
        public int getChildTypeCount() {
            return 2;
        }

        @Override
        public int getChildType(int groupPosition, int childPosition) {
            if (getChildrenCount(groupPosition) == childPosition + 1) {
                return ITEM_TYPE_ADD_BUTTON;
            }
            return ITEM_TYPE_NORMAL;
        }
    }

}
