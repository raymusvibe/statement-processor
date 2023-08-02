package com.rabobank.statement.processor.enums;

public enum FileType {
    XML,
    CSV,
    OTHER;

    public static FileType fromMimeType(String mimeType) {
        switch (mimeType.toLowerCase()) {
            case "text/csv", "application/csv":
                return CSV;
            case "text/xml", "application/xml":
                return XML;
            default:
                return OTHER;
        }
    }
}
