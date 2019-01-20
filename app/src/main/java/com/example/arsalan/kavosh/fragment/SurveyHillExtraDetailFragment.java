package com.example.arsalan.kavosh.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentSurveyHillExtraDetailBinding;
import com.example.arsalan.kavosh.databinding.ItemHillSideBinding;
import com.example.arsalan.kavosh.dialog.AddHillSideDialog;
import com.example.arsalan.kavosh.model.Hillside;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.SurHillExtraDetail;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyHillExtraDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyHillExtraDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyHillExtraDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "SurveyHillExtraDetailFr";
    private static final int REQ_ADD_HILL_SIDE = 1;
    private static final int REQ_EDIT_HILL_SIDE = 2;

    private OnFragmentInteractionListener mListener;
    private SurHillExtraDetail mDetail;
    private List<Hillside> mHillsideList;
    private LLHillSideAdapter mAdapterHillSide;
    private int mCurrentPosition;

    public SurveyHillExtraDetailFragment() {
        // Required empty public constructor
    }

    public static SurveyHillExtraDetailFragment newInstance(String param1) {
        SurveyHillExtraDetailFragment fragment = new SurveyHillExtraDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        mCurrentPosition = (int) v.getTag();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_delete_edit, menu);
        Log.d(TAG, "onCreateContextMenu:");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Toast.makeText(getContext(), "Editing!:" + mCurrentPosition, Toast.LENGTH_SHORT).show();
                AddHillSideDialog dialog = AddHillSideDialog.newInstance(mCurrentPosition, mHillsideList.get(mCurrentPosition));
                dialog.setTargetFragment(SurveyHillExtraDetailFragment.this, REQ_EDIT_HILL_SIDE);
                dialog.show(getFragmentManager(), "");

                return true;
            case R.id.menu_delete:
                // Trigger the deletion here
                mHillsideList.remove(mCurrentPosition);
                mAdapterHillSide.notifyDataSetChanged();
                Gson gson = new Gson();
                mDetail.setHillsideList(mHillsideList);
                mListener.onSurveyExtraDetail(gson.toJson(mDetail));

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonStr = getArguments().getString(ARG_PARAM1);
            try {
                Gson gson = new Gson();
                mDetail = gson.fromJson(jsonStr, SurHillExtraDetail.class);
            } catch (Exception e) {

            }
            if (mDetail == null) mDetail = new SurHillExtraDetail();
            mHillsideList = mDetail.getHillsideList();
            if (mHillsideList == null) mHillsideList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSurveyHillExtraDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_hill_extra_detail, container, false);
        Log.d(TAG, "onCreateView: mDetail:" + mDetail.getEwLenght());
        binding.setSurHillExtraDetail(mDetail);
        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_hill_side_shape));
        binding.txtShape.setAdapter(textAdapter1);
        mDetail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Gson gson = new Gson();
                mDetail.setHillsideList(mHillsideList);
                mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                Log.d(TAG, "onPropertyChanged: ");
            }
        });

        mAdapterHillSide = new LLHillSideAdapter(mHillsideList, binding.llHillSide);
        mAdapterHillSide.notifyDataSetChanged();


        binding.btnAddHillside.setOnClickListener(b -> {
            AddHillSideDialog dialog = new AddHillSideDialog();
            dialog.setTargetFragment(SurveyHillExtraDetailFragment.this, REQ_ADD_HILL_SIDE);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQ_ADD_HILL_SIDE:
                if (resultCode == Activity.RESULT_OK) {
                    Hillside hillside = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mHillsideList.add(hillside);
                    mAdapterHillSide.notifyDataSetChanged();

                    Gson gson = new Gson();
                    mDetail.setHillsideList(mHillsideList);
                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                }
                break;
            case REQ_EDIT_HILL_SIDE:
                if (resultCode == Activity.RESULT_OK) {
                    int index = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    mHillsideList.remove(index);
                    mHillsideList.add(index, data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mAdapterHillSide.notifyDataSetChanged();

                    Gson gson = new Gson();
                    mDetail.setHillsideList(mHillsideList);

                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                }
                break;

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
        void onSurveyExtraDetail(String detail);
    }

    private class LLHillSideAdapter {
        LinearLayout linearLayout;
        List<Hillside> arrayList;

        public LLHillSideAdapter(List<Hillside> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.header_hill_side, null);
            linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Hillside getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemHillSideBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hill_side, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemHillSideBinding) convertView.getTag();
            }
            binding.setHillside(getItem(position));
            View v = binding.getRoot();

            convertView.setTag(position);
            registerForContextMenu(convertView);

            return v;

        }
    }
}
