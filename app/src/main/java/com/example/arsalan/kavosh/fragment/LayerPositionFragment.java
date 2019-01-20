package com.example.arsalan.kavosh.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.example.arsalan.kavosh.R;
import com.example.arsalan.kavosh.databinding.FragmentLayerPositionBinding;
import com.example.arsalan.kavosh.databinding.ItemLayerBinding;
import com.example.arsalan.kavosh.databinding.ItemLayerPostitionBinding;
import com.example.arsalan.kavosh.di.Injectable;
import com.example.arsalan.kavosh.dialog.AddLayerPositionDialog;
import com.example.arsalan.kavosh.model.LayerCoordination;
import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.model.LayerPosition;
import com.example.arsalan.kavosh.model.MyConst;
import com.example.arsalan.kavosh.room.LayerFeatureDao;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import static android.app.Activity.RESULT_OK;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_INDEX;
import static com.example.arsalan.kavosh.model.MyConst.EXTRA_MODEL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LayerPositionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LayerPositionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LayerPositionFragment extends androidx.fragment.app.Fragment implements Injectable, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final int REQ_ADD_BESIDE_COORDINATION = 100;
    private static final int REQ_ADD_CUTTING_LAYER = 101;
    private static final int REQ_ADD_TOP_OF_LAYER = 102;
    private static final int REQ_ADD_AROUND_LAYER = 103;
    private static final int REQ_ADD_INSIDE_LAYER = 104;
    private static final int REQ_ADD_CUTED_BY_LAYER = 105;
    private static final int REQ_ADD_UNDER_LAYER = 113;

    private static final String ARG_PARAM2 = "param2";
    private static final int REQ_EDIT_BESIDE_COORDINATION = 106;
    private static final int REQ_EDIT_AROUND_LAYER = 107;
    private static final int REQ_EDIT_CUTED_BY_LAYER = 108;
    private static final int REQ_EDIT_CUTTING_LAYER = 109;
    private static final int REQ_EDIT_INSIDE_LAYER = 110;
    private static final int REQ_EDIT_TOP_OF_LAYER = 111;
    private static final int REQ_EDIT_UDER_LAYER = 112;
    private static final int REQ_EDIT_SIMILAR_LAYER = 114;
    private static final int REQ_ADD_SIMILAR_LAYER = 115;
    @Inject
    LayerFeatureDao mLayerFeatureDao;
    // TODO: Rename and change types of parameters
    private LayerPosition mLayerPosition;
    private OnFragmentInteractionListener mListener;
    private LLLayerCoordinationAdapter adapterBesideLayer;
    private LLLayerCoordinationAdapter adapterTopOfLayer;
    private LLLayerCoordinationAdapter adapterCuttingLayer;
    private LLLayerCoordinationAdapter adapterCuttedByLayer;
    private LLLayerCoordinationAdapter adapterInsideLayer;
    private LLLayerCoordinationAdapter adapterAroundLater;
    private LLLayerAdapter adapterLayerUder;
    private LLLayerAdapter adapterLayerSimilar;
    private List<LayerCoordination> mBesideCoordiantionList;
    private List<LayerCoordination> mTopOfCoordiantionList;
    private List<LayerCoordination> mCuttingCoordiantionList;
    private List<LayerCoordination> mCuttedByCoordiantionList;
    private List<LayerCoordination> mInsideCoordiantionList;
    private List<LayerCoordination> mAroundCoordiantionList;
    private List<String> mLayerUderList;
    private List<String> mLayerSimilarList;
    private String mExcavationItemId;
    private List<LayerFeature> mLayerList;
    private FragmentLayerPositionBinding binding;

    public LayerPositionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment LayerPositionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LayerPositionFragment newInstance(String param1, String excavationItemId) {
        LayerPositionFragment fragment = new LayerPositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, excavationItemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String positionSt = getArguments().getString(ARG_PARAM1);
            mExcavationItemId = getArguments().getString(ARG_PARAM2);
            try {
                Gson gson = new Gson();
                LayerPosition position = gson.fromJson(positionSt, LayerPosition.class);
                if (position != null) mLayerPosition = position;
                else mLayerPosition = new LayerPosition();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_layer_position, container, false);
        if (mLayerPosition != null) {
            mBesideCoordiantionList = mLayerPosition.getBesideLayer();
            mAroundCoordiantionList = mLayerPosition.getAroundlayers();
            mCuttedByCoordiantionList = mLayerPosition.getCuttedByLayers();
            mCuttingCoordiantionList = mLayerPosition.getCuttingLayers();
            mInsideCoordiantionList = mLayerPosition.getInsideLayers();
            mTopOfCoordiantionList = mLayerPosition.getTopOfLayer();
            mLayerUderList = mLayerPosition.getUnderLayer();
            mLayerSimilarList = mLayerPosition.getSimilarLayer();
        } else {
            mBesideCoordiantionList = new ArrayList<>();
            mAroundCoordiantionList = new ArrayList<>();
            mCuttedByCoordiantionList = new ArrayList<>();
            mCuttingCoordiantionList = new ArrayList<>();
            mInsideCoordiantionList = new ArrayList<>();
            mTopOfCoordiantionList = new ArrayList<>();
            mLayerUderList = new ArrayList<>();
            mLayerSimilarList = new ArrayList<>();
        }
        adapterBesideLayer = new LLLayerCoordinationAdapter(mBesideCoordiantionList, binding.llBesideLayer, REQ_EDIT_BESIDE_COORDINATION, "مجاور با لایه:");
        adapterAroundLater = new LLLayerCoordinationAdapter(mAroundCoordiantionList, binding.llAround, REQ_EDIT_AROUND_LAYER, "محصور شده با لایه:");
        adapterCuttedByLayer = new LLLayerCoordinationAdapter(mCuttedByCoordiantionList, binding.llCutedByLayer, REQ_EDIT_CUTED_BY_LAYER, "بریده شده با لایه:");
        adapterCuttingLayer = new LLLayerCoordinationAdapter(mCuttingCoordiantionList, binding.llCuttingLayer, REQ_EDIT_CUTTING_LAYER, "بریده است لایه:");
        adapterInsideLayer = new LLLayerCoordinationAdapter(mInsideCoordiantionList, binding.llInsideLayer, REQ_EDIT_INSIDE_LAYER, "محصور کرده لایه:");
        adapterTopOfLayer = new LLLayerCoordinationAdapter(mTopOfCoordiantionList, binding.llTopOfLayer, REQ_EDIT_TOP_OF_LAYER, "روی لایه:");
        adapterLayerUder = new LLLayerAdapter(mLayerUderList, binding.llUnderLayer, REQ_EDIT_UDER_LAYER, "زیر لایه");
        adapterLayerSimilar = new LLLayerAdapter(mLayerSimilarList, binding.llSimilarLayer, REQ_EDIT_SIMILAR_LAYER, "شبیه لایه");

        binding.btnAddBesideLayer.setOnClickListener(this);
        binding.btnAddTopOfLayer.setOnClickListener(this);
        binding.btnAddAroundLayer.setOnClickListener(this);
        binding.btnAddInsideLayer.setOnClickListener(this);
        binding.btnAddCutedByLayer.setOnClickListener(this);
        binding.btnAddCuttingLayer.setOnClickListener(this);
        binding.btnAddUnderLayer.setOnClickListener(this);
        binding.btnAddSimilarLayer.setOnClickListener(this);

        //  binding.etSimilarLayer.setText(mLayerPosition.getSimilarLayer());

        binding.btnAddUnderLayer.setOnClickListener(this);
        /*binding.etUnderLayer.setText(mLayerPosition.getUnderLayer());
        binding.etUnderLayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mLayerPosition != null && (mLayerPosition.getUnderLayer() == null || !mLayerPosition.getUnderLayer().equals(editable.toString()))) {
                    mLayerPosition.setUnderLayer(editable.toString());
                    Gson gson = new Gson();
                    try {
                        String s = gson.toJson(mLayerPosition);
                        mListener.UpdateLayerPosition(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        LiveData<List<LayerFeature>> layerFeatureListLive = mLayerFeatureDao.loadAllListByItemId(mExcavationItemId);
        layerFeatureListLive.observe(LayerPositionFragment.this, layerFeatureList -> {
            mLayerList = layerFeatureList;

        });

/*
        binding.etSimilarLayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mLayerPosition != null && (mLayerPosition.getSimilarLayer() == null || !mLayerPosition.getSimilarLayer().equals(editable.toString()))) {
                    //   mLayerPosition.setSimilarLayer(editable.toString());
                    Gson gson = new Gson();
                    try {
                        String s = gson.toJson(mLayerPosition);
                        mListener.UpdateLayerPosition(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
*/

        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LayerCoordination lc = data.getParcelableExtra(EXTRA_MODEL);
        int index = data.getIntExtra(EXTRA_INDEX, 0);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_ADD_BESIDE_COORDINATION:
                    mBesideCoordiantionList.add(lc);
                    adapterBesideLayer.notifyDataSetChanged();
                    break;
                case REQ_ADD_AROUND_LAYER:
                    mAroundCoordiantionList.add(lc);
                    adapterAroundLater.notifyDataSetChanged();
                    break;
                case REQ_ADD_CUTED_BY_LAYER:
                    mCuttedByCoordiantionList.add(lc);
                    adapterCuttedByLayer.notifyDataSetChanged();
                    break;
                case REQ_ADD_CUTTING_LAYER:
                    mCuttingCoordiantionList.add(lc);
                    adapterCuttingLayer.notifyDataSetChanged();
                    break;
                case REQ_ADD_INSIDE_LAYER:
                    mInsideCoordiantionList.add(lc);
                    adapterInsideLayer.notifyDataSetChanged();
                    break;
                case REQ_ADD_TOP_OF_LAYER:
                    mTopOfCoordiantionList.add(lc);
                    adapterTopOfLayer.notifyDataSetChanged();
                    break;
                case REQ_ADD_UNDER_LAYER: {
                    String layerName = data.getStringExtra(MyConst.EXTRA_MODEL);
                    mLayerUderList.add(layerName);
                    adapterLayerUder.notifyDataSetChanged();
                }
                break;
                case REQ_ADD_SIMILAR_LAYER: {
                    String layerName = data.getStringExtra(MyConst.EXTRA_MODEL);
                    mLayerSimilarList.add(layerName);
                    adapterLayerSimilar.notifyDataSetChanged();
                }
                break;
                case REQ_EDIT_BESIDE_COORDINATION:
                    mBesideCoordiantionList.remove(index);
                    mBesideCoordiantionList.add(index, lc);
                    adapterBesideLayer.notifyDataSetChanged();
                    break;
                case REQ_EDIT_AROUND_LAYER:
                    mAroundCoordiantionList.remove(index);
                    mAroundCoordiantionList.add(index, lc);
                    adapterAroundLater.notifyDataSetChanged();
                    break;
                case REQ_EDIT_CUTED_BY_LAYER:
                    mCuttedByCoordiantionList.remove(index);
                    mCuttedByCoordiantionList.add(index, lc);
                    adapterCuttedByLayer.notifyDataSetChanged();
                    break;
                case REQ_EDIT_CUTTING_LAYER:
                    mCuttingCoordiantionList.remove(index);
                    mCuttingCoordiantionList.add(index, lc);
                    adapterCuttingLayer.notifyDataSetChanged();
                    break;
                case REQ_EDIT_INSIDE_LAYER:
                    mInsideCoordiantionList.remove(index);
                    mInsideCoordiantionList.add(index, lc);
                    adapterInsideLayer.notifyDataSetChanged();
                    break;
                case REQ_EDIT_TOP_OF_LAYER:
                    mTopOfCoordiantionList.remove(index);
                    mTopOfCoordiantionList.add(index, lc);
                    adapterTopOfLayer.notifyDataSetChanged();
                    break;
            }
            Gson gson = new Gson();
            try {
                String s = gson.toJson(mLayerPosition);
                mListener.UpdateLayerPosition(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void onClick(View view) {
        AddLayerPositionDialog dialog;
        switch (view.getId()) {
            case R.id.btnAddBesideLayer:
                dialog = AddLayerPositionDialog.newInstance("مجاور با لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_BESIDE_COORDINATION);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddTopOfLayer:
                dialog = AddLayerPositionDialog.newInstance("روی لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_TOP_OF_LAYER);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddAroundLayer:
                dialog = AddLayerPositionDialog.newInstance("محصور شده با لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_AROUND_LAYER);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddInsideLayer:
                dialog = AddLayerPositionDialog.newInstance("محصور کرده لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_INSIDE_LAYER);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddCutedByLayer:
                dialog = AddLayerPositionDialog.newInstance("بریده شده با لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_CUTED_BY_LAYER);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddCuttingLayer:
                dialog = AddLayerPositionDialog.newInstance("بریده است لایه:");
                dialog.setTargetFragment(LayerPositionFragment.this, REQ_ADD_CUTTING_LAYER);
                dialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAddUnderLayer:
                if (mLayerList.size() > 0) {
                    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                    for (int i = 0; i < mLayerList.size(); i++) {
                        HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                        hashMap.put("name", mLayerList.get(i).getName());
                        arrayList.add(hashMap);//add the hashmap into arrayList
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_1,
                            new String[]{"name"},
                            new int[]{android.R.id.text1});
                    AlertDialog dialog2 = new AlertDialog.Builder(getContext())
                            .setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mLayerUderList.add(mLayerList.get(i).getName());
                                    adapterLayerUder.notifyDataSetChanged();
                                    dialogInterface.dismiss();
                                    Gson gson = new Gson();
                                    try {
                                        String s = gson.toJson(mLayerPosition);
                                        mListener.UpdateLayerPosition(s);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .create();
                    dialog2.show();
                }
                break;
            case R.id.btnAddSimilarLayer:
                if (mLayerList.size() > 0) {
                    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                    for (int i = 0; i < mLayerList.size(); i++) {
                        HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                        hashMap.put("name", mLayerList.get(i).getName());
                        arrayList.add(hashMap);//add the hashmap into arrayList
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getContext(), arrayList, android.R.layout.simple_list_item_1,
                            new String[]{"name"},
                            new int[]{android.R.id.text1});
                    AlertDialog dialog2 = new AlertDialog.Builder(getContext()).setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mLayerSimilarList.add(mLayerList.get(i).getName());
                            adapterLayerSimilar.notifyDataSetChanged();
                            dialogInterface.dismiss();
                            Gson gson = new Gson();
                            try {
                                String s = gson.toJson(mLayerPosition);
                                mListener.UpdateLayerPosition(s);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).create();
                    dialog2.show();
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
        void UpdateLayerPosition(String positionJson);
    }

    private class LLLayerCoordinationAdapter {
        LinearLayout linearLayout;
        List<LayerCoordination> arrayList;
        int requestCode;
        String title;

        public LLLayerCoordinationAdapter(List<LayerCoordination> arrayList, LinearLayout linearLayout, int requestCode, String title) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            this.requestCode = requestCode;
            this.title = title;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
           /* View header = LayoutInflater.from(getContext()).inflate(R.layout.header_composition, null);
            linearLayout.addView(header);*/
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public LayerCoordination getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("LLLayerCoordinationAda", "getView: ");
            ItemLayerPostitionBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layer_postition, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemLayerPostitionBinding) convertView.getTag();
            }
            binding.setLayerCoordination(getItem(position));
            View v = binding.getRoot();
            v.setOnClickListener(view -> {

                AddLayerPositionDialog dialog = AddLayerPositionDialog.newInstance(title, getItem(position), position);
                dialog.setTargetFragment(LayerPositionFragment.this, requestCode);
                dialog.show(getFragmentManager(), "");
            });
            return v;
        }
    }

    private class LLLayerAdapter {
        LinearLayout linearLayout;
        List<String> arrayList;
        int requestCode;
        String title;

        public LLLayerAdapter(List<String> arrayList, LinearLayout linearLayout, int requestCode, String title) {
            this.arrayList = arrayList;
            this.linearLayout = linearLayout;
            this.requestCode = requestCode;
            this.title = title;
            notifyDataSetChanged();
        }

        private void notifyDataSetChanged() {
            linearLayout.removeAllViews();
           /* View header = LayoutInflater.from(getContext()).inflate(R.layout.header_composition, null);
            linearLayout.addView(header);*/
            for (int i = 0; i < getCount(); i++) {
                linearLayout.addView(getView(i, null, linearLayout));
            }
        }

        public int getCount() {
            return arrayList.size();
        }

        public String getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(getClass().getSimpleName(), "getView: ");
            ItemLayerBinding binding;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layer, null);
                binding = DataBindingUtil.bind(convertView);
                convertView.setTag(binding);
            } else {
                binding = (ItemLayerBinding) convertView.getTag();
            }
            View v = binding.getRoot();
            binding.txtLayerName.setText(getItem(position));
            v.setOnClickListener(view -> {
                /*AddLayerPositionDialog dialog = AddLayerPositionDialog.newInstance(title,getItem(position),position);
                dialog.setTargetFragment(LayerPositionFragment.this, requestCode);
                dialog.show(getFragmentManager(), "");*/
            });
            return v;
        }
    }
}
