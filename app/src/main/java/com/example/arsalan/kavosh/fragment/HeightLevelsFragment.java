package com.example.arsalan.kavosh.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentHeightLevelsBinding;
import com.example.arsalan.kavosh.databinding.ItemHeightLevelBinding;
import com.example.arsalan.kavosh.dialog.AddHeightLevelDialog;
import com.example.arsalan.kavosh.model.HeightLevel;
import com.example.arsalan.kavosh.model.MyConst;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeightLevelsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeightLevelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeightLevelsFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_HEIGHTLEVEL_H = 100;
    private static final int REQ_ADD_HEIGHTLEVEL_L = 101;
    private static final int REQ_EDIT_HEIGHTLEVEL_H = 102;
    private static final int REQ_EDIT_HEIGHTLEVEL_L = 103;


    // TODO: Rename and change types of parameters
    private String mHLJsonH;
    private String mHLJsonL;

    private List<HeightLevel> mHeightLevelListH = new ArrayList<>();
    private List<HeightLevel> mHeightLevelListL = new ArrayList<>();

    private RecyclerAdapter mAdapterH;
    private RecyclerAdapter mAdapterL;


    private OnFragmentInteractionListener mListener;

    public HeightLevelsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param HL_H Parameter 1.
     * @param HL_L Parameter 2.
     * @return A new instance of fragment HeightLevelsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeightLevelsFragment newInstance(String HL_H, String HL_L) {
        HeightLevelsFragment fragment = new HeightLevelsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, HL_H);
        args.putString(ARG_PARAM2, HL_L);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHLJsonH = getArguments().getString(ARG_PARAM1);
            mHLJsonL = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHeightLevelsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_height_levels, container, false);

        mAdapterH = new RecyclerAdapter(mHeightLevelListH, REQ_EDIT_HEIGHTLEVEL_H);
        binding.rvHeightLevelH.setAdapter(mAdapterH);
        binding.rvHeightLevelH.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHeightLevelH.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mAdapterL = new RecyclerAdapter(mHeightLevelListL, REQ_EDIT_HEIGHTLEVEL_L);
        binding.rvHeightLevelL.setAdapter(mAdapterL);
        binding.rvHeightLevelL.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHeightLevelL.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        Gson gson = new Gson();
        try {
            List<HeightLevel> list = gson.fromJson(mHLJsonH, new TypeToken<List<HeightLevel>>() {
            }.getType());
            mHeightLevelListH.addAll(list);
            mAdapterH.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<HeightLevel> list = gson.fromJson(mHLJsonL, new TypeToken<List<HeightLevel>>() {
            }.getType());
            mHeightLevelListL.addAll(list);
            mAdapterL.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnAddHeightLevelH.setOnClickListener(view -> {
            AddHeightLevelDialog dialog = new AddHeightLevelDialog();
            dialog.setTargetFragment(HeightLevelsFragment.this, REQ_ADD_HEIGHTLEVEL_H);
            dialog.show(getFragmentManager(), "");

        });
        binding.btnAddHeightLevelL.setOnClickListener(view -> {
            AddHeightLevelDialog dialog = new AddHeightLevelDialog();
            dialog.setTargetFragment(HeightLevelsFragment.this, REQ_ADD_HEIGHTLEVEL_L);
            dialog.show(getFragmentManager(), "");
        });
        return binding.getRoot();
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
            case REQ_ADD_HEIGHTLEVEL_H:
                if (resultCode == RESULT_OK) {
                    mHeightLevelListH.add(data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mAdapterH.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String string = gson.toJson(mHeightLevelListH);
                    mListener.updateHeightLevelH(string);
                }
                break;
            case REQ_ADD_HEIGHTLEVEL_L:
                if (resultCode == RESULT_OK) {
                    mHeightLevelListL.add(data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mAdapterL.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String string = gson.toJson(mHeightLevelListL);
                    mListener.updateHeightLevelL(string);
                }
                break;

            case REQ_EDIT_HEIGHTLEVEL_H:
                if (resultCode == RESULT_OK) {
                    int index = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    mAdapterH.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String string = gson.toJson(mHeightLevelListH);
                    mListener.updateHeightLevelH(string);
                }
                break;
            case REQ_EDIT_HEIGHTLEVEL_L:
                if (resultCode == RESULT_OK) {
                    int index = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    mAdapterL.notifyDataSetChanged();
                    Gson gson = new Gson();
                    String string = gson.toJson(mHeightLevelListL);
                    mListener.updateHeightLevelL(string);
                }
                break;
        }
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
        void updateHeightLevelH(String jsonString);

        void updateHeightLevelL(String jsonString);

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        List<HeightLevel> heightLevelList;
        int listType;

        public RecyclerAdapter(List<HeightLevel> heightLevelList, int listType) {
            this.heightLevelList = heightLevelList;
            this.listType = listType;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_height_level, viewGroup, false));

        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.binding.setHeightLevel(heightLevelList.get(i));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddHeightLevelDialog dialog = AddHeightLevelDialog.newInstance(heightLevelList.get(i), i);
                    if (listType == REQ_EDIT_HEIGHTLEVEL_H)
                        dialog.setTargetFragment(HeightLevelsFragment.this, REQ_EDIT_HEIGHTLEVEL_H);
                    else
                        dialog.setTargetFragment(HeightLevelsFragment.this, REQ_EDIT_HEIGHTLEVEL_L);

                    dialog.show(getFragmentManager(), "");
                }
            });
        }

        @Override
        public int getItemCount() {
            return heightLevelList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ItemHeightLevelBinding binding;

            public ViewHolder(ItemHeightLevelBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
