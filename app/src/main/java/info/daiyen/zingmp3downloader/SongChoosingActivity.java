package info.daiyen.zingmp3downloader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SongChoosingActivity extends Activity {

    private ListView mainListView ;
    private SongInfo[] Songs ;
    private ArrayAdapter<SongInfo> listAdapter ;
    private ArrayList<SongInfo> SongList;
    Intent intent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_choosing);
        intent = getIntent();
        SongList = new ArrayList<SongInfo>();
        Button btnDownload = (Button)findViewById(R.id.btnDownload);
        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.listView1 );

        btnDownload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Gửi thông điệp là lưu bình phương
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",SongList);
                setResult(RESULT_OK,returnIntent);
                finish();
            }

        });
        // When item is tapped, toggle checked properties of CheckBox and Song.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                SongInfo Song = listAdapter.getItem( position );
                //listAdapter.
                Song.toggleChecked();
                Toast.makeText(getApplicationContext(), Song.getName(),
                        Toast.LENGTH_SHORT).show();
                SongViewHolder viewHolder = (SongViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( Song.isChecked() );
            }
        });




        try {
            SongList = (ArrayList<SongInfo>) intent.getSerializableExtra("songs");
            // Set our custom array adapter as the ListView's adapter.
            if(SongList.size()>0)
            {
                listAdapter = new SongArrayAdapter(this, SongList);
                mainListView.setAdapter( listAdapter );
            }
            else
            {
                mainListView.setEmptyView(null);
            }
            ;
        }
        catch(Exception e)
        {}

    }



    /** Holds child views for one row. */
    private static class SongViewHolder {
        private CheckBox checkBox ;
        private TextView textView ;
        public SongViewHolder() {}
        public SongViewHolder( TextView textView, CheckBox checkBox ) {
            this.checkBox = checkBox ;
            this.textView = textView ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }
        public TextView getTextView() {
            return textView;
        }
        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /** Custom adapter for displaying an array of Song objects. */
    private static class SongArrayAdapter extends ArrayAdapter<SongInfo> {

        private LayoutInflater inflater;

        public SongArrayAdapter( Context context, List<SongInfo> SongList ) {
            super( context, R.layout.row, R.id.textView1, SongList );
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Song to display
            SongInfo Song = (SongInfo) this.getItem( position );

            // The child views in each row.
            CheckBox checkBox ;
            TextView textView ;

            // Create a new row view
            if ( convertView == null ) {
                convertView = inflater.inflate(R.layout.row, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById( R.id.textView1 );
                checkBox = (CheckBox) convertView.findViewById( R.id.checkBox1 );

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag( new SongViewHolder(textView,checkBox) );

                // If CheckBox is toggled, update the Song it is tagged with.
                checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        SongInfo Song = (SongInfo) cb.getTag();
                        Song.setChecked( cb.isChecked() );
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                SongViewHolder viewHolder = (SongViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox() ;
                textView = viewHolder.getTextView() ;
            }

            // Tag the CheckBox with the Song it is displaying, so that we can
            // access the Song in onClick() when the CheckBox is toggled.
            checkBox.setTag( Song );

            // Display Song data
            checkBox.setChecked( Song.isChecked() );
            textView.setText( Song.getName() );

            return convertView;
        }

    }

   
}