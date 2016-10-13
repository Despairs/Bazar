package org.investsoft.bazar.api;

import android.content.Context;

import org.investsoft.bazar.R;
import org.investsoft.bazar.api.model.HttpRequest;
import org.investsoft.bazar.api.model.base.ApiException;
import org.investsoft.bazar.api.model.base.ApiResponse;
import org.investsoft.bazar.utils.ApplicationLoader;
import org.investsoft.bazar.utils.HttpClient;
import org.investsoft.bazar.utils.JsonHelper;
import org.investsoft.bazar.utils.StringUtils;

/**
 * Created by Despairs on 15.01.16.
 */
public class ApiMethodExecuter<Request, Response> {

    private final Context ctx;

    public ApiMethodExecuter() {
        this.ctx = ApplicationLoader.applicationContext;
    }

    protected Response execute(Request request, Response response) throws ApiException {
        if (!HttpClient.isInternetConnected()) {
            throw new ApiException("Нет подключения к интернету");
        }
        String url = null;
        String jsonReq = null;
        HttpRequest annotation = request.getClass().getAnnotation(HttpRequest.class);
        if (annotation == null) {
            throw new ApiException("Current method doesn't contains @HttpRequest annotation");
        }
        switch (annotation.requestType()) {
            case GET:
                url = getUrl(annotation.methodId(), request);
                break;
            case POST:
            case PUT:
                jsonReq = JsonHelper.toJson(request);
                url = getUrl(annotation.methodId());
                break;
        }
        String jsonResp = new HttpClient(url).sendRequest(jsonReq, annotation.requestType());
        response = (Response) JsonHelper.parseJson(jsonResp, response.getClass());
        if (response instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) response;
            if (apiResponse.getCode() != 0) {
                throw new ApiException(((ApiResponse) response).getMessage(), ((ApiResponse) response).getCode());
            }
        }
        return response;
    }

    private String getUrl(int methodId) {
        return ctx.getString(R.string.api_url) + ctx.getString(methodId);
    }

    private String getUrl(int methodId, Request request) {
        return getUrl(methodId) + StringUtils.getHttpGetRequest(request);
    }

}
