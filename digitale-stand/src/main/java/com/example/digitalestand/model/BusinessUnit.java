package com.example.digitalestand.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "BusinessUnit")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessUnit {
    @Id
    private String id;
    private String name;
    private String description;
    private BusinessUnitLabel businessUnitLabel;
    private String parentId;
    private List<BusinessUnit> children = new ArrayList<>();

    // Constructors
    public BusinessUnit() {
    }

    public BusinessUnit(String id, String name, String description, BusinessUnitLabel businessUnitLabel, String parentId, List<BusinessUnit> children) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.businessUnitLabel = businessUnitLabel;
        this.parentId = parentId;
        this.children = children;
    }

    // Method to add a child business unit
    public void addChild(BusinessUnit child) {
        this.children.add(child);
    }
}
