package popovvad.findme.supportLibrary;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class GeoPosition implements LocationListener {

    private double latitude;
    private double longitude;

    static Location imHere;

    // --Commented out by Inspection (04.07.2018 14:24):private static final int PERMISSION_REQUEST = 1;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void SetUpLocationListener(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10,
                this); // здесь можно указать другие более подходящие вам параметры

        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (imHere != null) {
            setLatitude(imHere.getLatitude());
            setLongitude(imHere.getLongitude());
            if (getLongitude() > 0 & getLatitude() > 0) return;
        } else
            imHere = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (imHere != null) {
            setLatitude(imHere.getLatitude());
            setLongitude(imHere.getLongitude());
            if (getLongitude() > 0 & getLatitude() > 0) return;
        } else {
            //Если не определил координаты тогда по дефолту устанавливаю Москву
            setLatitude(55.75370903771494);
            setLongitude(37.61981338262558);
        }
}

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
