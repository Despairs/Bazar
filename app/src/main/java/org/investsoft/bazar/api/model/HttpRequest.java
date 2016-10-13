package org.investsoft.bazar.api.model;

import org.investsoft.bazar.api.model.HttpRequestType;

import java.lang.annotation.*;

/**
 * Created by EKovtunenko on 06.10.2016.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpRequest {
    int methodId();

    HttpRequestType requestType();

}