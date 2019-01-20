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
import com.example.arsalan.kavosh.activities.SurveyActivity;
import com.example.arsalan.kavosh.activities.SurveyItemListActivity;
import com.example.arsalan.kavosh.databinding.FragmentSurveyItemListBinding;
import com.example.arsalan.kavosh.databinding.ItemSurveyBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.NewSurveyDialog;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.room.SurveyDao;
import com.example.arsalan.kavosh.room.SurveyProjectDao;
import com.example.arsalan.kavosh.viewModel.SurveyListViewModel;
import com.example.arsalan.kavosh.viewModel.SurveyProjectViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyItemListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyItemListFragment extends androidx.fragment.app.Fragment implements Injectable {
    private static final String TAG = "SurveyItemListFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Inject
    SurveyProjectDao mSurveyProjectDao;
    @Inject
    SurveyDao mSurveyDao;
    @Inject
    MyViewModelFactory mFactory;
    SurveyProject mSurveyProject;
    private String mSurveyProjectId = "";
    private List<Survey> mSurveyList = new ArrayList<>();
    private SurveyRecyclerAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SurveyItemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param projectId Parameter 1.
     * @param param2    Parameter 2.
     * @return A new instance of fragment SurveyItemListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyItemListFragment newInstance(String projectId, String param2) {
        SurveyItemListFragment fragment = new SurveyItemListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, projectId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSurveyProjectId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSurveyItemListBinding b = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_item_list, container, false);
        b.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SurveyRecyclerAdapter(mSurveyList);
        b.rv.setAdapter(mAdapter);
        SurveyProjectViewModel surveyProjectViewModel = ViewModelProviders.of(this, mFactory).get(SurveyProjectViewModel.class);
        surveyProjectViewModel.initial(mSurveyProjectId);
        surveyProjectViewModel.getSurveyProject().observe(this, surveyProject -> {
            mSurveyProject = surveyProject;
        });
        SurveyListViewModel surveyListVm = ViewModelProviders.of(this, mFactory).get(SurveyListViewModel.class);
        surveyListVm.initial(mSurveyProjectId);
        surveyListVm.getSurveyList().observe(this, surveys -> {
            Log.d(TAG, "getSurveyList().observe: " + surveys.size());
            b.waitingView.setVisibility(View.GONE);
            if (surveys != null) {
                mSurveyList.removeAll(mSurveyList);
                mSurveyList.addAll(surveys);
                mAdapter.notifyDataSetChanged();
            }
        });
        b.btnAdd.setOnClickListener(btn -> {
            NewSurveyDialog dialog = NewSurveyDialog.newInstance(mSurveyProjectId, getNewRegistrationCode(), mSurveyProject.getCodeName());
            dialog.show(getFragmentManager(), "");
        });
        return b.getRoot();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getNewRegistrationCode() {
        if (mSurveyProject == null) return "";
        return increaseCode(mSurveyProject.getCodeStartNum(), mSurveyDao.getNumberOfRows(mSurveyProject.getId()));
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

    private class SurveyRecyclerAdapter extends RecyclerView.Adapter<SurveyRecyclerAdapter.ViewHolder> implements SurveyItemListActivity.SurveyOnClickListener {
        List<Survey> surveyList;

        public SurveyRecyclerAdapter(List<Survey> surveyList) {
            this.surveyList = surveyList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemSurveyBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_survey, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.setSurvey(surveyList.get(position));
            holder.binding.setOnSurveyClick(SurveyRecyclerAdapter.this);
        }

        @Override
        public int getItemCount() {
            return surveyList.size();
        }

        @Override
        public void OnClickListener(String surveyId) {
            Intent intent = new Intent();
            intent.putExtra(MyConst.EXTRA_ID, surveyId);
            intent.putExtra(MyConst.EXTRA_PROJECT_NAME, mSurveyProject.getName());
            intent.setClass(getActivity(), SurveyActivity.class);
            startActivity(intent);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ItemSurveyBinding binding;

            public ViewHolder(ItemSurveyBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
