package popovvad.findme;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

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
    private Context context;

    ServerInteraction(String url, String json, Context context){
        this.context = context;
        setUrl(url);
        setJson(json);
    }

    private void setJson(String json) {
        this.json = json;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public String getJson(){
        return this.json;
    }

    @Override
    protected String doInBackground(String... arg) {
        try {
            PostQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

    }



    public String PostQuery() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String json = getJson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(getUrl())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        return "123";
    }


}
