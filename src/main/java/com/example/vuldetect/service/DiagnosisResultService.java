package com.example.vuldetect.service;

import com.example.vuldetect.azr.AzrAppService;
import com.example.vuldetect.azr.AzrLoggingService;
import com.example.vuldetect.azr.AzrStorageService;
import com.example.vuldetect.domain.DiagnosisItem;
import com.example.vuldetect.domain.DiagnosisResult;
import com.example.vuldetect.domain.ProjectResult;
import com.example.vuldetect.dto.DiagnosisItemDTO;
import com.example.vuldetect.dto.DiagnosisResultDTO;
import com.example.vuldetect.repository.DiagnosisItemRepository;
import com.example.vuldetect.repository.DiagnosisResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.vuldetect.Config.*;
import static com.example.vuldetect.Config.AZR_NAME;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisResultService {

    private final DiagnosisResultRepository diagnosisResultRepository;
    private final DiagnosisItemRepository diagnosisItemRepository;

    public List<DiagnosisResult> findAllDiagnosisResult() {
        List<DiagnosisResult> diagnosisResults = diagnosisResultRepository.findAll();
        return diagnosisResults;
    }

    public List<DiagnosisResult> findAllDiagnosisResultByProjectResultId(Long projectResultId) {
        List<DiagnosisResult> findDiagnosisResults = diagnosisResultRepository.findAllByProjectResultId(projectResultId);
        return findDiagnosisResults;
    }

    public void setJsonNodeDiagnosisResult(List<DiagnosisResult> diagnosisResults) throws JsonProcessingException {
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            String apiJsonNode = diagnosisResult.getApiJsonNode();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(apiJsonNode);
            diagnosisResult.setJsonNode(jsonNode);
        }
    }

    public List<DiagnosisResultDTO> getDiagnosisResultDTO(List<DiagnosisResult> diagnosisResults) {
        List<DiagnosisResultDTO> diagnosisResultDTOs = new ArrayList<>();
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            for (JsonNode jsonNode : diagnosisResult.getJsonNode()) {
                DiagnosisResultDTO diagnosisResultDTO = new DiagnosisResultDTO(
                        diagnosisResult.getName(),
                        diagnosisResult.getCode(),
                        diagnosisResult.getGoodStandard(),
                        diagnosisResult.getProjectResult(),
                        diagnosisResult.getDiagnosis_item_id(),
                        diagnosisResult.getVulnerability(),
                        jsonNode.get("dgnssEntityKey").asText(),
                        jsonNode.get("dgnssEntityStatus").asText(),
                        jsonNode.get("isPass").asText(),
                        jsonNode);
                if ((jsonNode.get("dgnssEntityStatus").asText() == "true")) {
                    diagnosisResultDTO.setResult("양호");
                } else {
                    diagnosisResultDTO.setResult("취약");
                }
                diagnosisResultDTOs.add(diagnosisResultDTO);
            }
        }
        return diagnosisResultDTOs;
    }

    public List<DiagnosisItemDTO> getDiagnosisItemDTO(List<DiagnosisResult> diagnosisResults) {
        List<DiagnosisItemDTO> diagnosisItemDTOs = new ArrayList<>();
        for (DiagnosisResult diagnosisResult : diagnosisResults) {
            DiagnosisItemDTO diagnosisItemDTO = new DiagnosisItemDTO(
                    diagnosisResult.getName(),
                    diagnosisResult.getProjectResult().getRound(),
                    diagnosisResult.getResult(),
                    diagnosisResult.getVulnerability()
            );
            diagnosisItemDTOs.add(diagnosisItemDTO);
        }
        return diagnosisItemDTOs;
    }

    public List<Integer> getResultsCount(ProjectResult projectResult) {
        List<Integer> resultsCount = new ArrayList<>();

        List<DiagnosisResult> passedResults = diagnosisResultRepository.findPassedResultByProjectResult(projectResult);
        List<DiagnosisResult> vulHresults = diagnosisResultRepository.findVulnerabilityIsHAndVulByProjectResult(projectResult);
        List<DiagnosisResult> vulMresults = diagnosisResultRepository.findVulnerabilityIsMAndVulByProjectResult(projectResult);
        List<DiagnosisResult> vulLresults = diagnosisResultRepository.findVulnerabilityIsLAndVulByProjectResult(projectResult);

        resultsCount.add(passedResults.size());
        resultsCount.add(vulLresults.size());
        resultsCount.add(vulHresults.size());
        resultsCount.add(vulMresults.size());

        return resultsCount;
    }
    public List<Integer> getCategoryPassedCount(ProjectResult projectResult) {
        List<Integer> categoryCount = new ArrayList<>();

        List<DiagnosisResult> securitySolutions = diagnosisResultRepository.findPassedCategoryByProjectResult(projectResult, "Security Solutions");
        List<DiagnosisResult> etc = diagnosisResultRepository.findPassedCategoryByProjectResult(projectResult, "ETC");
        List<DiagnosisResult> logging = diagnosisResultRepository.findPassedCategoryByProjectResult(projectResult, "Logging");
        List<DiagnosisResult> accessControl = diagnosisResultRepository.findPassedCategoryByProjectResult(projectResult, "Access Control");
        List<DiagnosisResult> monitoring = diagnosisResultRepository.findPassedCategoryByProjectResult(projectResult, "Monitoring");

        categoryCount.add(securitySolutions.size());
        categoryCount.add(etc.size());
        categoryCount.add(logging.size());
        categoryCount.add(accessControl.size());
        categoryCount.add(monitoring.size());

        return categoryCount;
    }
    public List<Integer> getCategoryCount(ProjectResult projectResult) {
        List<Integer> categoryCount = new ArrayList<>();

        List<DiagnosisResult> securitySolutions = diagnosisResultRepository.findCategoryByProjectResult(projectResult, "Security Solutions");
        List<DiagnosisResult> etc = diagnosisResultRepository.findCategoryByProjectResult(projectResult, "ETC");
        List<DiagnosisResult> logging = diagnosisResultRepository.findCategoryByProjectResult(projectResult, "Logging");
        List<DiagnosisResult> accessControl = diagnosisResultRepository.findCategoryByProjectResult(projectResult, "Access Control");
        List<DiagnosisResult> monitoring = diagnosisResultRepository.findCategoryByProjectResult(projectResult, "Monitoring");

        categoryCount.add(securitySolutions.size());
        categoryCount.add(etc.size());
        categoryCount.add(logging.size());
        categoryCount.add(accessControl.size());
        categoryCount.add(monitoring.size());

        return categoryCount;
    }
    public List<Integer> getCategoryFailCount(ProjectResult projectResult) {
        List<Integer> categoryCount = new ArrayList<>();

        List<DiagnosisResult> securitySolutions = diagnosisResultRepository.findFailCategoryByProjectResult(projectResult, "Security Solutions");
        List<DiagnosisResult> etc = diagnosisResultRepository.findFailCategoryByProjectResult(projectResult, "ETC");
        List<DiagnosisResult> logging = diagnosisResultRepository.findFailCategoryByProjectResult(projectResult, "Logging");
        List<DiagnosisResult> accessControl = diagnosisResultRepository.findFailCategoryByProjectResult(projectResult, "Access Control");
        List<DiagnosisResult> monitoring = diagnosisResultRepository.findFailCategoryByProjectResult(projectResult, "Monitoring");

        categoryCount.add(securitySolutions.size());
        categoryCount.add(etc.size());
        categoryCount.add(logging.size());
        categoryCount.add(accessControl.size());
        categoryCount.add(monitoring.size());

        return categoryCount;
    }

    public void deleteDiagnosisResults(Long projectResultId) {
        List<DiagnosisResult> findResults = diagnosisResultRepository.findAllByProjectResultId(projectResultId);
        diagnosisResultRepository.delete(findResults);
    }

    public void diagnosis() throws IOException, InterruptedException {
        List<DiagnosisItem> findDiagnosisItems = diagnosisItemRepository.findAll();
        for (DiagnosisItem findDiagnosisItem : findDiagnosisItems) {
            if (findDiagnosisItem.getCode().equals(AZR_041)
                    || findDiagnosisItem.getCode().equals(AZR_042)
                    || findDiagnosisItem.getCode().equals(AZR_043)
                    || findDiagnosisItem.getCode().equals(AZR_044)
                    || findDiagnosisItem.getCode().equals(AZR_045)
                    || findDiagnosisItem.getCode().equals(AZR_047)) {
                JsonNode storageAccountList = AzrStorageService.getStorageAccountList();
                DiagnosisResult diagnosisResult = new DiagnosisResult(findDiagnosisItem);
                diagnosisResult.diagnosis(storageAccountList);
                diagnosisResultRepository.save(diagnosisResult);
            } else if (findDiagnosisItem.getCode().equals(AZR_074)
                            || findDiagnosisItem.getCode().equals(AZR_076)
                            || findDiagnosisItem.getCode().equals(AZR_078)
                            || findDiagnosisItem.getCode().equals(AZR_080)
                            || findDiagnosisItem.getCode().equals(AZR_082)) {
                String subscriptionName = AzrLoggingService.accountShow();
                DiagnosisResult diagnosisResult = new DiagnosisResult(findDiagnosisItem);
                diagnosisResult.diagnosis(subscriptionName);
                diagnosisResultRepository.save(diagnosisResult);
            }
            else if (findDiagnosisItem.getCode().equals(AZR_106)
                    || findDiagnosisItem.getCode().equals(AZR_107)
                    || findDiagnosisItem.getCode().equals(AZR_108)
                    || findDiagnosisItem.getCode().equals(AZR_109)) {
                JsonNode webAppList = AzrAppService.appServiceList();
                DiagnosisResult diagnosisResult = new DiagnosisResult(findDiagnosisItem);
                diagnosisResult.diagnosis(webAppList);
                diagnosisResultRepository.save(diagnosisResult);
            } else {
                DiagnosisResult diagnosisResult = new DiagnosisResult(findDiagnosisItem);
                diagnosisResult.diagnosis();
                diagnosisResultRepository.save(diagnosisResult);
            }
        }
    }

    public List<String> getStorageAccountList() throws IOException, InterruptedException {
        // 엔티티마다 진단을 수행해야함
        // 스토리지 계정이름 가져오기
        List<String> getStorageAccountNames = new ArrayList<>();
        getStorageAccountNames.add(AZR_AZ);
        getStorageAccountNames.add(AZR_STORAGE);
        getStorageAccountNames.add(AZR_ACCOUNT);
        getStorageAccountNames.add(AZR_LIST);
        getStorageAccountNames.add(AZR_QUERY);
        getStorageAccountNames.add(AZR_NAME);
        JsonNode apiJsonNode = getApiJsonNode(getStorageAccountNames);
        System.out.println("apiJsonNode = " + apiJsonNode);
        List<String> storageAccountList = new ArrayList<>();
        for (JsonNode jsonNode : apiJsonNode) {
            String toStringValue = jsonNode.asText();
            storageAccountList.add(toStringValue);
        }
        System.out.println("storageAccountList = " + storageAccountList);
        System.out.println("storageAccountList = " + storageAccountList.getClass());
        return storageAccountList;
    }

    public JsonNode getApiJsonNode(List<String> azureCliApi) throws IOException, InterruptedException {
        System.out.println("command = " + azureCliApi);
        ProcessBuilder processBuilder = new ProcessBuilder(azureCliApi);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Process exited with code: " + exitCode);
        System.out.println("output = " + output);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(output.toString());
        System.out.println("apiJsonNode = " + jsonNode);
        System.out.println("objectMapper = " + objectMapper);
        return jsonNode;
    }
}
