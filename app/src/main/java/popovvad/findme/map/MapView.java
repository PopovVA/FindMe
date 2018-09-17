package popovvad.findme.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.UniversalMechanisms;

public class MapView extends AppCompatActivity implements MapContract.View, NavigationView.OnNavigationItemSelectedListener {

    private String main_user;
    private String tittle_user;

    private MapContract.Presenter mPresenter;

    private com.yandex.mapkit.mapview.MapView mapView;
    private PlacemarkMapObject mainPlacemarkMapObject;
    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(getString(R.string.yandex_map_kit_api_key));//Инициализация АПИ яндекса
        MapKitFactory.initialize(this);
        setContentView(R.layout.map_activity);
        init();
    }

    public void init() {
        mPresenter = new MapPresenter(this);
        Intent intent = getIntent();
        setMainUsername(intent.getStringExtra("main_user"));

        toolbar = findViewById(R.id.toolbar);

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
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            mPresenter.onButtonSearchWasClicked();
        } else if (id == R.id.nav_contacts) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {
            mPresenter.onButtonExitWasClicked();
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_faq) {

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
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
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
        mapView.getMap().getMapObjects().addPlacemark(mPresenter.getMapPoint(), ImageProvider.fromResource(this, R.drawable.mygeo_light_icon));
    }

    @Override
    public void moveCamera() {
        mapView.getMap().move(
                mPresenter.getCameraPosition(),
                mPresenter.getAnimation(),
                null);
    }
}
