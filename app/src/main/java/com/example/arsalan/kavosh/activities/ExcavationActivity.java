package com.example.arsalan.kavosh.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.NewExcavationItem1Dialog;
import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.room.UserDao;
import com.example.arsalan.kavosh.viewModel.ExcavationItemListViewModel;
import com.example.arsalan.kavosh.viewModel.factory.MyViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ExcavationActivity extends AppCompatActivity implements Injectable, View.OnClickListener, HasSupportFragmentInjector {
    private final String TAG = "ExcavationActivity";
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    @Inject
    MyViewModelFactory factory;
    @Inject
    UserDao mUserDao;
    private TextView txtTrenchTrialWorkshop;
    private TextView txtTrenchTrial;
    private TextView txtSupervisionName;
    private TextView txtStatus;
    private RecyclerView rvExcavation;
    private FloatingActionButton btnAddProject;
    private List<ExcavationItem> mExcavationItems = new ArrayList<>();
    private Context mContext;
    private ExcavationAdapter mAdapter;
    private ExcavationItemListViewModel viewModel;
    private String mProjectId = "";
    private String mProjectName = "";
    private View waitingV;

    public ExcavationActivity() {
        this.mContext = this;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excavation);
        findViews();

        if (getIntent().getExtras() != null) {
            mProjectId = getIntent().getStringExtra(MyConst.EXTRA_PROJECT_ID);
            mProjectName = getIntent().getStringExtra(MyConst.EXTRA_PROJECT_NAME);
            setTitle(mProjectName);
        }

        mAdapter = new ExcavationAdapter(mExcavationItems);
        rvExcavation.setAdapter(mAdapter);
        rvExcavation.setLayoutManager(new LinearLayoutManager(mContext));
        rvExcavation.addItemDecoration(new DividerItemDecoration(ExcavationActivity.this, DividerItemDecoration.VERTICAL));
        viewModel = ViewModelProviders.of(ExcavationActivity.this, factory).get(ExcavationItemListViewModel.class);
        viewModel.initial(mProjectId);
        viewModel.getExcavationItemList().observe(ExcavationActivity.this, excavationItems -> {
            Log.d("ExcavationItemListViewM", "observe: ");
            mExcavationItems.removeAll(mExcavationItems);
            mExcavationItems.addAll(excavationItems);
            mAdapter.notifyDataSetChanged();
            waitingV.setVisibility(View.GONE);
            Log.d(TAG, "onActivityCreated: projectList:" + excavationItems.toString());
        });

    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-09-01 20:45:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        txtTrenchTrialWorkshop = findViewById(R.id.txtTrenchTrialWorkshop);
        txtTrenchTrial = findViewById(R.id.txtTrenchTrial);
        txtSupervisionName = findViewById(R.id.txtSupervisionName);
        txtStatus = findViewById(R.id.txtStatus);
        rvExcavation = findViewById(R.id.rvExcavation);
        btnAddProject = findViewById(R.id.btnAddProject);
        waitingV = findViewById(R.id.waitingView);

        btnAddProject.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-09-01 20:45:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnAddProject) {
            NewExcavationItem1Dialog dialog = NewExcavationItem1Dialog.newInstance(mProjectId);
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2018-09-01 20:50:07 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtTrenchTrialWorkshop;
        public final TextView txtTrenchTrial;
        public final TextView txtSupervisionName;
        public final TextView txtStatus;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            txtTrenchTrialWorkshop = rootView.findViewById(R.id.txtTrenchTrialWorkshop);
            txtTrenchTrial = rootView.findViewById(R.id.txtTrenchTrial);
            txtSupervisionName = rootView.findViewById(R.id.txtSupervisionName);
            txtStatus = rootView.findViewById(R.id.txtStatus);
        }


    }

    private class ExcavationAdapter extends RecyclerView.Adapter<ViewHolder> {
        List<ExcavationItem> excavationItems;

        public ExcavationAdapter(List<ExcavationItem> excavationItems) {
            this.excavationItems = excavationItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_excavation_rv, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
            ExcavationItem excavationItem = excavationItems.get(i);
            vh.txtTrenchTrialWorkshop.setText(excavationItem.getName());
            vh.txtTrenchTrial.setText(excavationItem.getSubTrailTrenchName());
            vh.txtSupervisionName.setText(excavationItem.getSupervisorName());
            vh.itemView.setOnClickListener(view ->
            {
                Intent intent = new Intent();
                intent.setClass(ExcavationActivity.this, ExcavationItemActivity.class);
                intent.putExtra(MyConst.EXTRA_EXCAVATION_ITEM_ID, excavationItem.getId());
                intent.putExtra(MyConst.EXTRA_EXCAVATION_ITEM_LAYER_CODING, excavationItem.getLayerFeatureCoding());
                intent.putExtra(MyConst.EXTRA_REGISTRATION_CODING, excavationItem.getRegistrationCoding());
                intent.putExtra(MyConst.EXTRA_PROJECT_NAME, mProjectName);
                startActivity(intent);

            });
        }

        @Override
        public int getItemCount() {
            return excavationItems.size();
        }
    }


}
