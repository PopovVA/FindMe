package popovvad.findme.search;

import android.content.Context;
import android.content.Intent;

public interface SearchContract {

    interface View {

        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        void startActivity(Intent intent);

    }

    interface Presenter {

        void onButtonSearchWasClicked();
    }

}
