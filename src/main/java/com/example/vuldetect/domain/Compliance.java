package com.example.vuldetect.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Compliance {
    @Id @GeneratedValue
    @Column(name = "compliance_id")
    private Long id;

    @Column(name = "compliance_name")
    private String name;

    private String description;

    @OneToMany
    private List<DiagnosisItem> diagnosisItems = new ArrayList<>();

    protected Compliance() {

    }

    public Compliance(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addDiagnosisItem(List<DiagnosisItem> diagnosisItems) {
        this.diagnosisItems = diagnosisItems;
    }
}
