package popovvad.findme.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import popovvad.findme.R;
import popovvad.findme.supportLibrary.UniversalMechanisms;


public class SearchMainView extends AppCompatActivity implements SearchContract.View {

    private ProgressDialog progressDialog;
    private SearchPresenter mPresenter;

    private ListView lvMain;
    private String main_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_top);
        init();
    }

    private void init() {
        mPresenter = new SearchPresenter(this);
        Intent intent = getIntent();
        main_user = intent.getStringExtra("main_user");
        setMainUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvMain = (ListView) findViewById(R.id.lvMain);
        setSupportActionBar(toolbar);

        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.noticeSearch);
                mPresenter.onButtonSearchWasClicked(textView);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                mPresenter.onQuerySubmitWasClicked(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //вызовется при изменении ведённого текста
                return false;
            }
        });

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mPresenter.onItemListWasClicked();
            }
        });
    }

    @Override
    public void showToast(String message) {
        UniversalMechanisms.showMessage(getContextView(), message);
    }

    @Override
    public void showQueryList(String[] names) {
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lvMain.getContext(),
                R.layout.search_item, R.id.label, names);
        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getMainUser() {
        return this.main_user;
    }

    @Override
    public void setMainUser() {
        Intent intent = getIntent();
        main_user = intent.getStringExtra("main_user");
    }

}
