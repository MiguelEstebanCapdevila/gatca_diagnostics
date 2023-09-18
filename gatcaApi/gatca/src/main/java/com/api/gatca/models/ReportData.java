package com.api.gatca.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Generates getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor  // Generates an all-args constructor
@NoArgsConstructor  // Generates a no-args constructor
public class ReportData {

    private String title;
    private String logoPath;
    private String legalDataParagraph;
}
