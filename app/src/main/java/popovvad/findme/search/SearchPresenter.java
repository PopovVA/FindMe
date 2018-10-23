package popovvad.findme.search;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import popovvad.findme.R;
import popovvad.findme.contact.ContactView;
import popovvad.findme.dataBase.onlineBase.ModelDB;
import popovvad.findme.dataBase.onlineBase.OnlineRepository;
import popovvad.findme.supportLibrary.UniversalMechanisms;

public class SearchPresenter implements SearchContract.Presenter {

    //Компоненты MVP приложения
    private SearchContract.View mView;
    private OnlineRepository mRepository;
    private Resources resources;

    private Double latitude;
    private Double longitude;
    private String query;

    SearchPresenter(SearchMainView mView) {
        this.mView = mView;
        this.mRepository = new ModelDB();
        this.resources = mView.getContextView().getResources();
    }

    @Override
    public void onButtonSearchWasClicked(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onQuerySubmitWasClicked(final String query) {
        mView.showProgressDialog();
        UniversalMechanisms.checkLooper();
        mRepository.setJson(getJsonQuery(query));
        mRepository.setRequest("post");
        mRepository.setUrl(resources.getString(R.string.url_server) + resources.getString(R.string.url_UserCoordinates));
        mRepository.loadResponse(new ModelDB.CompleteCallback() {
            @Override
            public void onComplete(String response) {
                switch (response) {
                    case "failed_username":
                        mView.hideProgressDialog();
                        mView.showToast("Контакт не найден...");
                        break;
                    case "Ошибка соединения":
                        mView.hideProgressDialog();
                        mView.showToast("Ошибка поиска...");
                        break;
                    default:
                        mView.showToast("Выполняется поиск...");
                        break;
                }
                String[] subStr;
                String delimetr = ";";
                subStr = response.split(delimetr);
                latitude = Double.parseDouble(subStr[0]);
                longitude = Double.parseDouble(subStr[1]);
                // находим список
                String[] names = {"Найден : " + query};
                setQuery(query);
                mView.showQueryList(names);
            }
        });
        mView.hideProgressDialog();
        UniversalMechanisms.hideKeyboard(mView.getActivity());
    }

    @Override
    public void onItemListWasClicked() {
        Intent intent = new Intent(mView.getContextView(), ContactView.class);
        intent.putExtra("user", getQuery());
        intent.putExtra("main_user", mView.getMainUser());
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        mView.startActivity(intent);
    }

    private String getJsonQuery(String query) {
        return "{\"username\" " + ": \"" + query + "\"" + "}";
    }

    private void setQuery(String string) {
        this.query = string;
    }

    private String getQuery() {
        return this.query;
    }
}
