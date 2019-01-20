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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentSurveyHillDestructionDetailBinding;
import com.example.arsalan.kavosh.databinding.ItemDestructionBinding;
import com.example.arsalan.kavosh.dialog.AddDestructionDialog;
import com.example.arsalan.kavosh.model.Destruction;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.SurHillDestructionDetail;
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
 * {@link SurveyHillDestructionDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyHillDestructionDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyHillDestructionDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "SurveyHillDestructionDe";

    private static final int REQ_ADD_DESTRUCTION = 3;

    private OnFragmentInteractionListener mListener;
    private SurHillDestructionDetail mDetail;
    private List<Destruction> mDestructionList;
    private int mCurrentPosition;
    private LLDesctructionAdapter mAdapterDestruction;

    public SurveyHillDestructionDetailFragment() {
        // Required empty public constructor
    }

    public static SurveyHillDestructionDetailFragment newInstance(String param1) {
        SurveyHillDestructionDetailFragment fragment = new SurveyHillDestructionDetailFragment();
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

                return true;
            case R.id.menu_delete:
                // Trigger the deletion here
                //   mListener.onSurveyDestructionDetailUpdate(gson.toJson(mDetail));

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
                mDetail = gson.fromJson(jsonStr, SurHillDestructionDetail.class);
            } catch (Exception e) {

            }
            if (mDetail == null) mDetail = new SurHillDestructionDetail();
            mDestructionList = mDetail.getDestructionList();
            if (mDestructionList == null) mDestructionList = new ArrayList<>();
            Log.d(TAG, "onCreate: mDestructionList:" + mDestructionList.size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSurveyHillDestructionDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_hill_destruction_detail, container, false);
        binding.setSurHillDestructionDetail(mDetail);
        mDetail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Gson gson = new Gson();
                mListener.onSurveyDestructionDetailUpdate(gson.toJson(mDetail));
                Log.d(TAG, "onPropertyChanged: ");
            }
        });


        mAdapterDestruction = new LLDesctructionAdapter(mDestructionList, binding.llDestruction);
        mAdapterDestruction.notifyDataSetChanged();

        binding.btnAddDestruction.setOnClickListener(b -> {
            AddDestructionDialog destructionDialog = new AddDestructionDialog();
            destructionDialog.setTargetFragment(SurveyHillDestructionDetailFragment.this, REQ_ADD_DESTRUCTION);
            destructionDialog.show(getFragmentManager(), "");
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
            case REQ_ADD_DESTRUCTION:
                if (resultCode == Activity.RESULT_OK) {
                    mDestructionList.add(data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mDetail.setDestructionList(mDestructionList);
                    mAdapterDestruction.notifyDataSetChanged();
                    Gson gson = new Gson();
                    mListener.onSurveyDestructionDetailUpdate(gson.toJson(mDetail));
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
        void onSurveyDestructionDetailUpdate(String detail);
    }

    private class LLDesctructionAdapter {
        LinearLayout linearLayout;
        List<Destruction> arrayList;

        public LLDesctructionAdapter(List<Destruction> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            View header = LayoutInflater.from(getContext()).inflate(R.layout.header_hill_destruction, null);
            linearLayout.addView(header);
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Destruction getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemDestructionBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_destruction, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemDestructionBinding) convertView.getTag();
            }
            binding.setDestruction(getItem(position));
            View v = binding.getRoot();

            convertView.setTag(position);
            registerForContextMenu(convertView);

            return v;

        }
    }
}
