package com.pichincha.demo.retriable.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

@Slf4j
public class ApiRetryListener extends RetryListenerSupport {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("ApiRetryListener.open");
        return super.open(context, callback);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) { //código para tratar el error
        log.info("Reintento #{}", context.getRetryCount());
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("ApiRetryListener.close");
        log.info("ApiRetryListener.onError isExhausted {}", isExhausted(context));
        log.info("ApiRetryListener.onError isClosed {}", isClosed(context));
        log.info("ApiRetryListener.onError isRecovered {}", isRecovered(context));
    }


    private boolean isExhausted(RetryContext context) {
        return context.hasAttribute(RetryContext.EXHAUSTED);
    }

    private boolean isClosed(RetryContext context) {
        return context.hasAttribute(RetryContext.CLOSED);
    }

    private boolean isRecovered(RetryContext context) {
        return context.hasAttribute(RetryContext.RECOVERED);
    }

}