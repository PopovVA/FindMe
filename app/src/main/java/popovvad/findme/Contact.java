package popovvad.findme;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Contact extends AppCompatActivity implements View.OnClickListener {

    private Double latitude;
    private Double longitude;
    private String main_user;
    private String user;
    private String city;
    private String country;
    private Geocoder geocoder;
    private Button buttonNewContact;
    private Button buttonFind;
    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        initUI();
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        Intent intent = getIntent();
        main_user = intent.getStringExtra("main_user");
        user = intent.getStringExtra("user");
        latitude = intent.getDoubleExtra("latitude", 0.00);
        longitude = intent.getDoubleExtra("longitude", 0.00);


        TextView contactName = findViewById(R.id.contactName);
        contactName.setText(user);

        TextView city = findViewById(R.id.city);
        city.setText(getCity());

        TextView country = findViewById(R.id.country);
        country.setText(getCountry());

        TextView coordinates = findViewById(R.id.coordinates);
        coordinates.setText("Ширина : " + latitude + " Долгота : " + longitude);

        buttonNewContact = (Button) findViewById(R.id.buttonNewContact);
        buttonNewContact.setOnClickListener(this);

        buttonFind = (Button) findViewById(R.id.buttonFind);
        buttonFind.setOnClickListener(this);
    }

    private String getCity() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                city = returnedAddress.getAdminArea().toString();
            } else {
                city = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            city = "";
        }
        return city;
    }

    private String getCountry() {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                country = returnedAddress.getCountryName().toString();
            } else {
                country = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            country = "";
        }
        return country;
    }

    @Override
    public void onClick(View v) {

        progressDialog = new ProgressDialog(v.getContext(), R.style.MyTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.show();

        switch (v.getId()) {
            case R.id.buttonNewContact:
                progressDialog.hide();
                break;
            case R.id.buttonFind:
                Intent intentLog = new Intent(this, MapActivity.class);
                intentLog.putExtra("user", user);
                intentLog.putExtra("tittle_user", user);
                intentLog.putExtra("main_user", main_user);
                intentLog.putExtra("longitude", longitude);
                intentLog.putExtra("latitude", latitude);
                progressDialog.hide();
                startActivity(intentLog);
                break;
            default:
                progressDialog.hide();
                break;

        }
    }
}
