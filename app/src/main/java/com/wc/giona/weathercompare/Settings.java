package com.wc.giona.weathercompare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    public String newCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final CityPreference cityObj = new CityPreference(Settings.this);
        final EditText editTextCity = (EditText) findViewById(R.id.editTextCity);
        Button confirmButton = (Button) findViewById(R.id.confirm);

        editTextCity.setText(cityObj.getCity());

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCity = editTextCity.getText().toString();
                cityObj.setCity(newCity);
                Toast.makeText(getBaseContext(), "City updated!" , Toast.LENGTH_LONG ).show();
                Intent refreshMain = new Intent(Settings.this, MainActivity.class);
                startActivity(refreshMain);
            }
        });

        ImageButton settButton = (ImageButton) findViewById(R.id.backbutton);
        settButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Settings.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

}
