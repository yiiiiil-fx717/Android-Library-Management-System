package com.cs442.yliu308.foodorderapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends Activity {
    public static int[] imgArr = {   //pic src
        R.drawable.barbecue,
        R.drawable.fries,
        R.drawable.lamb_soup,
        R.drawable.malatang,
        R.drawable.noodles,
        R.drawable.pho,
        R.drawable.pizza,
        R.drawable.spicy_fish,
        R.drawable.spicy_soup,
        R.drawable.taco
    };

    private SharedPreferences sp;

    private Intent intent;
    private OnClickListener clickHandler;

    private TextView  mItemName;
    private ImageView mItemImg;
    private TextView  mItemDescription;
    private EditText  mItemOrdNum;
    private Button    btnDetaiOrder;
    private Button    btnDetailReset;

    private Item      item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();

        intiDisplay();

        mItemName.setText(item.strItemName);
        mItemImg.setImageResource(R.drawable.barbecue);//item.iItemImgId);
        mItemImg.setImageResource(intent.getIntExtra("iItemImgId", R.drawable.default_white));

        btnDetaiOrder.setOnClickListener(clickHandler);
        btnDetailReset.setOnClickListener(clickHandler);

        mItemOrdNum.setText("1");
    }

    private void intiDisplay(){
        item = new Item(intent.getIntExtra("iItemSeqNum", 0),
                        intent.getStringExtra("strItemName"),
                        intent.getDoubleExtra("dItemPrice", 0),
                        intent.getIntExtra("iItemOrdNumber", 0),
                        intent.getIntExtra("iItemImgId", R.drawable.default_white));

        mItemName = (TextView)findViewById(R.id.detail_item_name);
        mItemImg = (ImageView)findViewById(R.id.detail_item_img);
        mItemDescription = (TextView)findViewById(R.id.detail_item_description);
        mItemOrdNum = (EditText)findViewById(R.id.detail_item_ord_number);
        btnDetaiOrder = (Button)findViewById(R.id.detail_order);
        btnDetailReset = (Button)findViewById(R.id.detail_reset);

        clickHandler = new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.detail_reset:
                    {
                        resetOrderListDetail();
                        onBackPressed();
                    }
                    break;
                    case R.id.detail_order:
                    {
                        if(orderThisItemDetail(item, Integer.parseInt(mItemOrdNum.getText().toString()))) {
                            onBackPressed();
                        }
                    }
                    break;
                default:
                    break;
                }
            }
        };
    }

    public void resetMenuList(){
        MenuListFragment.itemList.clear();
        for(int i = 0; i < 10; i++){
            Item item = new Item(i + 1,"Food #" + Integer.toString(i + 1), 10.0 * (i + 1), 0, imgArr[i]);
            MenuListFragment.itemList.add(item);
        }
        saveToSharedPreference("itemList",MenuListFragment.itemList);
    }

    public void saveToSharedPreference(String key, List<Item> list){
        int[] itemNum = new int[list.size()];
        for(int i = 0; i < list.size(); i++){
            itemNum[i] = list.get(i).iItemOrdNum;
        }
        StringBuffer sb = new StringBuffer();
        for(int i : itemNum){
            sb.append(i);
            sb.append('-');
        }
        sp = this.getSharedPreferences("sharedPreference", MODE_PRIVATE);
        sp.edit().putString(key,sb.toString()).commit();
    }

    private void resetOrderListDetail(){
        //reset pane 1
        resetMenuList();
        //reset pane 2
        MenuCartFragment.itemOrderedList.clear();
    }

    private boolean orderThisItemDetail(Item item, int iItemNum){
        //
        if(iItemNum > 10000){
            Toast.makeText(this,"The number should be no more than 10000",Toast.LENGTH_SHORT).show();
            return false;
        }
        //update pane 1
        for(Item tmpItem : MenuListFragment.itemList){
            if(item.strItemName.equals(tmpItem.strItemName)){
                int index = MenuListFragment.itemList.indexOf(tmpItem);
                MenuListFragment.itemList.remove(tmpItem);
                if(tmpItem.iItemOrdNum + iItemNum > 10000){
                    Toast.makeText(this,"The number should be no more than 10000",Toast.LENGTH_SHORT).show();
                    return false;
                }
                tmpItem.setItemOrdNum(tmpItem.iItemOrdNum + iItemNum);
                MenuListFragment.itemList.add(index,tmpItem);

                saveToSharedPreference("itemList",MenuListFragment.itemList);
                break;
            }
        }
        //update pane 2
        for(Item tmpItem : MenuCartFragment.itemOrderedList){
            if(item.strItemName.equals(tmpItem.strItemName)){
                MenuCartFragment.itemOrderedList.remove(tmpItem);
                if(tmpItem.iItemOrdNum + iItemNum > 10000){
                    Toast.makeText(this,"The number should be no more than 10000",Toast.LENGTH_SHORT).show();
                    return false;
                }
                tmpItem.setItemOrdNum(tmpItem.iItemOrdNum + iItemNum);
                MenuCartFragment.itemOrderedList.add(0,tmpItem);
                return true;
            }
        }
        item.setItemOrdNum(iItemNum);
        MenuCartFragment.itemOrderedList.add(0,item);
        return true;
    }
}
