package com.example.phil.project1_phillip_heroux;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button2;
    Intent intent = null;
    int result;
    String number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize setup and button pointer/actionlistener
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(DialListener);

    }
    //method for second activity intent return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        intent = data;
        result = resultCode;
        //if number is valid, set button text to number and number to number variable
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            button2.setText("Call "+data.getStringExtra("number"));
            number = data.getStringExtra("number");
            Log.i("onActivityResult", "onActivityResult: "+number);

        }
        else
        {
            button2.setText("Call "+data.getStringExtra("number"));
        }
    }
    //onClick of first button define intent and start new activity with that intent
    //onClick method declared/connected to button in xml file
    public void sendMessage(View view){
        intent = new Intent(this, NumberEntryActivity.class);

        startActivityForResult(intent, 1);
    }

    //handling onClick of second button for ACTION_DIAL
    public View.OnClickListener DialListener = new View.OnClickListener(){

        public void onClick(View v){
            //if number is valid, set intent action to ACTION_DIAL and start new activity with intent
            if(result == RESULT_OK && number != null)
            {
                intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                Log.i("onClick", "result ok"+number);

                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
            //if number isn't a valid format, show toast message with error
            else if(result == RESULT_CANCELED && intent != null)
            {
                Log.i("onClick", "Result cancelled"+number);

                Context context = getApplicationContext();
                String errorText = getString(R.string.errorMessage)+intent.getStringExtra("number");
                int duration = Toast.LENGTH_LONG;
                Toast toastMessage = Toast.makeText(context, errorText, duration);
                toastMessage.show();
            }
            //if user clicks on button before navigating to second activity, show different toast message
            else
            {
                Log.i("onClick", "no number");

                Context context = getApplicationContext();
                String errorText = "No number entered, nothing to do";
                int duration = Toast.LENGTH_LONG;
                Toast toastMessage = Toast.makeText(context, errorText, duration);
                toastMessage.show();
            }
        }
    };
}


