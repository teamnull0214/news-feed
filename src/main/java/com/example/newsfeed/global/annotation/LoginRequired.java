package com.example.newsfeed.global.annotation;

import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginRequired {
}
