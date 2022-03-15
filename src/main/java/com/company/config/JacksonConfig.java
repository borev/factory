package com.company.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.ZoneOffset;
import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;

@Configuration(proxyBeanMethods = false)
public class JacksonConfig implements BeanPostProcessor {

  @Primary
  @Bean
  Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    return getBuilder();
  }

  private Jackson2ObjectMapperBuilder getBuilder() {
    return json()
        .findModulesViaServiceLoader(true)
        .defaultViewInclusion(true)
        .failOnUnknownProperties(false)
        .indentOutput(true)
        .featuresToEnable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
        .featuresToEnable(ACCEPT_SINGLE_VALUE_AS_ARRAY)
        .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS, WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .dateFormat(StdDateFormat.instance)
        .timeZone(TimeZone.getTimeZone(ZoneOffset.UTC))
        .locale(Locale.US)
        .modulesToInstall(new JavaTimeModule());
  }
}