package info.daiyen.zingmp3downloader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private ArrayList<SongInfo> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        songList = new ArrayList<SongInfo>();
        try {
            Intent intent = getIntent();
            String action = intent.getAction();
            String type = intent.getType();

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    // Handle text being sent
                    String songUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if (songUrl != null) {
                        ZingDownloader downloader = new ZingDownloader(this);
                        downloader.execute(songUrl);
                    }
                } else {
                    // Handle single image being sent
                }
            } else {

                // Handle other intents, such as being started from the home screen
            }
        } catch (Exception e) {
            //do nothing;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void SetSongList(ArrayList<SongInfo> songs)
    {
        this.songList = songs;
    }

    public void OnTaskCompleted()
    {
        if(this.songList.size()>1)
        {
            Intent i = new Intent(MainActivity.this,SongChoosingActivity.class);
            i.putExtra("songs",this.songList);
            startActivityForResult(i,113);
        }
        else
        {
            Intent i = new Intent(MainActivity.this, ZingMp3DownloaderService.class);
            i.putExtra("songs",songList);
            startService(i);
            super.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 113) {
            if(resultCode == RESULT_OK){
                  ArrayList<SongInfo> songs = (ArrayList<SongInfo>)data.getSerializableExtra("result");
//
//                ZingDownloadManager zManager = new ZingDownloadManager(this);
//                zManager.execute(songs);
                  Intent i = new Intent(MainActivity.this, ZingMp3DownloaderService.class);
                  i.putExtra("songs",songs);
                  startService(i);
                  super.finish();
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

}
