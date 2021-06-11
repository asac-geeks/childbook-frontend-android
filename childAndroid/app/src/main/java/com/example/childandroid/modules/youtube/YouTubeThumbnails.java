package com.example.childandroid.modules.youtube;

public class YouTubeThumbnails {
   YouTubeThumbnailsHigh high;

    public YouTubeThumbnails(){}
    public YouTubeThumbnails(YouTubeThumbnailsHigh high) {
        this.high = high;
    }

    public YouTubeThumbnailsHigh getHigh() {
        return high;
    }

    public void setHigh(YouTubeThumbnailsHigh high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "YouTubeThumbnails{" +
                "high=" + high +
                '}';
    }
}
