package com.shivanigb.bmicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class DataActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    TextView tvLoc,tvTemp,tvWelcomeName, tvHeight, tvFeet, tvInches, tvWeight;
    Spinner spnFeet, spnInches;
    EditText etWeight;
    Button btnCalculate, btnHistory;
    SharedPreferences sp1;

    GoogleApiClient gac;

    private LocationListener Location_Listener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        tvTemp=(TextView)findViewById(R.id.tvTemp);
        tvLoc=(TextView)findViewById(R.id.tvLoc);
        tvWelcomeName = (TextView) findViewById(R.id.tvWelcomeName);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvFeet = (TextView) findViewById(R.id.tvFeet);
        tvInches = (TextView) findViewById(R.id.tvInches);
        spnFeet = (Spinner) findViewById(R.id.spnFeet);
        spnInches = (Spinner) findViewById(R.id.spnInches);
        etWeight = (EditText) findViewById(R.id.etWeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnHistory = (Button) findViewById(R.id.btnHistory);

        sp1=getSharedPreferences("MyP1",MODE_PRIVATE);
        String n = sp1.getString("name","");
        tvWelcomeName.setText("Welcome  " + n);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this);
        builder.addOnConnectionFailedListener(this);
        gac = builder.build();



        final Integer feet[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};


        ArrayAdapter<Integer> adapterFeet = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, feet);

        spnFeet.setAdapter(adapterFeet);

        final Integer inches[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayAdapter<Integer> adapterInches = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, inches);


        spnInches.setAdapter(adapterInches);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fp = spnFeet.getSelectedItemPosition();
                int f = feet[fp];

                int ip = spnInches.getSelectedItemPosition();
                int i = inches[ip];

                if (etWeight.length() == 0) {
                    etWeight.setError("Weight is empty");
                    etWeight.requestFocus();
                    return;
                }

                int w = Integer.parseInt(etWeight.getText().toString());

                double meters = (double) (f * 0.3 + i * 0.025);

                double bmi = w / (meters * meters);

                String msg = "bmi=" + bmi;
Bundle b=new Bundle();
                b.putDouble("bmi",bmi);
                Intent j = new Intent(DataActivity.this, DisplayActivity.class);
               //Bundle b =getIntent().getExtras();
               // j.putExtra("bmi", bmi);
                j.putExtras(b);


                startActivity(j);
                //   Toast.makeText(DataActivity.this,msg, Toast.LENGTH_SHORT).show();

            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(DataActivity.this, ViewActivity.class);
                startActivity(n);
            }
        });


    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.about) {
            Toast.makeText(this, "App developed by Shivani", Toast.LENGTH_SHORT).show();

        }
        if(item.getItemId()==R.id.website){
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http:"+"http://kamalsir.com"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (gac != null) {
            gac.connect();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (gac != null)
            gac.disconnect();


    }

    @Override
    public void onConnected(Bundle bundle) {

        Location loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if (loc != null) {
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> al = g.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);

                Address add = al.get(0);

                String msg = add.getLocality();

                Task1 t1 = new Task1();
                t1.execute("http://api.openweathermap.org/data/2.5" + "/weather?units=metric&q=" + msg
                        + "&appid=c6e315d09197cec231495138183954bd");


                tvLoc.setText(msg + " : ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "check gps", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        gac.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d("DB3214", "Location Not Found");

    }

    class Task1 extends AsyncTask<String, Void, Double>
    {
        double temp;
        @Override
        protected Double doInBackground(String... strings) {
            String line="",json="";
            try {
                URL u=new URL(strings[0]);
                HttpURLConnection c=(HttpURLConnection) u.openConnection();
                c.connect();

                InputStream is=c.getInputStream();
                InputStreamReader isr= new InputStreamReader(is);
                BufferedReader br= new BufferedReader(isr);
                while( (line=br.readLine())!= null)
                {
                    json=json +line+"\n";

                }
                if(json!=null)
                {
                    JSONObject j=new JSONObject(json);
                    JSONObject q= j.getJSONObject("main");
                    temp =q.getDouble("temp");

                }
            }
            catch (Exception e)
            {
                Toast.makeText(DataActivity.this, "" + e, Toast.LENGTH_SHORT).show();

            }

            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvTemp.setText("Temp="+aDouble);
        }
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();
    }

}

