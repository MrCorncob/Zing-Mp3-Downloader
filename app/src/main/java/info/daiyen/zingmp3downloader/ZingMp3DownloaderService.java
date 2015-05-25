package info.daiyen.zingmp3downloader;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.net.URI;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class ZingMp3DownloaderService extends IntentService {


    public ZingMp3DownloaderService() {
        super("ZingMp3DownloaderService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<SongInfo> songInfos = (ArrayList<SongInfo>) intent.getSerializableExtra("songs");
            for(SongInfo song:songInfos)
            {
                Log.d("SongList", song.SongName + " : " + song.SongDownloadUrl);
                if(song.isChecked())
                    addDownloadQueue(song);
            }
        }
    }

    private void addDownloadQueue(SongInfo song)
    {
        try {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(song.SongDownloadUrl));
            request.setDescription("Zing MP3 Downloader");
            request.setTitle(song.SongName+".mp3");
            request.setMimeType("audio/mpeg");
            // in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            }
            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, song.SongName+".mp3");


            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }
        catch (Exception e)
        {

        }
    }

}
