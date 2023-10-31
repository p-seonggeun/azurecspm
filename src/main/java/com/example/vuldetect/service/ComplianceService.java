package com.example.vuldetect.service;

import com.example.vuldetect.domain.Compliance;
import com.example.vuldetect.repository.ComplianceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ComplianceService {

    private final ComplianceRepository complianceRepository;
    private final DiagnosisItemService diagnosisItemService;

    @PostConstruct
    public void initCompliance() {
        Compliance compliance = new Compliance("AZURE CIS BENCHMARK", "CIS BENCHMARK 기준에 따른 AZURE 클라우드 취약점 컴플라이언스");
        diagnosisItemService.initDiagnosisItem();
        compliance.addDiagnosisItem(diagnosisItemService.findAll());
        complianceRepository.save(compliance);
    }
}
