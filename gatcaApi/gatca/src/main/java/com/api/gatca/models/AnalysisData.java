package com.api.gatca.models;


import com.api.gatca.models.enums.AnalysisType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data  // Generates getters, setters, toString, equals, and hashCode methods
@AllArgsConstructor  // Generates an all-args constructor
@NoArgsConstructor  // Generates a no-args constructor
public class AnalysisData implements Serializable {

    AnalysisType type;
    String name;
    List<String> filesPath;


}
