package popovvad.findme;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Вадим on 21.03.2018.
 */

public class ServerInteraction extends AsyncTask<String, Integer, String> {

    private String url;
    private String json;
    //private Context context;
    private String request;

    ServerInteraction(String url, String json, String request) {
        this.url = url;
        this.json = json;
        this.request = request;
    }


    private void setJson(String json) {
        this.json = json;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }


    public String getUrl() {
        return url;
    }

    public String getJson() {
        return json;
    }

    @Override
    protected String doInBackground(String... arg) {
        try {
            return ServerQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        //
    }

    public String ServerQuery() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = getJson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        if (request == "post") {
            Request request = new Request.Builder()
                    .url(getUrl())
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } else if (request == "put") {
            Request request = new Request.Builder()
                    .url(getUrl())
                    .put(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        return null;
    }
}
