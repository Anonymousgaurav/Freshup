package com.omninos.freshup.Fragment;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omninos.freshup.Adapters.MultipleImagesAdapter;
import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BottomsheetFragment extends BottomSheetDialogFragment {


    private RecyclerView recyclerView;
    private MultipleImagesAdapter adapter;
    private TextView description;

    public BottomsheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);

        initView(view);
        SetUps(view);

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        description=view.findViewById(R.id.description);

    }

    private void SetUps(View view) {

        String value = getArguments().getString("Desc");
        description.setText(value);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (App.getAppPreferences().getMultipleImages()!=null){
            adapter = new MultipleImagesAdapter(getActivity(),App.getAppPreferences().getMultipleImages());
            recyclerView.setAdapter(adapter);
        }
    }

}
