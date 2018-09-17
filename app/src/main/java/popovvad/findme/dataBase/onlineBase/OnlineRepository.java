package popovvad.findme.dataBase.onlineBase;

public interface OnlineRepository {
    void loadResponse(ModelDB.CompleteCallback callback);

    void setUrl(String url);

    void setRequest(String request);

    void setJson(String json);
}
