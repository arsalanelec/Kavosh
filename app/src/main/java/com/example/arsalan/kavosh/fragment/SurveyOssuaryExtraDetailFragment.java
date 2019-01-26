package com.example.arsalan.kavosh.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentSurveyOssuaryExtraDetailBinding;
import com.example.arsalan.kavosh.databinding.ItemLaverBinding;
import com.example.arsalan.kavosh.databinding.ItemNicheBinding;
import com.example.arsalan.kavosh.dialog.AddEditLaverDialog;
import com.example.arsalan.kavosh.dialog.AddEditNicheDialog;
import com.example.arsalan.kavosh.model.Laver;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.model.Niche;
import com.example.arsalan.kavosh.model.SurOssuaryExtraDetail;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyOssuaryExtraDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SurveyOssuaryExtraDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyOssuaryExtraDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "SurveyOssuaryExtraDetai";
    private static final int REQ_EDIT_NICHE = 2;
    private static final int REQ_ADD_NICHE = 1;
    private static final int REQ_ADD_LAVER = 3;
    private static final int REQ_EDIT_LAVER = 4;

    private OnFragmentInteractionListener mListener;
    private SurOssuaryExtraDetail mDetail;
    private List<Laver> mLaverList;
    private List<Niche> mNicheList;
    private int mCurrentPosition;
    private NicheLLAdapter mAdapterNiche;
    private LaverLLAdapter mAdapterLaver;

    public SurveyOssuaryExtraDetailFragment() {
        // Required empty public constructor
    }

    public static SurveyOssuaryExtraDetailFragment newInstance(String param1) {
        SurveyOssuaryExtraDetailFragment fragment = new SurveyOssuaryExtraDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonStr = getArguments().getString(ARG_PARAM1);
            try {
                Gson gson = new Gson();
                mDetail = gson.fromJson(jsonStr, SurOssuaryExtraDetail.class);
            } catch (Exception e) {

            }
            if (mDetail == null) mDetail = new SurOssuaryExtraDetail();
            mLaverList = mDetail.getLaverList();
            mNicheList = mDetail.getNicheList();
            if (mLaverList == null) mLaverList = new ArrayList<>();
            if (mNicheList == null) mNicheList = new ArrayList<>();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSurveyOssuaryExtraDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_ossuary_extra_detail, container, false);
        binding.setOssuaryDetail(mDetail);
        ArrayAdapter<String> textAdapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.array_ossuary_entry));
        binding.txtEntryProperties.setAdapter(textAdapter1);

        mDetail.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Gson gson = new Gson();
                mDetail.setLaverList(mLaverList);
                mDetail.setNicheList(mNicheList);
                mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                Log.d(TAG, "onPropertyChanged: ");
            }
        });

        mAdapterNiche = new NicheLLAdapter(mNicheList, binding.llNiche);
        mAdapterLaver = new LaverLLAdapter(mLaverList, binding.llLaver);

        binding.btnAddNiche.setOnClickListener(b -> {
            AddEditNicheDialog dialog = new AddEditNicheDialog();
            dialog.setTargetFragment(SurveyOssuaryExtraDetailFragment.this, REQ_ADD_NICHE);
            dialog.show(getFragmentManager(), "");
        });

        binding.btnAddLaver.setOnClickListener(b -> {
            AddEditLaverDialog dialog = new AddEditLaverDialog();
            dialog.setTargetFragment(SurveyOssuaryExtraDetailFragment.this, REQ_ADD_LAVER);
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
            case REQ_ADD_NICHE:
                if (resultCode == Activity.RESULT_OK) {
                    Niche niche = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mNicheList.add(niche);
                    mAdapterNiche.notifyDataSetChanged();
                    Gson gson = new Gson();
                    mDetail.setNicheList(mNicheList);
                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                }
                break;

            case REQ_ADD_LAVER:
                if (resultCode == Activity.RESULT_OK) {
                    Laver laver = data.getParcelableExtra(MyConst.EXTRA_MODEL);
                    mLaverList.add(laver);
                    mAdapterLaver.notifyDataSetChanged();
                    Gson gson = new Gson();
                    mDetail.setLaverList(mLaverList);
                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                }
                break;

            case REQ_EDIT_NICHE:
                if (resultCode == Activity.RESULT_OK) {
                    int index = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    mNicheList.remove(index);
                    mNicheList.add(index, data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mAdapterNiche.notifyDataSetChanged();
                    Gson gson = new Gson();
                    mDetail.setNicheList(mNicheList);
                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                }
                break;

            case REQ_EDIT_LAVER:
                if (resultCode == Activity.RESULT_OK) {
                    int index = data.getIntExtra(MyConst.EXTRA_INDEX, 0);
                    mLaverList.remove(index);
                    mLaverList.add(index, data.getParcelableExtra(MyConst.EXTRA_MODEL));
                    mAdapterLaver.notifyDataSetChanged();
                    Gson gson = new Gson();
                    mDetail.setLaverList(mLaverList);
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

    public interface EventListenerNiche {
        void onNicheOptionsClicked(Niche niche, View view, int index);
    }

    public interface EventListenerLaver {
        void onOptionsClicked(Laver laver, View view, int index);
    }

    private class NicheLLAdapter implements EventListenerNiche {
        LinearLayout linearLayout;
        List<Niche> arrayList;

        public NicheLLAdapter(List<Niche> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            if (arrayList.size() > 0) {
                View header = LayoutInflater.from(getContext()).inflate(R.layout.header_ossuary, null);
                linearLayout.addView(header);
            }
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Niche getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemNicheBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_niche, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemNicheBinding) convertView.getTag();
            }
            binding.setNiche(getItem(position));
            binding.setView(binding.tvOptions);
            binding.setEventListenerNiche(NicheLLAdapter.this);
            binding.setIndex(position);
            View v = binding.getRoot();
            convertView.setTag(position);
            registerForContextMenu(convertView);

            return v;

        }

        @Override
        public void onNicheOptionsClicked(Niche niche, View view, int index) {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(getContext(), view);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_delete_edit);

            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        AddEditNicheDialog dialog = AddEditNicheDialog.newInstance(index, niche);
                        dialog.setTargetFragment(SurveyOssuaryExtraDetailFragment.this, REQ_EDIT_NICHE);
                        dialog.show(getFragmentManager(), "");
                        return true;
                    case R.id.menu_delete:
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
                                    mNicheList.remove(index);
                                    mAdapterNiche.notifyDataSetChanged();
                                    Gson gson = new Gson();
                                    mDetail.setNicheList(mNicheList);
                                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                                    dialogInterface.dismiss();
                                })
                                .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                                .create().show();
                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popup.show();
        }
    }

    private class LaverLLAdapter implements EventListenerLaver {
        LinearLayout linearLayout;
        List<Laver> arrayList;

        public LaverLLAdapter(List<Laver> arrayList, LinearLayout linearLayout) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
            if (arrayList.size() > 0) {
                View header = LayoutInflater.from(getContext()).inflate(R.layout.header_ossuary, null);
                linearLayout.addView(header);
            }
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public Laver getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemLaverBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laver, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemLaverBinding) convertView.getTag();
            }
            binding.setLaver(getItem(position));
            binding.setView(binding.tvOptions);
            binding.setEventListener(LaverLLAdapter.this);
            binding.setIndex(position);
            View v = binding.getRoot();
            convertView.setTag(position);
            registerForContextMenu(convertView);

            return v;

        }

        @Override
        public void onOptionsClicked(Laver laver, View view, int index) {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(getContext(), view);
            //inflating menu from xml resource
            popup.inflate(R.menu.menu_delete_edit);

            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        AddEditLaverDialog dialog = AddEditLaverDialog.newInstance(index, laver);
                        dialog.setTargetFragment(SurveyOssuaryExtraDetailFragment.this, REQ_EDIT_LAVER);
                        dialog.show(getFragmentManager(), "");
                        return true;
                    case R.id.menu_delete:
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
                                    mLaverList.remove(index);
                                    notifyDataSetChanged();
                                    Gson gson = new Gson();
                                    mDetail.setLaverList(mLaverList);
                                    mListener.onSurveyExtraDetail(gson.toJson(mDetail));
                                    dialogInterface.dismiss();
                                })
                                .setNegativeButton("خیر", (dialogInterface, i) -> dialogInterface.dismiss())
                                .create().show();
                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popup.show();
        }
    }
}
