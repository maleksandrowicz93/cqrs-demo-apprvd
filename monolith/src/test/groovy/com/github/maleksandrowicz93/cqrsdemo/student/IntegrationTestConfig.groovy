package com.github.maleksandrowicz93.cqrsdemo.student

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@TestConfiguration
class IntegrationTestConfig {

    @Bean
    Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, getLocalDateJsonSerializer())
                .registerTypeAdapter(LocalDate.class, getLocalDateAdapter())
                .create();
    }

    JsonSerializer<LocalDate> getLocalDateJsonSerializer() {
        return (localDate, type, jsonSerializationContext) -> {
            String format = localDate.format(DateTimeFormatter.ISO_DATE);
            return new JsonPrimitive(format);
        };
    }

    TypeAdapter<LocalDate> getLocalDateAdapter() {
        return new TypeAdapter<LocalDate>() {
            @Override
            void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
                jsonWriter.value(localDate.toString());
            }

            @Override
            LocalDate read(JsonReader jsonReader) throws IOException {
                return LocalDate.parse(jsonReader.nextString());
            }
        }
    }
}
