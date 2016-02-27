package org.investsoft.bazar.api;

import org.investsoft.bazar.api.model.ApiException;
import org.investsoft.bazar.api.model.get.HttpGetRequest;
import org.investsoft.bazar.api.model.post.HttpPostRequest;
import org.investsoft.bazar.utils.HttpClient;
import org.investsoft.bazar.utils.JsonHelper;

/**
 * Created by Despairs on 15.01.16.
 */
public abstract class ApiMethodExecuter<Request, Response> {

    private final String url;

    public ApiMethodExecuter(String url) {
        this.url = url;
    }

    protected abstract String getMethodPath();

    protected Response execute(Request request, Response response) throws ApiException {
        if (!HttpClient.isInternetConnected()) {
            throw new ApiException("Нет подключения к интернету");
        }
        HttpClient client;
        String jsonResp;
        if (request instanceof HttpGetRequest) {
            client = new HttpClient(url + getMethodPath() + ((HttpGetRequest) request).getUrlParameters());
            jsonResp = client.sendGetRequest();
        } else {
            String jsonReq = JsonHelper.toJson(request);
            client = new HttpClient(url + getMethodPath());
            jsonResp = request instanceof HttpPostRequest ? client.sendPostRequest(jsonReq) : client.sendPutRequest(jsonReq);
        }
        response = (Response) JsonHelper.parseJson(jsonResp, response.getClass());
        return response;
    }
}
