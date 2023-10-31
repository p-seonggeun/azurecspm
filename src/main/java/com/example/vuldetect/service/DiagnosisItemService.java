package com.example.vuldetect.service;

import com.example.vuldetect.Config;
import com.example.vuldetect.domain.DiagnosisItem;
import com.example.vuldetect.repository.DiagnosisItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.vuldetect.Config.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisItemService {

    private final DiagnosisItemRepository diagnosisItemRepository;

    public List<DiagnosisItem> findAll() {
        return diagnosisItemRepository.findAll();
    }

    public void initDiagnosisItem() {
        List<DiagnosisItem> diagnosisItemList = new ArrayList<>();
        diagnosisItemList.add(new DiagnosisItem("서버용 MS Defender On 설정 확인", "Security Solutions", AZR_023, "\"pricingTier\" : \"Standard\"면 양호", "High",
                "서버용 Microsoft Defender를 설정하면 서버에 대한 위협 탐지 기능이 활성화되어 클라우드용 Microsoft Defender에서 위협 인텔리전스, 이상 탐지 및 동작 분석 기능을 제공합니다."));

        diagnosisItemList.add(new DiagnosisItem("앱 서비스용 MS Defender On 설정 확인", "Security Solutions", AZR_024, "\"pricingTier\" : \"Standard\"면 양호", "High",
                "앱 서비스용 Microsoft Defender를 설정하면 앱 서비스에 대한 위협 탐지 기능이 활성화되어 클라우드용 Microsoft Defender에서 위협 인텔리전스, 이상 탐지 및 동작 분석 기능을 제공합니다."));

        diagnosisItemList.add(new DiagnosisItem("Azure SQL 데이터베이스 MS Defender On 설정 확인", "Security Solutions", AZR_025, "\"pricingTier\" : \"Standard\"면 양호", "High",
                "Azure SQL 데이터베이스용 Microsoft Defender를 설정하면 Azure SQL 데이터베이스 서버에 대한 위협 탐지 기능이 활성화되어 클라우드용 Microsoft Defender에서 위협 인텔리전스, 이상 탐지 및 동작 분석 기능을 제공합니다."));

        diagnosisItemList.add(new DiagnosisItem("스토리지용 MS Defender On 설정 확인", "Security Solutions", AZR_027, "\"pricingTier\" : \"Standard\"면 양호","High",
                "스토리지용 MS Defender를 설정하면 스토리지에 대한 위협 탐지가 활성화되어 클라우드용 MS Defender에서 위협 인텔리전스, 이상 탐지 및 동작 분석을 제공합니다."));

        diagnosisItemList.add(new DiagnosisItem("키 볼트용 MS Defender On 설정 확인", "Security Solutions", AZR_030, "\"pricingTier\" : \"Standard\"면 양호","Medium",
                "Key Vault용 MS Defender를 설정하면 Key Vault에 대한 위협 탐지가 활성화되어 클라우드용 MS Defender에서 위협 인텔리전스, 이상 탐지 및 동작 분석을 제공합니다."));

        diagnosisItemList.add(new DiagnosisItem("클라우드 앱용 MS Defender 설정 확인", "Security Solutions", AZR_032, "\"enabled\" : \"true\"면 \"양호\"", "High",
                "이 설정을 사용하면 MCAS(Microsoft Defender for Cloud Apps)와 Microsoft Defender for Cloud를 통합 할 수 있습니다."));

        diagnosisItemList.add(new DiagnosisItem("스토리지 계정 내 기본 네트워크 액세스 제한 설정", "Access Control", AZR_041, "\"defaultAction\" : \"Deny\"면 양호", "High",
                "스토리지 계정이 모든 네트워크의 클라이언트 연결을 허용하므로 기본 네트워크 액세스를 제한하면 새로운 보안 계층을 제공하는데 도움이 됩니다.\n" +
                "선택한 네트워크에 대한 액세스를 제한하려면 기본 작업를 변경해야합니다."));

        diagnosisItemList.add(new DiagnosisItem("스토리지 계정 내 서비스 액세스 제한 설정", "Access Control", AZR_042, "\"bypass\" : \"AzureServices\"면 양호", "High","스토리지 계정과 상호 작용하는 일부 MS 서비스는 네트워크 규칙을 통해 액세스 권한을 부여할 수 없는 네트워크에서 작동합니다.\n" +
                "이러한 유형의 서비스가 올바르게 작동하려면 신뢰할 수 있는 MS 서비스 집합이 네트워크 규칙을 무시할 수 있도록 합니다.\n" +
                "그런 다음 이러한 서비스는 강력한 인증을 사용하여 스토리지 계정에 액세스합니다."));

        diagnosisItemList.add(new DiagnosisItem("스토리지 내 일시 삭제(Soft Delete) 기능 활성화 확인", "ETC", AZR_043, "\"enabled\" : \"true\"면 양호", "High", "Azure 스토리지 blob(개체)에는 ePHI, Financial, secret 또는 personal과 같은 데이터가 포함되어 있습니다.\n" +
                "애플리케이션이나 다른 스토리지 계정 사용자가 실수로 수정 또는 삭제하면 데이터가 손실되거나 데이터를 사용할 수 없게 됩니다.\n" +
                "소프트 삭제 구성을 활성화하여 Azure 스토리지를 복구할 수 있도록 하는 것을 권고합니다.\n" +
                "이는 blob 또는 blob 스냅샷이 삭제될 때 데이터를 저장하고 복구하기 위한 것입니다."));

        diagnosisItemList.add(new DiagnosisItem("Blob 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정", "Logging", AZR_044, "\"delete\" : \"true\", \"read\" : \"true\", \"write\" : \"true\"면 양호", "Medium", "스토리지 Blob 서비스는 클라우드에서 확장 가능하고 비용 효율적인 목표 스토리지를 제공합니다.\n" +
                "스토리지 로깅은 서버측에서 발생하며 성공/실패한 요청에 대한 세부정보를 스토리지 계정에 기록할 수 있습니다.\n" +
                "이러한 로그를 통해 사용자는 blobs(개체)에 대한 읽기, 쓰기 및 삭제 작업의 세부 정보를 볼 수 있습니다."));

        diagnosisItemList.add(new DiagnosisItem("테이블 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정", "Logging", AZR_045, "\"delete\" : \"true\", \"read\" : \"true\", \"write\" : \"true\"면 양호", "Medium", "Storage 테이블 스토리지는 스키마가 없는 디자인 형태의 키/속성 저장소를 제공하는 NoSQL 데이터 구조로 클라우드에 저장하는 서비스입니다.\n" +
                "스토리지 로깅은 서버측에서 발생하며 성공/실패한 요청에 대한 세부정보를 스토리지 계정에 기록할 수 있습니다.\n" +
                "이러한 로그를 통해 사용자는 테이블에 대한 읽기, 쓰기 및 삭제 작업의 세부 정보를 볼 수 있습니다."));

        diagnosisItemList.add(new DiagnosisItem("스토리지 액세스 키 재생성 주기 점검", "Access Control", AZR_047, "\"keyPolicy\" : 90 이하 또는 \"null\" 아니면 양호", "High", "스토리지 계정 액세스 키를 주기적으로 재생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("정책 할당 (생성) 이벤트 작업로그 경고 설정", "Monitoring", AZR_074, "\"enabled\" : \"true\"면 양호", "Medium", "정책 할당 생성 이벤트에 대한 활동 로그 경고를 생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("네트워크 보안그룹(생성/갱신) 이벤트 작업로그 경고 설정", "Monitoring", AZR_076, "\"enabled\" : \"true\"면 양호", "Medium", "네트워크 보안 그룹 생성 또는 갱신 이벤트에 대한 활동 로그 경고를 생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("네트워크 보안그룹 룰(생성/갱신) 이벤트 작업로그 경고 설정", "Monitoring", AZR_078, "\"enabled\" : \"true\"면 양호", "Medium", "네트워크 보안 그룹 규칙 생성 또는 갱신 이벤트에 대한 활동 로그 경고를 생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("보안솔루션 (생성/갱신) 이벤트 작업로그 경고 설정", "Monitoring", AZR_080, "\"enabled\" : \"true\"면 양호", "Medium", "보안 솔루션 생성 또는 업데이트 이벤트에 대한 활동 로그 경고를 생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("SQL 서버 방화벽 룰(생성/갱신/삭제) 이벤트 작업로그 경고 설정", "Monitoring", AZR_082, "\"enabled\" : \"true\"면 양호", "Medium", "SQL 서버 방화벽 규칙 생성 or 갱신 이벤트에 대한 활동 로그 경고를 생성합니다."));

        diagnosisItemList.add(new DiagnosisItem("Azure 앱 서비스 내 웹/앱 HTTPS 리다이렉트 사용 확인", "ETC", AZR_106, "\"httpsOnly\" : \"true\"면 양호", "Medium", "Azure Web Apps를 사용하면 기본적으로 HTTP와 HTTPS 모두에서 사이트를 실행할 수 있습니다.\n" +
                "기본적으로 안전하지 않은 HTTP 링크를 사용하는 모든 사람이 웹 앱에 액세스할 수 있습니다.\n" +
                "안전하지 않은 HTTP 요청은 제한할 수 있으며 모든 HTTP 요청은 안전한 HTTPS 포트로 리디렉션됩니다.\n" +
                "HTTPS 전용 트래픽을 적용하는 것을 권고합니다."));

        diagnosisItemList.add(new DiagnosisItem("최신 버전 TLS 암호화 사용 여부 확인", "ETC", AZR_107, "\"minTlsVersion\" : \"1.2\"면 양호", "Medium", "TLS(전송 계층 보안) 프로토콜은 표준 암호화 기술을 사용하여 인터넷을 통한 데이터 전송을 보호합니다.\n" +
                "최신 버전의 TLS로 암호화를 설정해야 합니다.\n" +
                "앱 서비스는 기본적으로 PCI DSS와 같은 업계 표준에서 권장하는 TLS 수준인 TLS 1.2를 허용합니다."));

        diagnosisItemList.add(new DiagnosisItem("웹/앱 클라이언트 인증서(수신측) 상태 확인", "ETC", AZR_108, "\"clientCertEnabled\" : \"true\"면 양호", "High", "클라이언트 인증서를 사용하면 앱이 수신 요청에 대한 인증서를 요청할 수 있습니다.\n" +
                "유효한 인증서를 가진 클라이언트만 앱에 접속할 수 있습니다."));

        diagnosisItemList.add(new DiagnosisItem("앱 서비스 관리형 ID가 AD 연동 여부 확인", "ETC", AZR_109, "\"principalId\" : \"id값\"면 양호", "Medium", "앱 서비스의 관리형 서비스 ID는 연결 문자열의 자격 증명과 같은 앱의 Secrets을 제거하여 앱을 더욱 안전하게 만듭니다.\n" +
                "앱 서비스에서 Azure Active Directory에 등록하면 사용자 이름과 암호 없이도 다른 Azure 서비스에 안전하게 연결됩니다."));

        for (DiagnosisItem diagnosisItem : diagnosisItemList) {
            diagnosisItemRepository.save(diagnosisItem);
        }
    }
}
