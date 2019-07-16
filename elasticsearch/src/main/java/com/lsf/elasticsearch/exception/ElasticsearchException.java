package com.lsf.elasticsearch.exception;

public class ElasticsearchException extends Exception {
    public static final String DEFAULT_ERROR_CODE = "00001";

    private String code;

    public ElasticsearchException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ElasticsearchException(String message) {
        super(message);
        this.code = DEFAULT_ERROR_CODE;
    }

    public ElasticsearchException() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
