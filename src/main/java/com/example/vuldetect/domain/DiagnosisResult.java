package com.example.vuldetect.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.example.vuldetect.Config.*;
import static com.example.vuldetect.Config.AZR_074;
import static jakarta.persistence.FetchType.*;


@Entity
@Getter
@ToString
public class DiagnosisResult {

    @Id
    @GeneratedValue
    @Column(name = "diagnosis_result_id")
    private Long id;

    private String category;
    private String name;

    private String code;

    private String result;

    private String goodStandard;

    private String vulnerability;

    @Column
    private Long diagnosis_item_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_result_id")
    private ProjectResult projectResult;


    @Column(columnDefinition = "LONGTEXT")
    private String apiJsonNode;
    @Column(columnDefinition = "TEXT")
    private String compareJsonNode;

    @Transient
    private JSONArray apiJsonArray = new JSONArray();

    @Transient
    @Setter
    private JsonNode jsonNode;

    public void convertToApiJsonNode(JSONArray apiJsonArray) {
        if (getResult() == null) {
            this.result = "양호";
        }
        System.out.println("getResult() = " + getResult());
        if (apiJsonArray != null) {
            this.apiJsonNode = apiJsonArray.toString(4);
        } else {
            this.apiJsonNode = null;
        }
    }

    protected DiagnosisResult() {
    }

    public DiagnosisResult(DiagnosisItem diagnosisItem) {
        this.category = diagnosisItem.getCategory();
        this.name = diagnosisItem.getName();
        this.code = diagnosisItem.getCode();
        this.goodStandard = diagnosisItem.getGoodStandard();
        this.vulnerability = diagnosisItem.getVulnerability();
        this.diagnosis_item_id = diagnosisItem.getId();
    }

    public void setProjectResult(ProjectResult projectResult) {
        this.projectResult = projectResult;
    }

    public void diagnosis(JsonNode list) throws IOException, InterruptedException {
        if (this.code.equals(AZR_041)) {
            AZR_041(list);
        } else if (this.code.equals(AZR_042)) {
            AZR_042(list);
        } else if (this.code.equals(AZR_043)) {
            AZR_043(list);
        } else if (this.code.equals(AZR_044)) {
            AZR_044(list);
        } else if (this.code.equals(AZR_045)) {
            AZR_045(list);
        } else if (this.code.equals(AZR_047)) {
            AZR_047(list);
        } else if (this.code.equals(AZR_106)) {
            AZR_106(list);
        } else if (this.code.equals(AZR_107)) {
            AZR_107(list);
        } else if (this.code.equals(AZR_108)) {
            AZR_108(list);
        } else if (this.code.equals(AZR_109)) {
            AZR_109(list);
        }
    }

    public void diagnosis() {
        if (this.code.equals(AZR_023)) {
            AZR_023();
        } else if (this.code.equals(AZR_024)) {
            AZR_024();
        }
        else if (this.code.equals(AZR_025)) {
            AZR_025();
        } else if (this.code.equals(AZR_027)) {
            AZR_027();
        } else if (this.code.equals(AZR_030)) {
            AZR_030();
        } else if (this.code.equals(AZR_032)) {
            AZR_032();
        }
    }
    public void diagnosis(String subscriptionName) {
        if (this.code.equals(AZR_074)) {
            AZR_074(subscriptionName);
        } else if (this.code.equals(AZR_076)) {
            AZR_076(subscriptionName);
        } else if (this.code.equals(AZR_078)) {
            AZR_078(subscriptionName);
        } else if (this.code.equals(AZR_080)) {
            AZR_080(subscriptionName);
        } else if (this.code.equals(AZR_082)) {
            AZR_082(subscriptionName);
        }
    }


    public JSONObject jsonNodeToJSONObject(JsonNode jsonNode) {
        System.out.println("jsonNode = " + jsonNode);
        System.out.println("jsonNode = " + jsonNode.getClass());
        if (jsonNode.getClass().equals(ArrayNode.class) && jsonNode.size() == 1) {
            System.out.println("in this condition");
            for (JsonNode node : jsonNode) {
                System.out.println("node = " + node);
                JSONObject jsonObject = new JSONObject(node.toPrettyString());
                return jsonObject;
            }
        }
        JSONObject jsonObject = new JSONObject(jsonNode.toPrettyString());
        return jsonObject;
    }

    public static JsonNode getStorageAccountList() {
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
    private void AZR_023() {
        // 비교할 키 : pricingTier
        // 양호한 값 : Standard, 취약한 값 : Free
        // az security pricing show -n VirtualMachines
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_PRICING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_VIRTUALMACHINES);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_PRICINGTIER, AZR_STANDARD);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_024() {
        // 비교할 키 : pricingTier
        // 양호한 값 : Standard, 취약한 값 : Free
        // az security pricing show -n AppServices
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_PRICING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_APPSERVICES);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_PRICINGTIER, AZR_STANDARD);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_025() {
        // 비교할 키 : pricingTier
        // 양호한 값 : Standard, 취약한 값 : Free
        // az security pricing show -n SqlServers
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_PRICING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_SQLSERVERS);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_PRICINGTIER, AZR_STANDARD);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_027() {
        // 비교할 키 : pricingTier
        // 양호한 값 : Standard, 취약한 값 : Free
        // az security pricing show -n StorageAccounts
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_PRICING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_STORAGEACCOUNTS);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_PRICINGTIER, AZR_STANDARD);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_030() {
        // 비교할 키 : pricingTier
        // 양호한 값 : Standard, 취약한 값 : Free
        // az security pricing show -n KeyVaults
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_PRICING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_KEYVAULTS);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_PRICINGTIER, AZR_STANDARD);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_032() {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : false
        // az security setting show -n "MCAS"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_SECURITY);
        azureCliApi.add(AZR_SETTING);
        azureCliApi.add(AZR_SHOW);
        azureCliApi.add(AZR_DASH_NAME);
        azureCliApi.add(AZR_MCAS);
        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_041(JsonNode storageAccountList) {
        // 비교할 키 : defaultAction
        // 양호한 값 : Deny, 취약한 값 : Allow

        storageAccountList.forEach(storageAccount -> {
                    List<String> azureCliApi = new ArrayList<>();
                    azureCliApi.add(AZR_AZ);
                    azureCliApi.add(AZR_STORAGE);
                    azureCliApi.add(AZR_ACCOUNT);
                    azureCliApi.add(AZR_SHOW);
                    azureCliApi.add(AZR_DASH_NAME);
                    azureCliApi.add(storageAccount.findValue("name").asText());
                    JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
                    LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
                    diagnosisField.put(AZR_DEFAULTACTION, AZR_DENY);
                    JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
                    String result = compareAndReturn(apiJsonNode, compareJsonNode);
                    setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);
                }
        );
        // 엔티티마다 진단을 수행해야함
        // az storage account show --name <StorageAccountName>
        // 반복문이 다 돌면 JSONArray를 String으로 변환
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_042(JsonNode storageAccountList) {
        // 비교할 키 : bypass
        // 양호한 값 : AzureServices, 취약한 값 : Null

        // az storage account show --name <StorageAccountName> --query '[*].networkRuleSet'
        storageAccountList.forEach(storageAccount -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_STORAGE);
            azureCliApi.add(AZR_ACCOUNT);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_DASH_NAME);
            azureCliApi.add(storageAccount.findValue("name").asText());
            azureCliApi.add(AZR_QUERY);
            azureCliApi.add(AZR_NETWORKRULESET);
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_BYPASS, AZR_AZURESERVICES);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            System.out.println("resultOfcompareAndReturn = " + result);
            setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_043(JsonNode storageAccountList) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : false
        // az storage blob service-properties delete-policy show --account-name <StorageAccountName> --auth-mode login
        storageAccountList.forEach(storageAccount -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_STORAGE);
            azureCliApi.add(AZR_BLOB);
            azureCliApi.add(AZR_SERIVCE_PROPERTIES);
            azureCliApi.add(AZR_DELETE_POLICY);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_ACCOUNT_NAME);
            azureCliApi.add(storageAccount.findValue("name").asText());
            azureCliApi.add(AZR_AUTH_MODE);
            azureCliApi.add(AZR_LOGIN);
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_ENABLED, AZR_TRUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            System.out.println("resultOfcompareAndReturn = " + result);
            setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_044(JsonNode storageAccountList) throws IOException, InterruptedException {
        // 비교할 키 delete, read, write
        // 양호한 값 : true, 취약한 값 : false

        // az storage logging show --services b --account-name <StorageAccountName> --query blob
        storageAccountList.forEach(storageAccount -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_STORAGE);
            azureCliApi.add(AZR_LOGGING);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_SERVICE);
            azureCliApi.add(AZR_B);
            azureCliApi.add(AZR_ACCOUNT_NAME);
            azureCliApi.add(storageAccount.findValue("name").asText());
            azureCliApi.add(AZR_QUERY);
            azureCliApi.add(AZR_BLOB);
            azureCliApi.add(AZR_ACCOUNT_KEY);
            azureCliApi.add(getStorageAccountKey(storageAccount));
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_DELETE, AZR_TRUE);
            diagnosisField.put(AZR_READ, AZR_TRUE);
            diagnosisField.put(AZR_WRITE, AZR_TRUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            System.out.println("resultOfcompareAndReturn = " + result);
            setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_045(JsonNode storageAccountList) throws IOException, InterruptedException {
        // 비교할 키 delete, read, write
        // 양호한 값 : true, 취약한 값 : false
        // az storage logging show --services t --account-name <StorageAccountName> --query table
        storageAccountList.forEach(storageAccount -> {
            String storageAccountKey = getStorageAccountKey(storageAccount);
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_STORAGE);
            azureCliApi.add(AZR_LOGGING);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_SERVICE);
            azureCliApi.add(AZR_T);
            azureCliApi.add(AZR_ACCOUNT_NAME);
            azureCliApi.add(storageAccount.findValue("name").asText());
            azureCliApi.add(AZR_QUERY);
            azureCliApi.add(AZR_TABLE);
            azureCliApi.add(AZR_ACCOUNT_KEY);
            azureCliApi.add(storageAccountKey);
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_DELETE, AZR_TRUE);
            diagnosisField.put(AZR_READ, AZR_TRUE);
            diagnosisField.put(AZR_WRITE, AZR_TRUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            System.out.println("resultOfcompareAndReturn = " + result);
            setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);

        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_047(JsonNode storageAccountList) throws IOException, InterruptedException {
        // 비교할 키 : keyExpirationPeriodInDays
        // 양호한 값 : 90 미만, 취약한 값 : null
        // az storage account show -n <StorageAccountName> -- query keyPolicy
        storageAccountList.forEach(storageAccount -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_STORAGE);
            azureCliApi.add(AZR_ACCOUNT);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_N);
            azureCliApi.add(storageAccount.findValue("name").asText());
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_KEYEXPIRATIONPERIODINDAYS, 90);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            System.out.println("resultOfcompareAndReturn = " + result);
            setDgnssEntityAndisPassAndsetResult(storageAccount, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_074(String subscriptionName) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : null 또는 false
        // az monitor activity-log alert list --subscription <subscriptionName> --query "[?condition.allOf[?equals == 'Microsoft.Authorization/policyAssignments/write']]"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_MONITOR);
        azureCliApi.add(AZR_ACTIVITYLOG);
        azureCliApi.add(AZR_ALERT);
        azureCliApi.add(AZR_LIST);
        azureCliApi.add(AZR_SUBSCRIPTION);
        azureCliApi.add(subscriptionName);
        azureCliApi.add(AZR_QUERY);
        azureCliApi.add(AZR_074WRITECONDITIONS);

        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_076(String subscriptionName) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : null 또는 false
        // az monitor activity-log alert list --subscription <subscriptionName> --query "[?condition.allOf[?equals == 'Microsoft.Network/networkSecurityGroups/write']]"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_MONITOR);
        azureCliApi.add(AZR_ACTIVITYLOG);
        azureCliApi.add(AZR_ALERT);
        azureCliApi.add(AZR_LIST);
        azureCliApi.add(AZR_SUBSCRIPTION);
        azureCliApi.add(subscriptionName);
        azureCliApi.add(AZR_QUERY);
        azureCliApi.add(AZR_076WRITECONDITIONS);

        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_078(String subscriptionName) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : null 또는 false
        // az monitor activity-log alert list --subscription <subscriptionName> --query "[?condition.allOf[?equals == 'Microsoft.Network/networkSecurityGroups/securityRules/write']]"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_MONITOR);
        azureCliApi.add(AZR_ACTIVITYLOG);
        azureCliApi.add(AZR_ALERT);
        azureCliApi.add(AZR_LIST);
        azureCliApi.add(AZR_SUBSCRIPTION);
        azureCliApi.add(subscriptionName);
        azureCliApi.add(AZR_QUERY);
        azureCliApi.add(AZR_078WRITECONDITIONS);

        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_080(String subscriptionName) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : null 또는 false
        // az monitor activity-log alert list --subscription <subscriptionName> --query "[?condition.allOf[?equals == 'Microsoft.Security/securitySolutions/write']]"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_MONITOR);
        azureCliApi.add(AZR_ACTIVITYLOG);
        azureCliApi.add(AZR_ALERT);
        azureCliApi.add(AZR_LIST);
        azureCliApi.add(AZR_SUBSCRIPTION);
        azureCliApi.add(subscriptionName);
        azureCliApi.add(AZR_QUERY);
        azureCliApi.add(AZR_080WRITECONDITIONS);

        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }
    private void AZR_082(String subscriptionName) {
        // 비교할 키 : enabled
        // 양호한 값 : true, 취약한 값 : null 또는 false
        // az monitor activity-log alert list --subscription <subscriptionName> --query "[?condition.allOf[?equals == 'Microsoft.Sql/servers/firewallRules/write']]"
        List<String> azureCliApi = new ArrayList<>();
        azureCliApi.add(AZR_AZ);
        azureCliApi.add(AZR_MONITOR);
        azureCliApi.add(AZR_ACTIVITYLOG);
        azureCliApi.add(AZR_ALERT);
        azureCliApi.add(AZR_LIST);
        azureCliApi.add(AZR_SUBSCRIPTION);
        azureCliApi.add(subscriptionName);
        azureCliApi.add(AZR_QUERY);
        azureCliApi.add(AZR_082WRITECONDITIONS);

        JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
        LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
        diagnosisField.put(AZR_ENABLED, AZR_TRUE);
        JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
        String result = compareAndReturn(apiJsonNode, compareJsonNode);
        setDgnssEntityAndisPassAndsetResult(apiJsonNode.findValue("id").asText(), apiJsonNode, result);
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_106(JsonNode appServiceList) throws IOException, InterruptedException {
        // 비교할 키 : httpsOnly
        // 양호한 값 : true, 취약한 값 : false

        // az webapp list -g <ResourceGroupName>
        appServiceList.forEach(appService -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_WEBAPP);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_G);
            azureCliApi.add(appService.findValue("resourceGroup").asText());
            azureCliApi.add(AZR_DASH_NAME);
            azureCliApi.add(appService.get("name").asText());

            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_HTTPSONLY, AZR_TRUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            setDgnssEntityAndisPassAndsetResult(appService, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_107(JsonNode appServiceList) throws IOException, InterruptedException {
        // 비교할 키 : minTlsVersion
        // 양호한 값 : 1.2 , 취약한 값 : Null
        // az webapp config show --resource-group <ResourceGroupName> --name <AppServiceName>
        appServiceList.forEach(appService -> {

            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_WEBAPP);
            azureCliApi.add(AZR_CONFIG);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_RESOURCEGROUP);
            azureCliApi.add(appService.findValue("resourceGroup").asText());
            azureCliApi.add(AZR_DASH_NAME);
            azureCliApi.add(appService.get("name").asText());
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_MINTLSVERSION, "1.2");
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            setDgnssEntityAndisPassAndsetResult(appService, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_108(JsonNode appServiceList) throws IOException, InterruptedException {
        // 비교할 키 : clientCertEnabled
        // 양호한 값 : true, 취약한 값 : false

        // az webapp list -g <ResourceGroupName>
        appServiceList.forEach(appService -> {
            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_WEBAPP);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_G);
            azureCliApi.add(appService.findValue("resourceGroup").asText());
            azureCliApi.add(AZR_DASH_NAME);
            azureCliApi.add(appService.get("name").asText());
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_CLIENTCERTENABLED, AZR_TRUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            setDgnssEntityAndisPassAndsetResult(appService, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }

    private void AZR_109(JsonNode appServiceList) throws IOException, InterruptedException {
        // 비교할 키 : principalId
        // 양호한 값 : 고유 주체 ID, 취약한 값 : Null
        // 비교를 Null 값이 아니면 고유 주체 ID 값은 존재하므로
        // 비교 할 compareJsonNode를 randomValue로 지정

        // az webapp identity show --resource-group <ResourceGroupName> --name <AppServiceName>
        appServiceList.forEach(appService -> {

            List<String> azureCliApi = new ArrayList<>();
            azureCliApi.add(AZR_AZ);
            azureCliApi.add(AZR_WEBAPP);
            azureCliApi.add(AZR_SHOW);
            azureCliApi.add(AZR_G);
            azureCliApi.add(appService.findValue("resourceGroup").asText());
            azureCliApi.add(AZR_DASH_NAME);
            azureCliApi.add(appService.get("name").asText());
            JsonNode apiJsonNode = getApiJsonNode(azureCliApi);
            LinkedHashMap<String, Object> diagnosisField = new LinkedHashMap<>();
            diagnosisField.put(AZR_PRINCIPALID, AZR_RANDOMVALUE);
            JsonNode compareJsonNode = toJsonFromDiagField(diagnosisField);
            String result = compareAndReturn(apiJsonNode, compareJsonNode);
            setDgnssEntityAndisPassAndsetResult(appService, apiJsonNode, result);
        });
        convertToApiJsonNode(this.apiJsonArray);
    }


    /**
     * 각 항목을 진단하기 위해 필요한 메서드
     */


    private void setDgnssEntityAndisPassAndsetResult(JsonNode list, JsonNode apiJsonNode, String result) {
        String dgnssEntityKey;
        Boolean dgnssEntityStatus;
        int isPass;
        if (list.has("id")) {
            dgnssEntityKey = list.findValue("id").asText();
        } else {
            dgnssEntityKey = "-";
        }

        if (result.equals("양호")) {
            dgnssEntityStatus = true;
        } else {
            dgnssEntityStatus = false;
        }

        if (true) {
            isPass = 1;
        }
        addProperty(apiJsonNode, dgnssEntityKey, dgnssEntityStatus, isPass);
        if (result.equals("취약")) {
            this.result = "취약";
        }
    }
    private void setDgnssEntityAndisPassAndsetResult(String id, JsonNode apiJsonNode, String result) {
        String dgnssEntityKey;
        Boolean dgnssEntityStatus;
        int isPass;
        if (id != null) {
            dgnssEntityKey = id;
        } else {
            dgnssEntityKey = "-";
        }

        if (result.equals("양호")) {
            dgnssEntityStatus = true;
        } else {
            dgnssEntityStatus = false;
        }

        if (true) {
            isPass = 1;
        }
        addProperty(apiJsonNode, dgnssEntityKey, dgnssEntityStatus, isPass);
        if (result.equals("취약")) {
            this.result = "취약";
        }
    }

    // API 응답을 JsonNode로 변환해주는 메서드
    public JsonNode getApiJsonNode(List<String> azureCliApi) {
        try {
            // 외부 프로세스 실행
            ProcessBuilder processBuilder = new ProcessBuilder(azureCliApi);
            Process process = processBuilder.start();

            // 프로세스의 입력 스트림을 가져옴
            InputStream inputStream = process.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            System.out.println("jsonNode = " + jsonNode);

            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStorageAccountKey(JsonNode storageAccount) {
        // 엔티티마다 진단을 수행해야함
        // 스토리지 계정이름 가져오기
        List<String> getStorageAccountKey = new ArrayList<>();
        getStorageAccountKey.add(AZR_AZ);
        getStorageAccountKey.add(AZR_STORAGE);
        getStorageAccountKey.add(AZR_ACCOUNT);
        getStorageAccountKey.add(AZR_KEYS);
        getStorageAccountKey.add(AZR_LIST);
        getStorageAccountKey.add(AZR_ACCOUNT_NAME);
        getStorageAccountKey.add(storageAccount.findValue("name").toString());
        getStorageAccountKey.add(AZR_QUERY);
        getStorageAccountKey.add(AZR_0_VALUE);
        JsonNode jsonNode = executeCommand(getStorageAccountKey);
        return jsonNode.toString();
    }


    // 진단 항목의 키, 값을 JsonNode로 변환해주는 메서드
    public JsonNode toJsonFromDiagField(LinkedHashMap<String, Object> diagnosisField) {
        // 진단 할 키, 값을 Json 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(diagnosisField);
        return jsonNode;
    }

    public String compareAndReturn(JsonNode apiJsonNode, JsonNode compareJsonNode) {
        // API 응답 Json 객체와 양호 기준 Json 객체를 비교하여 출력
        this.compareJsonNode = compareJsonNode.toPrettyString();
        System.out.println("compareAndReturnApiJsonNode = " + apiJsonNode.toPrettyString());
        System.out.println("compareJsonNode = " + compareJsonNode.toPrettyString());

        if (apiJsonNode.getClass().equals(ArrayNode.class) && apiJsonNode.size() == 1) {
            apiJsonNode = apiJsonNode.get(0);
        }

        // Null인 경우 취약한 항목들을 걸러줌
        // AZR_047, AZR_109
        if (getCode().equals(AZR_047)) {
            if (apiJsonNode.has(AZR_KEYPOLICY)) {
                if (apiJsonNode.get(AZR_KEYPOLICY).has(AZR_KEYEXPIRATIONPERIODINDAYS)) {
                    if (apiJsonNode.get(AZR_KEYPOLICY).get(AZR_KEYEXPIRATIONPERIODINDAYS).intValue() <= compareJsonNode.get(AZR_KEYEXPIRATIONPERIODINDAYS).intValue()) {
                        return "양호";
                    }
                }
            }
            return "취약";
        }

        if (getCode().equals(AZR_109)) {
            if (apiJsonNode.get(AZR_IDENTITY).has(AZR_PRINCIPALID)) {
                return "양호";
            }
            return "취약";
        }


        // 비교할 키 값이 명확한 경우
        for (Iterator<String> it = compareJsonNode.fieldNames(); it.hasNext(); ) {
            String compareKey = it.next();
            if (apiJsonNode.has(compareKey)) {
                if (apiJsonNode.findValue(compareKey).equals(compareJsonNode.findValue(compareKey))) {
                    return "양호";
                } else {
                    return "취약";
                }
            } else {
                return "취약";
            }
        }
        return "해당없음";
    }

    public void addProperty(JsonNode apiJsonNode, String dgnssEntityKey, Boolean dgnssEntityStatus, int isPass) {
        JSONObject jsonObject = new JSONObject();
        if (!(apiJsonNode.isNull())) {
            jsonObject = jsonNodeToJSONObject(apiJsonNode);
        }
        jsonObject.put(DGNSS_ENTITY_KEY, dgnssEntityKey);
        jsonObject.put(DGNSS_ENTITY_STATUS, dgnssEntityStatus);
        jsonObject.put(IS_PASS, isPass);
        this.apiJsonArray.put(jsonObject);
    }
}

