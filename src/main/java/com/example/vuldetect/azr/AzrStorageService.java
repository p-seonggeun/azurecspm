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
public class AzrStorageService {
     public static JsonNode getStorageAccountList(){
        List<String> getStorageAccountNames = new ArrayList<>();
        getStorageAccountNames.add(AZR_AZ);
        getStorageAccountNames.add(AZR_STORAGE);
        getStorageAccountNames.add(AZR_ACCOUNT);
        getStorageAccountNames.add(AZR_LIST);

        // az storage account list 내용이 담긴 jsonNode
        JsonNode jsonNode = executeCommand(getStorageAccountNames);
        return jsonNode;
    }

    // CLI 실행해주는 메서드
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
