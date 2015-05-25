package info.daiyen.zingmp3downloader;

import java.io.Serializable;

/**
 * Created by Corncob on 25/04/2015.
 */

/** Holds Song data. */
public class SongInfo implements Serializable{
    public String SongDownloadUrl;
    public String SongName = "" ;
    private boolean checked = true ;
    public SongInfo() {}
    public SongInfo(String name) {
        this.SongName = name ;
    }
    public SongInfo(String name, boolean checked) {
        this.SongName = name ;
        this.checked = checked ;
    }
    public String getName() {
        return SongName;
    }
    public void setName(String name) {
        this.SongName = name;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String toString() {
        return SongName ;
    }
    public void toggleChecked() {
        checked = !checked ;
    }
    public void SetSongInfo(String url, String name)
    {
        this.SongDownloadUrl = url;
        this.SongName = name;
    }
}