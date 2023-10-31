package com.example.vuldetect.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class ProjectResult {

    @Id @GeneratedValue
    @Column(name = "project_result_id")
    private Long id;

    private int score;

    @OneToMany(fetch = LAZY, mappedBy = "projectResult")
    private List<DiagnosisResult> diagnosisResults = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private int round;

    protected ProjectResult() {

    }

    public ProjectResult(Project project) {
        this.project = project;
        this.round = project.getRound();
    }

    public void addDiagnosisResult(List<DiagnosisResult> diagnosisResults) {
        this.diagnosisResults = diagnosisResults;
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            diagnosisResult.setProjectResult(this);
        }
    }

    public void calculateScore() {
        int high = 3;
        int medium = 2;
        int low = 1;
        double numerator = 0; // 분자
        double denominator = 0; // 분모
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            // 항목 당 무조건 분모에는 더해야한다
            // 취약일 경우에만 분자에 더한다
            if (diagnosisResult.getVulnerability().equals("High")) {
                denominator += high;
                if (diagnosisResult.getResult().equals("양호")) {
                    numerator += high;
                }
            }
            else if (diagnosisResult.getVulnerability().equals("Medium")) {
                denominator += medium;
                if (diagnosisResult.getResult().equals("양호")) {
                    numerator += medium;
                }
            }
            else if (diagnosisResult.getVulnerability().equals("Low")) {
                denominator += low;
                if (diagnosisResult.getResult().equals("양호")) {
                    numerator += low;
                }
            }
        }
        this.score = (int) ((numerator / denominator) * 100);
    }
}
