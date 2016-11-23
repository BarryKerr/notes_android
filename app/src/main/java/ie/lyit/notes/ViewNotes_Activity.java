package ie.lyit.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class ViewNotes_Activity extends AppCompatActivity {
    private ListView listView;
    private DBHelper dbHelper;
    private ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes_);

        dbHelper = new DBHelper(ViewNotes_Activity.this,null,null,3);
        listView = (ListView) findViewById(R.id.listViewNotes);

        notes = new ArrayList<>();

        notes = dbHelper.getNotes();

        NotesAdapter notesAdopter = new NotesAdapter(ViewNotes_Activity.this,R.layout.note_layout,notes);
        listView.setAdapter(notesAdopter);
    }
}
