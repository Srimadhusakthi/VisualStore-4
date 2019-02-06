package com.visualstore.Model;

public class ShapeBevelModel {
    private int mLenstype;
    private String mId;

    public ShapeBevelModel(int mLenstype, String mId) {
        this.mLenstype = mLenstype;
        this.mId = mId;
    }

    public int getmLenstype() {
        return mLenstype;
    }

    public void setmLenstype(int mLenstype) {
        this.mLenstype = mLenstype;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
