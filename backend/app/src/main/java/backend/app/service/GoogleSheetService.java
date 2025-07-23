package backend.app.service;

import backend.app.util.JavaClassGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class GoogleSheetService {

    private static final String SCRIPT_BASE_URL = "https://script.google.com/macros/s/AKfycbxSiqpa9UwZgmoyT42I1H3qYZu-DKkKDMlHk6DVD4m1aLI61n-0a2JzxoE014Xg4x74/exec";

    private JSONObject fullSheetDataCache = null; // ✅ 전체 데이터를 캐싱 (옵션)

    // ✅ 전체 JSON 받아오기 (User, Product 등 포함)
    private JSONObject fetchAllSheetsData() throws IOException {
        if (fullSheetDataCache != null) return fullSheetDataCache;

        String url = SCRIPT_BASE_URL + "?mode=sheets";
        System.out.println("🌐 [HTTP GET] " + url);

        String response = httpGet(url);
        System.out.println("📥 전체 시트 응답 원본: " + response);

        fullSheetDataCache = new JSONObject(response);
        return fullSheetDataCache;
    }

    // ✅ 시트 이름만 추출
    public String fetchSheetNames() {
        try {
            JSONObject all = fetchAllSheetsData();
            JSONArray keys = new JSONArray(all.keySet());
            System.out.println("✅ 시트 이름 배열: " + keys);
            return keys.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"시트 이름 추출 실패\"}";
        }
    }

    // ✅ 특정 시트 처리: 클래스 + JSON 저장
    public String fetchAndProcessSheet(String sheetName) {
        try {
            JSONObject all = fetchAllSheetsData();

            if (!all.has(sheetName)) return "{\"error\": \"존재하지 않는 시트\"}";

            JSONArray sheetData = all.getJSONArray(sheetName);
            System.out.println("📄 " + sheetName + " 시트 데이터: " + sheetData);

            if (sheetData.isEmpty()) return "{\"error\": \"데이터 없음\"}";

            // 필드 추론
            JSONObject first = sheetData.getJSONObject(0);
            Map<String, String> fieldTypes = new LinkedHashMap<>();
            for (String key : first.keySet()) {
                Object val = first.get(key);
                fieldTypes.put(key, inferType(val));
            }

            // ✅ Java 클래스 생성 (경로 명시)
            String targetDir = "app/src/main/java/backend/app/model";
            JavaClassGenerator.generate(sheetName, fieldTypes, targetDir);
            System.out.println("✅ 클래스 생성 완료: " + targetDir + "/" + sheetName + ".java");

            // ✅ JSON 저장
            saveJsonFile(sheetName, sheetData.toString(2));
            System.out.println("✅ JSON 저장 완료");

            return sheetData.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"시트 처리 중 오류\"}";
        }
    }

    private String inferType(Object value) {
        if (value instanceof Boolean) return "boolean";
        if (value instanceof Integer) return "int";
        if (value instanceof Number) return "double";
        return "String";
    }

    private String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

private void saveJsonFile(String name, String content) throws IOException {
    // ✅ 클래스 경로를 기준으로 절대 경로 얻기
    String folderPath = Objects.requireNonNull(
        getClass().getClassLoader().getResource("static/data")
    ).getPath();

    String filePath = folderPath + "/" + name + ".json";

    File file = new File(filePath);
    if (file.exists()) {
        System.out.println("✏️ 기존 JSON 파일 덮어쓰기: " + filePath);
    } else {
        System.out.println("🆕 새 JSON 파일 생성: " + filePath);
    }

    try (FileWriter writer = new FileWriter(file)) {
        writer.write(content);
        System.out.println("✅ 저장 완료: " + filePath);
    }
}
}
