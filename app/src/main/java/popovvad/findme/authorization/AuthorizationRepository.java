package popovvad.findme.authorization;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthorizationRepository implements AuthorizationContract.Repository {

    private String url;
    private String json;
    private String request;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public void setJson(String json) {
        this.json = json;
    }


    public String getUrl() {
        return url;
    }

    public String getJson() {
        return json;
    }

    interface CompleteCallback {
        void onComplete(String response);
    }

    public void loadResponse(CompleteCallback callback) {
        CreateRequest createRequest = new CreateRequest(callback);
        createRequest.execute();
    }

    private class CreateRequest extends AsyncTask<Void, Void, String> {

        private final CompleteCallback callback;

        public CreateRequest(CompleteCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                return ServerQuery();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String response) {
            if (callback != null) {
                callback.onComplete(response);
            }
        }

        private String ServerQuery() throws IOException {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), getJson());
            if (request == "post") {
                Request request = new Request.Builder()
                        .url(getUrl())
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body() != null ? response.body().string() : "Ошибка соединения";
            } else if (request == "put") {
                Request request = new Request.Builder()
                        .url(getUrl())
                        .put(body)
                        .build();
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                return response.body() != null ? response.body().string() : "Ошибка соединения";

            } else if (request == "get") {
                Request request = new Request.Builder()
                        .url(getUrl())
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                return response.body() != null ? response.body().string() : "Ошибка соединения";
            }
            return "Ошибка соединения";
        }

    }
}
