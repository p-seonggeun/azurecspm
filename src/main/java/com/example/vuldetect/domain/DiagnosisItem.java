package com.example.vuldetect.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class DiagnosisItem {

    @Id @GeneratedValue
    @Column(name = "diagnosis_item_id")
    private Long id;

    private String name;

    private String category;

    private String code;

    private String goodStandard;

    private String vulnerability;

    private String itemDescription;

    protected DiagnosisItem() {
    }

    public DiagnosisItem(String name, String category, String code, String goodStandard, String vulnerability, String itemDescription) {
        this.name = name;
        this.category = category;
        this.code = code;
        this.goodStandard = goodStandard;
        this.vulnerability = vulnerability;
        this.itemDescription = itemDescription;
    }
}
