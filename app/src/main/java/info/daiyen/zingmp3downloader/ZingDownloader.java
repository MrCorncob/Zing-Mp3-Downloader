package info.daiyen.zingmp3downloader;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Corncob on 25/04/2015.
 */
public class ZingDownloader extends AsyncTask<String, Integer, ArrayList<SongInfo>> {

    public MainActivity activityParent;
    public ZingDownloader(MainActivity act)
    {
        activityParent = act;
    }

    @Override
    protected ArrayList<SongInfo> doInBackground(String... songUrl) {
        String rawLink = songUrl[0];
        LinkProcessor coreProcessor = new LinkProcessor();
        ArrayList<SongInfo> songInfos = new ArrayList<SongInfo>();
        songInfos = coreProcessor.GetLink(rawLink);
        return songInfos;
    }


    @Override
    protected void onPostExecute(ArrayList<SongInfo> songInfos) {
        super.onPostExecute(songInfos);
        activityParent.SetSongList(songInfos);
        Toast.makeText(activityParent,"Đã Get Link Xong, Chọn bài cần tải.",Toast.LENGTH_LONG);
        activityParent.OnTaskCompleted();
    }
}
