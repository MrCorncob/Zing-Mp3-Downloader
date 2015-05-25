package info.daiyen.zingmp3downloader;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LinkProcessor {

    private SongInfo GetSongDownloadLink(String rawLink)
    {
        String songId = null;
        SongInfo song = new SongInfo();
        int indexOfHtml, lastIndexOfSplash;
        try {
            indexOfHtml = rawLink.indexOf(".html");
            lastIndexOfSplash = rawLink.lastIndexOf('/');
        }
        catch (Exception e)
        {
            return null;
        }
        if (indexOfHtml != -1) {
            songId = rawLink.substring(lastIndexOfSplash + 1, indexOfHtml);
            String fileName = null;
            String rawUrl = "http://v3.mp3.zing.vn/download/vip/song/" + songId;
            URL url = null;
            HttpURLConnection ucon = null;
            String secondURL = null;
            try {

                url = new URL(rawUrl);
                ucon = (HttpURLConnection) url.openConnection();
                if (ucon != null) {
                    ucon.setInstanceFollowRedirects(false);
                }
                secondURL =ucon.getHeaderField("Location");
                int index = secondURL.indexOf("filename=");
                fileName = secondURL.substring(index + "filename=".length(), secondURL.lastIndexOf(".mp3"));
                secondURL = secondURL.substring(0,index-1);
                Log.d("MyLog", "Location" + ucon.getHeaderField("Location"));
                Log.d("MyLog", "File Name:" + fileName);
                song.SetSongInfo(secondURL,fileName);


            } catch (Exception e) {
                Log.d("Exception Log", "Message: " + e.getStackTrace());
                e.printStackTrace();

            }
        }

        return song;
    }


    private ArrayList<SongInfo> GetAlbumDownloadLink(String rawLink)
    {
        ArrayList<SongInfo> songList = new ArrayList<SongInfo>();
        Document doc = null;
        try {
            doc = Jsoup.connect(rawLink).get();
            Elements songItems = doc.select("div.i25.i-small.direct > a");
            for(Element item : songItems)
            {
                SongInfo song;
                String songUrl = item.attr("href");
                song = this.GetSongDownloadLink(songUrl);
                songList.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songList;

    }

    public ArrayList<SongInfo> GetLink(String rawLink) {

        ArrayList<SongInfo> songList = new ArrayList<SongInfo>();
        if (rawLink.contains("mp3.zing.vn/album/"))
        {
            songList = this.GetAlbumDownloadLink(rawLink);
        }
        else
        {
            SongInfo song = new SongInfo();
            song = this.GetSongDownloadLink(rawLink);
            songList.add(song);
        }
        return songList;
    }

}


