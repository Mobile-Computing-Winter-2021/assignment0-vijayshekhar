package com.example.helloworld.Fragments;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.WifiapAdaptor;
import com.example.helloworld.Model;
import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

public class WifiListFragment extends Fragment {

//    public ListFragment(){}

    List<ScanResult> scanlist;
    List<Model> itemList;
    RecyclerView wifiListView;
    private static final String TAG = "ListFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.list_fragement, container, false);

        wifiListView =view.findViewById(R.id.rec1);
//        wifiListView.setHasFixedSize(true);
        wifiListView.setLayoutManager(new LinearLayoutManager(getContext()));


        Intent intent = getActivity().getIntent();
        Bundle args = intent.getBundleExtra("listresult");
        scanlist = (List<ScanResult>) args.getSerializable("scanlist");
        Log.d(TAG, "onCreateView: Data sent as bundle,");

        wifiListView.setAdapter(new WifiapAdaptor(populateRecyclerView(),getContext()));

        Log.d(TAG, "onCreateView: Adaptor initialised");

        return view;
    }

    private List<Model> populateRecyclerView() {

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
        Log.d(TAG, "populateRecyclerView: list returned");
        return itemList;
    }
}

