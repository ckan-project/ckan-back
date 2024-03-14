package com.hanyang.dataportal.dataset.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum Type {
    csv, xls, xlsx, pdf, json, docx;
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Type findByType(String type) {
        return Stream.of(Type.values())
                .filter(o -> o.toString().equals(type))
                .findFirst()
                .orElse(null);
    }
}
