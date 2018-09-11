package popovvad.findme.authorization;

import android.content.Context;

public interface AuthorizationContract {

    interface View {
        void showToast(String message);

        void showProgressDialog();

        void hideProgressDialog();

        Context getContextView();

        String getUsername();

        String getPassword();
    }

    interface Presenter {
        void onButtonLoginWasClicked();

        void onButtonRegWasClicked();

        void onDestroy();
    }

    interface Repository {
        void loadResponse(AuthorizationRepository.CompleteCallback callback);

        void setUrl(String url);

        void setRequest(String request);

        void setJson(String json);

    }
}
