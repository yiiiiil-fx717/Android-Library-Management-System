package com.cs442.yliu308.foodorderapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liuyi on 9/23/17.
 */

public class MenuCartFragment extends Fragment{
    SharedPreferences sp;

    private int iTotalValue;
    private OnClickListener clickHandler;

    private ItemAdapter mCartAdapter = null;
    public static List<Item> itemOrderedList;
    private TextView cartTotalPrice;
    private ListView cartListView;
    private Button   btnCartOrder;
    private Button   btnCartReset;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sp = this.getActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);

        initOrderedList();
        View v = inflater.inflate(R.layout.fragment_menu_cart,null);

        cartTotalPrice = (TextView) v.findViewById(R.id.cart_total_price);
        cartTotalPrice.setText(Integer.toString(iTotalValue));

        cartListView = (ListView) v.findViewById(R.id.cart_list);
        mCartAdapter = new ItemAdapter(getActivity(), R.layout.fragment_menu_item, itemOrderedList, false);
        cartListView.setAdapter(mCartAdapter);

        intiDisplay(v);
        btnCartOrder.setOnClickListener(clickHandler);
        btnCartReset.setOnClickListener(clickHandler);

        return v;
    }

    public void initOrderedList(){
        itemOrderedList = new ArrayList<Item>();

        int[] tmpArray = parseFromSharePreference(sp);
        if(null != tmpArray) {
            for (int i = 0; i < 10; i++) {
                if(0 != tmpArray[i]){
                    Item item = new Item(i + 1, "Item #" + Integer.toString(i + 1), 10.0 * (i + 1), tmpArray[i], DetailActivity.imgArr[i]);
                    itemOrderedList.add(item);
                }
            }
        }
        //for(Item item : MenuListFragment.itemList){
        //    if(0 != item.iItemOrdNum){
        //        itemOrderedList.add(item);
        //    }
        //}
        getTotalValue();
    }

    public void getTotalValue(){
        iTotalValue = 0;
        for(Item item : itemOrderedList){
            iTotalValue += item.iItemOrdNum * item.dItemPrice;
        }
    }

    private void intiDisplay(View v){
        btnCartOrder = (Button)v.findViewById(R.id.cart_order);
        btnCartReset = (Button)v.findViewById(R.id.cart_reset);

        clickHandler = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.cart_reset:
                    {
                        resetOrderListCart();
                    }
                    break;
                    case R.id.cart_order:
                    {
                        orderThisItemCart();
                    }
                    break;
                default:
                    break;
                }
            }
        };
    }

    private void resetOrderListCart(){
        MenuListFragment.itemList.clear();
        for (int i = 0; i < 10; i++) {
            Item item = new Item(i + 1, "Item #" + Integer.toString(i + 1), 10.0 * (i + 1), 0, DetailActivity.imgArr[i]);
            MenuListFragment.itemList.add(item);
        }
        saveToSharedPreference("itemList",MenuListFragment.itemList);


        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.id_fragment_list, new MenuListFragment());
        fragmentTransaction.commit();

        itemOrderedList.clear();
        mCartAdapter.notifyDataSetChanged();
        getTotalValue();
        cartTotalPrice.setText(Integer.toString(iTotalValue));
    }

    public void saveToSharedPreference(String key,List<Item> list){
        int[] itemNum = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            itemNum[i] = list.get(i).iItemOrdNum;
        }

        StringBuffer sb = new StringBuffer();
        for(int i : itemNum){
            sb.append(i);
            sb.append('-');
        }

        //sp = this.getActivity().getSharedPreferences("sharedPreference", MODE_PRIVATE);
        sp.edit().putString(key,sb.toString()).commit();
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

    private void resetToInit(){
        resetOrderListCart();
    }

    private void orderThisItemCart(){
        Toast.makeText(getActivity(),
                "Your order has been placed, total price is $" + Integer.toString(iTotalValue),
                Toast.LENGTH_SHORT).show();
        resetToInit();
    }

    @Override
    public void onResume(){
        super.onResume();
        mCartAdapter.notifyDataSetChanged();

        getTotalValue();
        cartTotalPrice.setText(Integer.toString(iTotalValue));
    }

}
