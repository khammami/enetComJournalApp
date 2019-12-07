package com.example.journalapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.journalapp.R;
import com.example.journalapp.db.Converters;

import java.util.Calendar;
import java.util.Date;

import static com.example.journalapp.ui.MainActivity.EXTRA_DATA_ID;
import static com.example.journalapp.ui.MainActivity.EXTRA_DATA_UPDATE_CONTENT;
import static com.example.journalapp.ui.MainActivity.EXTRA_DATA_UPDATE_DATE;
import static com.example.journalapp.ui.MainActivity.EXTRA_DATA_UPDATE_TITLE;

public class NewJournalActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TITLE = "REPLY_TITLE";
    public static final String EXTRA_REPLY_DATE = "REPLY_DATE";
    public static final String EXTRA_REPLY_CONTENT = "REPLY_CONTENT";
    public static final String EXTRA_REPLY_ID = "REPLY_ID";

    private EditText mEditTitleView;
    private EditText mEditContentView;
    private TextView mDateView;

    private Date mJournalDate;
    private Bundle extras;

    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);

        mEditTitleView = findViewById(R.id.journalTitleEditView);
        mEditContentView = findViewById(R.id.journalDescEditView);
        mDateView = findViewById(R.id.dateTextView);

        mJournalDate = Calendar.getInstance().getTime();
        mDateView.setText(mJournalDate.toString());

        mId = -1 ;

        extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String title = extras.getString(EXTRA_DATA_UPDATE_TITLE, "");
            String content = extras.getString(EXTRA_DATA_UPDATE_CONTENT, "");
            long date = extras.getLong(EXTRA_DATA_UPDATE_DATE, 0);

            if (!title.isEmpty()) {
                mEditTitleView.setText(title);
            }

            if (!content.isEmpty()) {
                mEditContentView.setText(content);
            }

            if (date != 0) {
                mJournalDate = Converters.fromTimestamp(date);
                mDateView.setText(mJournalDate.toString());
            }
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
                // No word was entered, set the result accordingly.
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                // Get the new word that the user entered.
                String title = mEditTitleView.getText().toString();
                String content = mEditContentView.getText().toString();
                // Put the new word in the extras for the reply Intent.
                Log.d("title",title);
                Log.d("content",content);
                replyIntent.putExtra(EXTRA_REPLY_TITLE, title);
                replyIntent.putExtra(EXTRA_REPLY_CONTENT, content);
                replyIntent.putExtra(EXTRA_REPLY_DATE, Converters.dateToTimestamp(mJournalDate));
                if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                    mId = extras.getInt(EXTRA_DATA_ID, -1);
                }
                replyIntent.putExtra(EXTRA_REPLY_ID, mId);
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
        mJournalDate = date;
        mDateView.setText(mJournalDate.toString());
    }
}
