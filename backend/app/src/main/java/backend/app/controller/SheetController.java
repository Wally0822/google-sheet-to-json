package backend.app.controller;

import backend.app.service.GoogleSheetService;
import static spark.Spark.*;

public class SheetController {

    private final GoogleSheetService service = new GoogleSheetService();

    public void setupRoutes() {

        // ✅ 전체 시트 이름만 반환 (ex: ["User", "Product"])
        get("/sheets", (req, res) -> {
            res.type("application/json");
            return service.fetchSheetNames();
        });

        // ✅ 시트 이름을 기반으로 해당 데이터만 처리 (JSON 저장 + Java 클래스 생성)
        get("/sheets/:name", (req, res) -> {
            String sheetName = req.params(":name");
            res.type("application/json");
            return service.fetchAndProcessSheet(sheetName);
        });
    }
}
