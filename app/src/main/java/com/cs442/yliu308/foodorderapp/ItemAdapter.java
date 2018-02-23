package com.cs442.yliu308.foodorderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liuyi on 9/24/17.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private int resource;
    private boolean bIsMenuList;

    public ItemAdapter(Context context, int resource, List<Item> objects, boolean bIsMenuList) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        this.resource = resource;//resource为listView的每个子项的布局id
        this.bIsMenuList = bIsMenuList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub

        final Item item = getItem(position);
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.itemSeqNum = (TextView) view.findViewById(R.id.item_seq_number);
            viewHolder.itemName =(TextView) view.findViewById(R.id.item_name);
            viewHolder.itemPrice =(TextView) view.findViewById(R.id.item_price);
            viewHolder.itemOrdNum = (TextView) view.findViewById(R.id.item_ord_number);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemSeqNum.setText(Integer.toString(item.iItemSeqNum));
        viewHolder.itemName.setText(item.strItemName);
        viewHolder.itemPrice.setText(Double.toString(item.dItemPrice));
        viewHolder.itemOrdNum.setText(Integer.toString(item.iItemOrdNum));

        if(bIsMenuList){
            if(0 != item.iItemOrdNum){
                viewHolder.itemOrdNum.setBackgroundColor(Color.parseColor("#FF99FF"));
            }
            else{
                viewHolder.itemOrdNum.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }

        return view;

    }
    class ViewHolder{
        TextView itemSeqNum;
        TextView itemName;
        TextView itemPrice;
        TextView itemOrdNum;
    }
}