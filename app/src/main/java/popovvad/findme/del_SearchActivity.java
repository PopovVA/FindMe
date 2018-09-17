package popovvad.findme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import popovvad.findme.supportLibrary.UniversalMechanisms;

public class del_SearchActivity extends AppCompatActivity {

    private String response;
    private Double latitude;
    private Double longitude;

    private Button newContact_button;
    private Button onMap_button;

    private String query;

    private String main_user;

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_top);
        mCtx = this;

        Intent intent = getIntent();
        main_user = intent.getStringExtra("main_user");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Activity activity = this;

        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView textView = findViewById(R.id.noticeSearch);
                textView.setVisibility(View.INVISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                //вызовется при нажатии на лупу на клавиатуре

                        if (Looper.myLooper() == null)
                        {
                            Looper.prepare();
                        }
                        String json = "{\"username\" " + ": \"" + query + "\"" + "}";
                del_ServerInteraction serverInteraction = new del_ServerInteraction("http://popovvad.ru/UserCoordinates.php",
                               json,"post");
                        serverInteraction.execute();
                        try {
                            response = serverInteraction.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                if (response == null) {
                    UniversalMechanisms.showShortMessage(searchView.getContext(), "Контакт не найден...");
                    return false;
                }
                else if(response.equals("failed_username")) {
                    UniversalMechanisms.showShortMessage(searchView.getContext(), "Контакт не найден...");
                    return false;
                }
                else {
                    UniversalMechanisms.showShortMessage(searchView.getContext(), "Выполняется поиск...");
                }

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String[] subStr;
                String delimetr = ";";
                subStr = response.split(delimetr);
                latitude = Double.parseDouble(subStr[0]);
                longitude = Double.parseDouble(subStr[1]);
                // находим список
                String[] names = {"Найден : " + query};
                ListView lvMain = (ListView) findViewById(R.id.lvMain);


                // создаем адаптер
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(lvMain.getContext(),
                        R.layout.search_item, R.id.label, names);

                // присваиваем адаптер списку
                lvMain.setAdapter(adapter);

                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent intent = new Intent(mCtx, Contact.class);
                        intent.putExtra("user", query);
                        intent.putExtra("main_user", main_user);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }
                });

                        UniversalMechanisms.hideKeyboard(activity);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //вызовется при изменении ведённого текста
                return false;
            }
        });



    }


//    public void OnButtonClicked(View v) {
//        switch (v.getId()) {
//            case R.id.login_button:
//                Intent intentLog = new Intent(this, MapActivity.class);
//                intentLog.putExtra("user", query);
//                startActivity(intentLog);
//                break;
//            case R.id.newContact:
//                UniversalMechanisms.showMessage(this.getApplicationContext(), "В разработке...");
//                break;
//            default:
//                break;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.search_menu_scrolling, menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
