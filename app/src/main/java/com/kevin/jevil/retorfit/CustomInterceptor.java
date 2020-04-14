package com.kevin.jevil.retorfit;

import com.kevin.jevil.devl.Devil;
import com.kevin.jevil.devl.models.DevilRequest;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) {
        Request request = chain.request();
        long startNs = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        if (response != null) {
            long contentLength = Objects.requireNonNull(response.body()).contentLength();
            String bodySize = contentLength != -1L ? contentLength + "-byte" : "unknown-length";
            Devil.pr(new DevilRequest(response.code(), tookMs, request.url().toString(), bodySize, (response.receivedResponseAtMillis() - response.sentRequestAtMillis())));
        }
        return response;

    }
}
