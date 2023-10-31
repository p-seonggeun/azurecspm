package com.example.vuldetect.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.vuldetect.Config.*;
import static com.example.vuldetect.Config.AZR_LIST;

@Service
public class AzrAppService {
    public static JsonNode appServiceList(){
        List<String> getStorageAccountNames = new ArrayList<>();
        getStorageAccountNames.add(AZR_AZ);
        getStorageAccountNames.add(AZR_WEBAPP);
        getStorageAccountNames.add(AZR_LIST);

        // az storage account list 내용이 담긴 jsonNode
        JsonNode jsonNode = executeCommand(getStorageAccountNames);
        return jsonNode;
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
