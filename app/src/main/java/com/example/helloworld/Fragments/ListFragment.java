package com.example.helloworld.Fragments;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.Activities.listActivity;
import com.example.helloworld.ItemAdapter;
import com.example.helloworld.Model;
import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public ListFragment(){}

    RecyclerView recyclerView;
    List<Model> itemList;
    List<ScanResult> scanlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.list_fragement, container, false);

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initData();
        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("listresult");
        scanlist = (List<ScanResult>) args.getSerializable("scanlist");


        recyclerView.setAdapter(new ItemAdapter(initData(),getContext()));



        return view;
    }

    private List<Model> initData() {

        itemList=new ArrayList<>();
        try {
            if(scanlist.size() > 0){
                for(ScanResult scanResult: scanlist){
                    itemList.add(new Model(R.drawable.thumbnail,scanResult.SSID, scanResult.BSSID, (scanResult.level+100) ) );
                }
            }
        }catch (Exception exception){
            Toast.makeText(getActivity(), "scan results not arrived ", Toast.LENGTH_SHORT).show();
        }
//

        return itemList;
    }
}

