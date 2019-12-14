package com.example.journalapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journalapp.R;
import com.example.journalapp.adapter.JournalListAdapter;
import com.example.journalapp.model.Journal;
import com.example.journalapp.viewmodel.JournalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_ID = "extra_data_id";

    private JournalViewModel mJournalViewModel;
    private AlertDialog.Builder mAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final JournalListAdapter adapter = new JournalListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the JournalViewModel.
        mJournalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        // Get all the journals from the database
        // and associate them to the adapter.
        mJournalViewModel.getAllJournals().observe(this, new Observer<List<Journal>>() {
            @Override
            public void onChanged(@Nullable final List<Journal> journals) {
                // Update the cached copy of the journals in the adapter.
                adapter.setJournals(journals);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewJournalActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        adapter.setOnItemClickListener(new JournalListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Journal journal = adapter.getJournalAtPosition(position);
                launchUpdateJournalActivity(journal);
            }
        });

        mAlertBuilder = new AlertDialog.Builder(MainActivity.this);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a journal,
                    // delete that journal from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final int position = viewHolder.getAdapterPosition();
                        final Journal mJournal = adapter.getJournalAtPosition(position);


                        // Set the dialog title and message.
                        mAlertBuilder.setTitle("Delete a Journal");
                        mAlertBuilder.setMessage("This wil delete "+mJournal.getTitle()+" permanently");

                        // Add the dialog buttons.
                        mAlertBuilder.setPositiveButton(R.string.ok_button,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.delete_journal_preamble) + " " +
                                                        mJournal.getTitle(), Toast.LENGTH_LONG).show();
                                        // Delete the journal.
                                        mJournalViewModel.deleteJournal(mJournal);
                                    }
                                });

                        mAlertBuilder.setNegativeButton(R.string.cancel_button,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // User cancelled the dialog.
                                        adapter.notifyItemChanged(position);
                                    }
                                });
                        // Create and show the AlertDialog.
                        mAlertBuilder.show();
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);
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
        if (id == R.id.action_delete_all) {

            // Set the dialog title and message.
            mAlertBuilder.setTitle("Clear all data");
            mAlertBuilder.setMessage("This wil delete al your journals permanently");

            // Add the dialog buttons.
            mAlertBuilder.setPositiveButton(R.string.ok_button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Add a toast just for confirmation
                            Toast.makeText(getApplicationContext()
                                    , R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();
                            // Delete the existing data.
                            mJournalViewModel.deleteAll();
                        }
                    });

            mAlertBuilder.setNegativeButton(R.string.cancel_button,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // User cancelled the dialog.
                        }
                    });
            // Create and show the AlertDialog.
            mAlertBuilder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //TODO: toast for successful inset
        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            //TODO: Toast for successful update
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    private void launchUpdateJournalActivity(Journal journal) {
        Intent intent = new Intent(this, NewJournalActivity.class);
        intent.putExtra(EXTRA_DATA_ID, journal.getId());
        startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }
}
