package com.cs442.yliu308.foodorderapp;

/**
 * Created by liuyi on 9/23/17.
 */

public class Item {
    public int    iItemSeqNum;
    public String strItemName;
    public double dItemPrice;
    public int    iItemOrdNum;
    public int    iItemImgId;

    public Item(int iItemSeqNum, String strItemName, double dItemPrice, int iItemOrdNum, int iItemImgId){
        this.iItemSeqNum = iItemSeqNum;
        this.strItemName = strItemName;
        this.dItemPrice  = dItemPrice;
        this.iItemOrdNum = iItemOrdNum;
        this.iItemImgId  = iItemImgId;
    }

    public void setItemOrdNum(int iItemOrdNum){
        this.iItemOrdNum = iItemOrdNum;
    }
}