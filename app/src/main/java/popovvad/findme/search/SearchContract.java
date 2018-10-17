package popovvad.findme.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public interface SearchContract {

    interface View {

        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        void showQueryList(String[] strings);

        Context getContextView();

        void startActivity(Intent intent);

        Activity getActivity();

        String getMainUser();

        void setMainUser();

    }

    interface Presenter {

        void onButtonSearchWasClicked(TextView textView);

        void onQuerySubmitWasClicked(String string);

        void onItemListWasClicked();
    }

}
