package com.matheusfelixr.exemplojasper.domain.enums;

public enum ReportType {

    PDF("PDF"),
    CSV("CSV"),
    HTML("HTML"),
    xml("XML");


    private String description;

    private ReportType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
