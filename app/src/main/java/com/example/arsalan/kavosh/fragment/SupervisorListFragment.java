package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentSupervisorListBinding;
import com.example.arsalan.kavosh.databinding.ItemSupervisorHistoryBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;
import com.example.arsalan.kavosh.viewModel.ExcavationItemSupervisorViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SupervisorListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SupervisorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupervisorListFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    ExcavationItemSupervisorViewModel viewModel;
    @Inject
    MyViewModelFactory factory;
    private OnFragmentInteractionListener mListener;
    private FragmentSupervisorListBinding binding;
    private List<ExcavationItemSupervisorHistory> mSupervisorList = new ArrayList<>();
    private SupervisorRecyclerAdapter mAdapter;
    private String mItemId;

    public SupervisorListFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId Parameter 1.
     * @return A new instance of fragment LayerFeatureListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupervisorListFragment newInstance(String itemId) {
        SupervisorListFragment fragment = new SupervisorListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supervisor_list, container, false);

        View view = binding.getRoot();

        mAdapter = new SupervisorRecyclerAdapter(mSupervisorList);
        binding.rvSupervisor.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvSupervisor.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.rvSupervisor.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(SupervisorListFragment.this, factory).get(ExcavationItemSupervisorViewModel.class);
        viewModel.initial(mItemId);
        viewModel.getExcavationItems().observe(SupervisorListFragment.this, supervisors -> {
            Log.d("onActivityCreated", "observe: ");
            mSupervisorList.removeAll(mSupervisorList);
            mSupervisorList.addAll(supervisors);
            mAdapter.notifyDataSetChanged();
            //  waitingV.setVisibility(View.GONE);
            //    Log.d(TAG, "onActivityCreated: projectList:"+projects.toString());
        });
        Log.d(getClass().getSimpleName(), "onActivityCreated: ");
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


    public interface SupervisorEventListener {
        void onSupervisorClicker(ExcavationItemSupervisorHistory supervisorHistory);

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

    public class SupervisorRecyclerAdapter extends RecyclerView.Adapter<SupervisorRecyclerAdapter.ViewHolder> implements SupervisorEventListener {
        List<ExcavationItemSupervisorHistory> supervisorHistoryList;

        public SupervisorRecyclerAdapter(List<ExcavationItemSupervisorHistory> supervisorHistoryList) {
            this.supervisorHistoryList = supervisorHistoryList;
        }

        @NonNull
        @Override

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemSupervisorHistoryBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_supervisor_history, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(binding);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            ExcavationItemSupervisorHistory supervisor = supervisorHistoryList.get(i);
            viewHolder.binding.setSupervisor(supervisor);
            viewHolder.binding.setItemClickListener(this);
        }

        @Override
        public int getItemCount() {
            return supervisorHistoryList.size();
        }

        @Override
        public void onSupervisorClicker(ExcavationItemSupervisorHistory supervisorHistory) {

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ItemSupervisorHistoryBinding binding;

            public ViewHolder(ItemSupervisorHistoryBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
