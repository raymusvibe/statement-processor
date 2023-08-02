package com.rabobank.statement.processor.enums;

public enum FileType {
    XML,
    CSV,
    OTHER;

    public static FileType fromMimeType(String mimeType) {
        switch (mimeType.toLowerCase()) {
            case "text/csv":
            case "application/csv":
                return CSV;
            case "text/xml":
            case "application/xml":
                return XML;
            default:
                return OTHER;
        }
    }
}
