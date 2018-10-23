package popovvad.findme.contact;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import popovvad.findme.map.MapMainView;

public class ContactPresenter implements ContactContract.Presenter {

    //Компоненты MVP приложения
    private ContactView mView;

    ContactPresenter(ContactView mView) {
        this.mView = mView;
        mView.setCity(getCity());
        mView.setContactName(mView.getMainUser());
        mView.setCounty(getCountry());
        mView.setCoordinates(getCoordinates());
    }

    private String getCoordinates() {
        return "Ширина : " + mView.getLatitude() + " Долгота : " + mView.getLongitude();
    }

    private String getCity() {
        String city;
        Geocoder geocoder = new Geocoder(mView.getContextView(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(mView.getLatitude(), mView.getLongitude(), 1);

            if (addresses != null & addresses.size() > 0) {
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
        String country;
        Geocoder geocoder = new Geocoder(mView.getContextView(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(mView.getLatitude(), mView.getLongitude(), 1);

            if (addresses != null & addresses.size() > 0) {
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
    public void onButtonFindWasClicked() {
        Intent intent = new Intent(mView.getContextView(), MapMainView.class);
        intent.putExtra("tittle_user", "Местоположение: " + mView.getMainUser());
        intent.putExtra("longitude", mView.getLongitude());
        intent.putExtra("latitude", mView.getLatitude());
        mView.startSomeActivity(intent);
    }

    @Override
    public void onButtonNewContactWasClicked() {

    }
}
