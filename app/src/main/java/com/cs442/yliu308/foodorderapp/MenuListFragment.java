package com.cs442.yliu308.foodorderapp;

/**
 * Created by liuyi on 9/23/17.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.FragmentManager;
import android.content.Intent;
import android.app.Fragment;
//import android.support.v4.app.Fragment;//不知为什么要加这个？
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import static android.content.Context.MODE_PRIVATE;

public class MenuListFragment extends Fragment {
    private SharedPreferences sp;

    private ItemAdapter mListMenuAdapter = null;
    public static List<Item> itemList;
    private ListView menuListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp = this.getActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);

        initItemList();

        View v = inflater.inflate(R.layout.fragment_menu_list,null);

        menuListView = (ListView) v.findViewById(R.id.menu_list);
        mListMenuAdapter = new ItemAdapter(getActivity(), R.layout.fragment_menu_item, itemList, true);
        menuListView.setAdapter(mListMenuAdapter);

        menuListView.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick (AdapterView<?> arg0, View arg1, int arg2, long arg3){
                Item item = mListMenuAdapter.getItem(arg2);

                if(null == item) return;

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("iItemSeqNum", item.iItemSeqNum);
                intent.putExtra("strItemName", item.strItemName);
                intent.putExtra("dItemPrice", item.dItemPrice);
                intent.putExtra("iItemOrdNumber", item.iItemOrdNum);
                intent.putExtra("iItemImgId", item.iItemImgId);

                //intent.putExtra("itemOrderedList", (Serializable)MenuCartFragment.itemOrderedList);

                startActivity(intent);
            }

        });

        return v;
    }

    public void initItemList(){
        itemList = new ArrayList<Item>();
        int[] tmpArray = parseFromSharePreference(sp);
        if(null != tmpArray) {
            for (int i = 0; i < 10; i++) {
                Item item = new Item(i + 1, "Food #" + Integer.toString(i + 1), 10.0 * (i + 1), tmpArray[i], DetailActivity.imgArr[i]);
                itemList.add(item);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                Item item = new Item(i + 1, "Food #" + Integer.toString(i + 1), 10.0 * (i + 1), 0, DetailActivity.imgArr[i]);
                itemList.add(item);
            }
            saveToSharedPreference(itemList);
        }
        //item = new Item(2,"Item 2", 10.0, 0);
        //itemList.add(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        mListMenuAdapter.notifyDataSetChanged();
        saveToSharedPreference(itemList);
    }

    public int[] parseFromSharePreference(SharedPreferences sp){
        String strTmp = sp.getString("itemList", null);
        if(null == strTmp)return null;

        String[] tmpStrArray = strTmp.split("-");

        int[] array = new int[tmpStrArray.length];
        for(int i = 0; i < array.length; i++){
            array[i] = Integer.parseInt(tmpStrArray[i]);
        }
        return array;
    }

    public void saveToSharedPreference(List<Item> itemList){
        int[] itemNum = new int[itemList.size()];
        for(int i = 0; i < itemList.size(); i++){
            itemNum[i] = itemList.get(i).iItemOrdNum;
        }

        StringBuffer sb = new StringBuffer();
        for(int i : itemNum){
            sb.append(i);
            sb.append('-');
        }

        //sp = this.getActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);
        sp.edit().putString("itemList",sb.toString()).commit();
    }
}
