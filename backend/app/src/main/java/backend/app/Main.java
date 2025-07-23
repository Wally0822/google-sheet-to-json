package backend.app;

import backend.app.controller.SheetController;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        // ✅ 포트 설정 (기본 8080)
        port(8080);

        // ✅ 정적 파일 경로 등록
        staticFiles.location("/static");

        // ✅ REST API 경로 등록
        new SheetController().setupRoutes();

        System.out.println("🚀 Server started at http://localhost:8080");
    }
}
