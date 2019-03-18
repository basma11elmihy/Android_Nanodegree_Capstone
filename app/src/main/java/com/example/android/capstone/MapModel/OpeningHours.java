
package com.example.android.capstone.MapModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    private boolean openNow;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OpeningHours() {
    }

    /**
     * 
     * @param openNow
     */
    public OpeningHours(boolean openNow) {
        super();
        this.openNow = openNow;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

}
