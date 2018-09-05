package com.shivanigb.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvPersonalDetails;
    EditText etName,etPhone,etAge;
    RadioGroup rgGender;
    Button btnRegister;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPersonalDetails=(TextView)findViewById(R.id.tvPersonalDetails);
        etName=(EditText)findViewById(R.id.etName);
        etAge=(EditText)findViewById(R.id.etAge);
        etPhone=(EditText)findViewById(R.id.etPhone);
        rgGender=(RadioGroup)findViewById(R.id.rgGender);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        sp1= getSharedPreferences("MyP1",MODE_PRIVATE);


String name=sp1.getString("name","");
        int age=sp1.getInt("age",0);
        String phone=sp1.getString("ph","");

        if(!name.equals(""))
        {
            if(!(age==0))
            {
                if(!phone.equals(("")))
                {
                    Intent i=new Intent(MainActivity.this,DataActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }

        else {

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    int age = Integer.parseInt(etAge.getText().toString());
                    String phone = etPhone.getText().toString();


                    if (name.length() == 0) {
                        etName.setError("Name is empty");
                        etName.requestFocus();
                        return;
                    }
                    if (age < 1 || age > 200) {
                        etAge.setError("Invalid age");
                        etAge.requestFocus();
                        return;
                    }
                    if (phone.length() != 10) {
                        etPhone.setError("Invalid Phone Number");
                        etPhone.requestFocus();
                        return;
                    }
                    int id = rgGender.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    String g = rb.getText().toString();

                  /*  SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("n", name);
                    editor.putInt("a", age);
                    editor.putString("p", phone);
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, DataActivity.class);
                  //  i.putExtra("n", name);

                    startActivity(i);

finish();*/

                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("name", name);
                    editor.putInt("age", age);
                    editor.putString("ph", phone);

                    editor.commit();


                    Toast.makeText(getApplicationContext(), "Your data is successfully saved", Toast.LENGTH_SHORT).show();


                    try {
                        Intent i = new Intent(MainActivity.this, DataActivity.class);
                        i.putExtra("name", name);
                        startActivity(i);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



               /* String msg=name+"--"+age+"-- "+phone+"-- "+g;
                Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
*/

               /* Toast.makeText(MainActivity.this, "data saved", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etPhone.setText("");
                etAge.setText(""); */
                }
            });

        }
    }

}
