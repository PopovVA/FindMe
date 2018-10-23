package popovvad.findme.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.GeoPosition;
import popovvad.findme.supportLibrary.UniversalMechanisms;

public class MapMainView extends AppCompatActivity implements MapContract.View, NavigationView.OnNavigationItemSelectedListener {

    private String main_user;
    private String tittle_user;

    private MapView mapView;
    private MapContract.Presenter mPresenter;

    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("0f8d1d87-de3b-4c52-823d-94463b58dae7");//Инициализация АПИ яндекса
        MapKitFactory.initialize(this);
        setContentView(R.layout.map_activity);
        init();
    }

    private void init() {
        mPresenter = new MapPresenter(this);
        setMainUsername(getIntent().getStringExtra("main_user"));
        tittle_user = getIntent().getStringExtra("tittle_user");

        toolbar = findViewById(R.id.toolbar);
        setTittleToolbar("Местоположение: " + getMainUsername());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonFabWasClicked();
            }
        });

        mapView = findViewById(R.id.mapview);
        if (tittle_user.equals("")) {
            GeoPosition geoPosition = new GeoPosition();
            geoPosition.SetUpLocationListener(this);
            mapView.getMap().move(
                    new CameraPosition(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
            mapView.getMap().getMapObjects().clear();
            mapView.getMap().getMapObjects().addPlacemark(new Point(geoPosition.getLatitude(), geoPosition.getLongitude()), ImageProvider.fromResource(this, R.drawable.ic_geo));
        } else {
            setTittleToolbar(getIntent().getStringExtra("tittle_user"));
            mapView.getMap().move(
                    new CameraPosition(new Point(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0)), 15.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
            mapView.getMap().getMapObjects().clear();
            mapView.getMap().getMapObjects().addPlacemark(new Point(getIntent().getDoubleExtra("latitude", 0.00), getIntent().getDoubleExtra("longitude", 0.00)), ImageProvider.fromResource(this, R.drawable.ic_geo));
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            mPresenter.onButtonSearchWasClicked();
        } else if (id == R.id.nav_contacts) {
            mPresenter.onButtonContactsWasClicked();
        } else if (id == R.id.nav_settings) {
            mPresenter.onButtonSettingsWasClicked();
        } else if (id == R.id.nav_exit) {
            mPresenter.onButtonExitWasClicked();
        } else if (id == R.id.nav_send) {
            mPresenter.onButtonSendWasClicked();
        } else if (id == R.id.nav_faq) {
            mPresenter.onButtonFaqWasClicked();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public String getMainUsername() {
        return main_user;
    }

    @Override
    public void setMainUsername(String main_user) {
        this.main_user = main_user;
    }

    @Override
    public void setTittleUsername(String tittle_user) {
        this.tittle_user = tittle_user;
    }

    @Override
    public String getTittleUsername() {
        return tittle_user;
    }

    @Override
    public void setTittleToolbar(String tittleToolbar) {
        toolbar.setTitle(tittleToolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setMapPoint() {
        mapView.getMap().getMapObjects().clear();
        mapView.getMap().getMapObjects().addPlacemark(mPresenter.getMapPoint(), ImageProvider.fromResource(this, R.drawable.ic_geo));
    }

    @Override
    public void moveCamera() {
        mapView.getMap().move(
                mPresenter.getCameraPosition(),
                mPresenter.getAnimation(),
                null);
    }

    @Override
    public void startSomeActivity(Intent intent) {
        startActivity(intent);
    }
}
