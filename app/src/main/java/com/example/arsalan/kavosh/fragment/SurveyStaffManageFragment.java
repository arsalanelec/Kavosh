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
import com.example.arsalan.kavosh.databinding.FragmentSurveyStaffManageBinding;
import com.example.arsalan.kavosh.databinding.ItemSupervisorBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddSupervisorDialog;
import com.example.arsalan.kavosh.dialog.ExitSupervisorDialog;
import com.example.arsalan.kavosh.dialog.SupervisorDetailDialog;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.room.SupervisorDao;
import com.example.arsalan.kavosh.viewModel.SupervisorListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.example.arsalan.kavosh.wokrmanager.SupervisorUploadWorker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
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
 * {@link SurveyStaffManageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyStaffManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyStaffManageFragment extends androidx.fragment.app.Fragment implements Injectable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_ADD_SUPERVISOR = 1;
    private static final int REQ_EXIT_SUPERVISOR = 2;

    private static final String TAG = "SurveyStaffManageFragme";
    @Inject
    SupervisorDao supervisorDao;
    @Inject
    MyViewModelFactory mFactory;
    // TODO: Rename and change types of parameters
    private String mProjectId;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SurveyStaffManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param projectId Parameter 1.
     * @param param2    Parameter 2.
     * @return A new instance of fragment SurveyStaffManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyStaffManageFragment newInstance(String projectId, String param2) {
        SurveyStaffManageFragment fragment = new SurveyStaffManageFragment();
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
            mProjectId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSurveyStaffManageBinding b = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_staff_manage, container, false);
        List<Supervisor> supervisorList = new ArrayList<>();
        SupervisorRecyclerAdapter adapter = new SupervisorRecyclerAdapter(supervisorList);
        SupervisorListViewModel viewModel = ViewModelProviders.of(this, mFactory).get(SupervisorListViewModel.class);
        b.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        b.rv.setAdapter(adapter);
        viewModel.initial(mProjectId);
        viewModel.getSupervisors().observe(this, supervisors -> {
            if (supervisorList != null) {
                supervisorList.removeAll(supervisorList);
                supervisorList.addAll(supervisors);
                adapter.notifyDataSetChanged();
                b.waitingView.setVisibility(View.GONE);
            }
        });
        b.btnAdd.setOnClickListener(btn -> {
            AddSupervisorDialog dialog = new AddSupervisorDialog();
            dialog.setTargetFragment(SurveyStaffManageFragment.this, REQ_ADD_SUPERVISOR);
            dialog.show(getFragmentManager(), "");
        });
        return b.getRoot();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_ADD_SUPERVISOR:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: project_id:" + mProjectId);
                    String supervisorId = data.getStringExtra(MyConst.EXTRA_ID);
                    Supervisor supervisor = new Supervisor(supervisorId, mProjectId);
                    supervisor.setName(data.getStringExtra(MyConst.EXTRA_NAME));
                    supervisor.setDateAdded(new Date(data.getLongExtra(MyConst.EXTRA_DATE, 0)));
                    supervisor.setStatus(1);
                    supervisorDao.save(supervisor);
                    storeSupervisorForProject(supervisor);
                }
                break;
            case REQ_EXIT_SUPERVISOR:
                if (resultCode == RESULT_OK) {
                    Supervisor supervisor = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    supervisorDao.save(supervisor);
                    storeSupervisorForProject(supervisor);

                }
                break;
        }
    }

    private void storeSupervisorForProject(Supervisor supervisor) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                .CONNECTED).build();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", new Locale("en", "gb"));

        Data inputData = new Data.Builder()
                .putString("user_id", supervisor.getUserId())
                .putString("project_id", supervisor.getProjectId())
                .putString("date_added", fmt.format(supervisor.getDateAdded()))
                .putString("date_removed", fmt.format(supervisor.getDateRemoved()))
                .putString("status", String.valueOf(supervisor.getStatus()))
                .build();

        OneTimeWorkRequest uploadWorker = new OneTimeWorkRequest.Builder(SupervisorUploadWorker.class)
                .setConstraints(constraints)
                .setInputData(inputData).build();
        WorkManager.getInstance().enqueue(uploadWorker).getState().observe(SurveyStaffManageFragment.this, status -> {
            Log.d("SupervisorUploadWorker", "storeSupervisorForProject: status:" + status);
        });

    }


    public interface OnSupervisorClickListener {
        void onClick(Supervisor supervisor, View view);
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

    private class SupervisorRecyclerAdapter extends RecyclerView.Adapter<SupervisorRecyclerAdapter.ViewHolder> implements OnSupervisorClickListener {
        List<Supervisor> userList;

        public SupervisorRecyclerAdapter(List<Supervisor> userList) {
            this.userList = userList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemSupervisorBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_supervisor, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.setSupervisor(userList.get(position));
            holder.binding.setThisView(holder.binding.textViewOptions);
            holder.binding.setSupervisorClickListener(SupervisorRecyclerAdapter.this);

        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        @Override
        public void onClick(Supervisor supervisor, View view) {
            //  SupervisorDetailDialog detailDialog = SupervisorDetailDialog.newInstance(supervisor);
            //  detailDialog.show(getFragmentManager(), "");
            //creating a popup menu
            PopupMenu popup = new PopupMenu(getContext(), view);
            //inflating menu from xml resource
            if (supervisor.getStatus() == 1) {
                popup.inflate(R.menu.menu_view_edit);
            } else {
                popup.inflate(R.menu.menu_view);
            }
            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_view:
                        SupervisorDetailDialog detailDialog = SupervisorDetailDialog.newInstance(supervisor);
                        detailDialog.show(getFragmentManager(), "");
                        return true;
                    case R.id.menu_remove:
                        ExitSupervisorDialog dialog = ExitSupervisorDialog.newInstance(supervisor);
                        dialog.setTargetFragment(SurveyStaffManageFragment.this, REQ_EXIT_SUPERVISOR);
                        dialog.show(getFragmentManager(), "");
                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popup.show();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ItemSupervisorBinding binding;

            public ViewHolder(ItemSupervisorBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
