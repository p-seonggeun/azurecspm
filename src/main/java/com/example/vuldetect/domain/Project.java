package com.example.vuldetect.domain;

import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "compliance_id")
    private Compliance compliance;

    private int round = 0;

    protected Project() {
    }

    public Project(String name, User user, Compliance compliance) {
        this.name = name;
        this.user = user;
        this.compliance = compliance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void diagnosisCounter() {
        round++;
    }
}