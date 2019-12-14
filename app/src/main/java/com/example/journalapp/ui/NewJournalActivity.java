package com.example.journalapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.journalapp.R;
import com.example.journalapp.model.Journal;
import com.example.journalapp.viewmodel.NewJournalViewModel;

import java.util.Calendar;
import java.util.Date;

import static com.example.journalapp.ui.MainActivity.EXTRA_DATA_ID;

public class NewJournalActivity extends AppCompatActivity {

    private EditText mEditTitleView;
    private EditText mEditContentView;
    private TextView mDateView;

    private int mId;
    private Journal mJournal;
    private NewJournalViewModel mNewJournalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        mEditTitleView = findViewById(R.id.journalTitleEditView);
        mEditContentView = findViewById(R.id.journalDescEditView);
        mDateView = findViewById(R.id.dateTextView);


        mId = -1 ;

        mNewJournalViewModel = ViewModelProviders.of(this).get(NewJournalViewModel.class);

        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            mId = extras.getInt(EXTRA_DATA_ID, -1);
        }

        if (mId == -1){
            mJournal = new Journal();
            mJournal.setJournalDate(Calendar.getInstance().getTime());
            populateUI(mJournal);
        }else {
            mNewJournalViewModel.getJournalById(mId).observe(this, new Observer<Journal>() {
                @Override
                public void onChanged(Journal journal) {
                    mJournal = journal;
                    populateUI(journal);
                }
            });
        }
    }

    private void populateUI(Journal journal) {
        if (journal != null){
            mEditTitleView.setText(journal.getTitle());
            mEditContentView.setText(journal.getContent());
            mDateView.setText(journal.getJournalDate().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            // Create a new Intent for the reply.
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditTitleView.getText()) &&
                    TextUtils.isEmpty(mEditContentView.getText())) {
                // No journal was entered, set the result accordingly.
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                // Get the new journal that the user entered.
                mJournal.setTitle(mEditTitleView.getText().toString());
                mJournal.setContent(mEditContentView.getText().toString());

                if (mId == -1 ){
                    mNewJournalViewModel.insert(mJournal);
                }else {
                    mNewJournalViewModel.update(mJournal);
                }
                // Set the result status to indicate success.
                setResult(RESULT_OK, replyIntent);
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.datepicker));
    }

    public void setJournalDate(Date date){
        mJournal.setJournalDate(date);
        mDateView.setText(date.toString());
    }
}
