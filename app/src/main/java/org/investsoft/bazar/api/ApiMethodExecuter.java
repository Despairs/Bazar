package org.investsoft.bazar.api;

import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.base.ApiResponse;
import org.investsoft.bazar.api.model.base.HttpGetRequest;
import org.investsoft.bazar.api.model.base.HttpPostRequest;
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
        if (response instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) response;
            if (apiResponse.getCode() != 0) {
                throw new ApiException(((ApiResponse) response).getMessage(), ((ApiResponse) response).getCode());
            }
        }
        return response;
    }
}
