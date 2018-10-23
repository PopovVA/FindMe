package popovvad.findme.contact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.UniversalMechanisms;

public class ContactView extends AppCompatActivity implements ContactContract.View {


    private ProgressDialog progressDialog;

    private ContactContract.Presenter mPresenter;

    private TextView contactName;
    private TextView city;
    private TextView country;
    private TextView coordinates;
    private Button buttonNewContact;
    private Button buttonFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        init();
    }

    private void init() {
        contactName = findViewById(R.id.contactName);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        coordinates = findViewById(R.id.coordinates);
        buttonNewContact = (Button) findViewById(R.id.buttonNewContact);
        mPresenter = new ContactPresenter(this);
        buttonNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonNewContactWasClicked();
            }
        });
        buttonFind = (Button) findViewById(R.id.buttonFind);
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonFindWasClicked();
            }
        });
    }

    @Override
    public void showToast(String message) {
        UniversalMechanisms.showMessage(getContextView(), message);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, "Загрузка", getString(R.string.please_wait));
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public Context getContextView() {
        return this.getApplicationContext();
    }

    @Override
    public String getMainUser() {
        return getIntent().getStringExtra("user");
    }

    @Override
    public double getLatitude() {
        return getIntent().getDoubleExtra("latitude", 0);
    }

    @Override
    public double getLongitude() {
        return getIntent().getDoubleExtra("longitude", 0);
    }

    @Override
    public void setContactName(String mContactName) {
        contactName.setText(mContactName);
    }

    @Override
    public void setCity(String mCity) {
        city.setText(mCity);
    }

    @Override
    public void setCounty(String mCountry) {
        country.setText(mCountry);
    }

    @Override
    public void setCoordinates(String mCoordinates) {
        coordinates.setText(mCoordinates);
    }

    @Override
    public void startSomeActivity(Intent intent) {
        startActivity(intent);
    }
}
