package com.example.vuldetect.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.vuldetect.Config.*;

@Service
public class AzrLoggingService {
    public static JsonNode alertServiceList(String subscriptionName) {
        List<String> getAlertServiceList = new ArrayList<>();
        getAlertServiceList.add(AZR_AZ);
        getAlertServiceList.add(AZR_MONITOR);
        getAlertServiceList.add(AZR_ACTIVITYLOG);
        getAlertServiceList.add(AZR_ALERT);
        getAlertServiceList.add(AZR_LIST);
        getAlertServiceList.add(AZR_SUBSCRIPTION);
        getAlertServiceList.add(subscriptionName);
        getAlertServiceList.add(AZR_QUERY);
        getAlertServiceList.add(AZR_NAME);
        JsonNode jsonNode = executeCommand(getAlertServiceList);
        return jsonNode;
    }

    public static String accountShow() {
        List<String> getAccountShow = new ArrayList<>();
        getAccountShow.add(AZR_AZ);
        getAccountShow.add(AZR_ACCOUNT);
        getAccountShow.add(AZR_SHOW);

        JsonNode jsonNode = executeCommand(getAccountShow);
        return jsonNode.get("name").asText();
    }

    public static JsonNode executeCommand(List<String> command) {
        try {
            // 외부 프로세스 실행
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // 프로세스의 입력 스트림을 가져옴
            InputStream inputStream = process.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);

            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
