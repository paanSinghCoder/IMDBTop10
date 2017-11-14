
package com.topten.grv.imdbtop10.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by grv on 11-11-2017.
 */

public class Poster {

    @SerializedName("thumb")
    @Expose
    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

}
