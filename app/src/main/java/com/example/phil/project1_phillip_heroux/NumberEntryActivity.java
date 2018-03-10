package com.example.phil.project1_phillip_heroux;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberEntryActivity extends AppCompatActivity {

    EditText editField;
    Intent intent;
    String number;
    Pattern numPattern;
    Matcher match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_entry);

        //create pointer to widget
        editField = (EditText)findViewById(R.id.editText);

        //set new actionlistener for edittext widget
        editField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //if "Done" key is pressed parse string and use matcher to compare format to pattern
                if(i == EditorInfo.IME_ACTION_DONE){

                    intent = getIntent();
                    number = editField.getText().toString();
                    number = number.replaceAll("^\\s+|\\s+$", "");
                    numPattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
                    match = numPattern.matcher(number);

                    //if format is correct, add intent extra and RESULT_OK
                    if(match.find())
                    {
                        Log.i("onCreate", "pattern match");
                        intent.putExtra("number", number);
                        setResult(RESULT_OK, intent);

                    }
                    //if format isnt correct, return number in extra and RESULT_CANCELED
                    else
                    {
                        Log.i("onCreate", "damn");
                        intent.putExtra("number", number);
                        setResult(RESULT_CANCELED, intent);
                    }
                    Log.i("OnCreate", "onEditorAction: editorworking"+number);
                    //kill activity to return to first activity
                    finish();

                    return true;
                }
                return false;
            }
        });

    }
    //actionlistener for return key on android
    public void onBackPressed() {

        editField = (EditText)findViewById(R.id.editText);

        intent = getIntent();
        number = editField.getText().toString();
        number = number.replaceAll("^\\s+|\\s+$", "");
        numPattern = Pattern.compile("\\(\\d{3}\\)\\s\\d{3}-\\d{4}");
        match = numPattern.matcher(number);

        //if format is correct, add extra and result_ok
        if(match.find())
        {
            Log.i("onCreate", "pattern match");
            intent.putExtra("number", number);
            setResult(RESULT_OK, intent);

        }
        //if format isnt correct, add extra and result_canceled
        else
        {
            Log.i("onCreate", "damn");
            intent.putExtra("number", number);
            setResult(RESULT_CANCELED, intent);
        }
        Log.i("OnCreate", "onEditorAction: editorworking"+number);
        //kill activity to return to first activity
        finish();

    }

}
