package com.example.arsalan.kavosh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.activities.SurFoundDetailActivity;
import com.example.arsalan.kavosh.databinding.FragmentSurfoundListBinding;
import com.example.arsalan.kavosh.databinding.ItemSurfoundBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddSurFoundDialog;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.viewModel.SurFoundViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SurfoundListFragment extends Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Inject
    MyViewModelFactory mFactory;
    private String mSurveyId;
    private OnFragmentInteractionListener mListener;
    private List<SurFound> mFoundList;
    private FoundRecyclerAdapter mAdapter;

    public SurfoundListFragment() {
        // Required empty public constructor
    }


    public static SurfoundListFragment newInstance(String surveyId) {
        SurfoundListFragment fragment = new SurfoundListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, surveyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSurveyId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSurfoundListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_surfound_list, container, false);

        mFoundList = new ArrayList<>();
        mAdapter = new FoundRecyclerAdapter(mFoundList);
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvList.setAdapter(mAdapter);
        binding.rvList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.btnAdd.setOnClickListener(b ->
        {
            AddSurFoundDialog dialog = new AddSurFoundDialog();
            dialog.show(getFragmentManager(), "");
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SurFoundViewModel viewModel = ViewModelProviders.of(SurfoundListFragment.this, mFactory).get(SurFoundViewModel.class);
        viewModel.initial(mSurveyId);
        viewModel.getSurFounds().observe(SurfoundListFragment.this, surFounds -> {
            mFoundList.removeAll(mFoundList);
            mFoundList.addAll(surFounds);
            mAdapter.notifyDataSetChanged();
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
        void onSaveSurFound(SurFound surFound);

        void onRemoveSurFound(SurFound surFound);
    }

    public interface OnSurFoundEventListener {
        void onSurFoundDetailClicked(SurFound surFound);

        void onRemoveSourFoundClicked(SurFound surFound);
    }

    private class FoundRecyclerAdapter extends RecyclerView.Adapter<FoundRecyclerAdapter.ViewHolder> implements OnSurFoundEventListener {
        List<SurFound> foundList;

        public FoundRecyclerAdapter(List<SurFound> foundList) {
            this.foundList = foundList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            ItemSurfoundBinding binding =
                    DataBindingUtil.inflate(
                            LayoutInflater.from(viewGroup.getContext()),
                            R.layout.item_surfound, viewGroup, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.binding.setSurFound(foundList.get(i));
            viewHolder.binding.setListener(FoundRecyclerAdapter.this);
        }

        @Override
        public int getItemCount() {
            return foundList.size();
        }

        @Override
        public void onSurFoundDetailClicked(SurFound surFound) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), SurFoundDetailActivity.class);
            intent.putExtra(MyConst.EXTRA_ID, surFound.getId());
            startActivity(intent);
        }

        @Override
        public void onRemoveSourFoundClicked(SurFound surFound) {
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
                        mListener.onRemoveSurFound(surFound);
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create().show();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemSurfoundBinding binding;

            public ViewHolder(ItemSurfoundBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
