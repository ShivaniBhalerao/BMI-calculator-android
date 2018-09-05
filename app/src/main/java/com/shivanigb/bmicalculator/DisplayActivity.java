package com.shivanigb.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    TextView tvResult, tvData1, tvData2, tvData3, tvData4,tvMsg;
    Button btnBack, btnShare, btnSave;
    SharedPreferences sp1;



    String msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
tvMsg=(TextView)findViewById(R.id.tvMsg);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvData1 = (TextView) findViewById(R.id.tvData1);
        tvData2 = (TextView) findViewById(R.id.tvData2);
        tvData3 = (TextView) findViewById(R.id.tvData3);
        tvData4 = (TextView) findViewById(R.id.tvData4);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnSave = (Button) findViewById(R.id.btnSave);
        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);


       // sp1 = getSharedPreferences("yo", MODE_PRIVATE);

        final String name = sp1.getString("name","");
        final int age = sp1.getInt("age",0);
        String phone = sp1.getString("ph","");





        //Bundle b =getIntent().getExtras();





       Intent j = getIntent();
        Bundle b =j.getExtras();
      //  final double myBmi =b.getDouble("bmi");

        final double myBmi = b.getDouble("bmi");
        tvResult.setText("Your BMI is"+myBmi);


        if (myBmi < 18.5) {
            msg="Underweight";
            tvMsg.setText(msg);
            tvData1.setTextColor(Color.RED);
        }
        else if (18.5 < myBmi && myBmi<25) {
            msg="Normal";
            tvMsg.setText(msg);
            tvData2.setTextColor(Color.RED);
        }
        else if (25 < myBmi && myBmi<30) {
            msg="Overweight";
            tvMsg.setText(msg);
            tvData3.setTextColor(Color.RED);
        }
        else if(myBmi>30)
        {
            msg="Obese";
            tvMsg.setText(msg);
            tvData4.setTextColor(Color.RED);
         }

       /* btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sp1.getString("n", "");
                 int age = sp1.getInt("a", 0);
                String phone = sp1.getString("p", "");
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                if (myBmi < 18.5) {
                    String  m = "Name:" + name + "\n" + "Age:" + age + "\n" + "Phone No.:" + phone + "\n" + "My BMI is " + myBmi + "and I am underweight";
                    i.putExtra(Intent.EXTRA_TEXT,m);
                } else if (myBmi > 18.5 && myBmi < 25) {
                    String  m = "Name:" + name + "\n" + "Age:" + age + "\n" + "Phone No.:" + phone + "\n" + "My BMI is " + myBmi + "and I am normal";
                    i.putExtra(Intent.EXTRA_TEXT,m);
                } else if (myBmi > 25 && myBmi > 30) {
                    String  m = "Name:" + name + "\n" + "Age:" + age + "\n" + "Phone No.:" + phone + "\n" + "My BMI is " + myBmi + "and I am overweight";
                    i.putExtra(Intent.EXTRA_TEXT,m);
                } else if (myBmi > 30) {
                    String  m = "Name:" + name + "\n" + "Age:" + age + "\n" + "Phone No.:" + phone + "\n" + "My BMI is " + myBmi + "and I am obese";
                    i.putExtra(Intent.EXTRA_TEXT,m);
                }
                startActivity(i);
            }
        });*/

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                sp1.getString("name",name);
                String yo="Name = "+ name +"\n Age = "+ age +"\n "+ "Your bmi is "+ myBmi +".\n So you are "+ msg +".";


                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT,"Bmi ");
                i.putExtra(Intent.EXTRA_TEXT,yo);



                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);

                boolean isIntentSafe = activities.size() > 0;

                if (isIntentSafe) {
                    startActivity(Intent.createChooser(i,"Share via"));

                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final DatabaseHandler db = new DatabaseHandler(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date = new Date();
                db.addAns(String.valueOf(myBmi), String.valueOf(date));

            }
        });
    }
}
