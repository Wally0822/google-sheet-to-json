package backend.app.util;

import com.squareup.javapoet.*;
import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class JavaClassGenerator {

    // 기본 버전 (경로 지정 없이 자동 처리)
    public static void generate(String className, Map<String, String> fields) throws IOException {
        String defaultTargetDir = "backend/app/src/main/java";
        generate(className, fields, defaultTargetDir);
    }

    // 경로 지정 가능한 버전
    public static void generate(String className, Map<String, String> fields, String targetDir) throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            String typeName = entry.getValue();

            TypeName type = mapToTypeName(typeName);

            // 필드
            classBuilder.addField(FieldSpec.builder(type, fieldName, Modifier.PRIVATE).build());

            // getter
            classBuilder.addMethod(MethodSpec.methodBuilder("get" + capitalize(fieldName))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(type)
                    .addStatement("return this.$N", fieldName)
                    .build());

            // setter
            classBuilder.addMethod(MethodSpec.methodBuilder("set" + capitalize(fieldName))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(type, fieldName)
                    .addStatement("this.$N = $N", fieldName, fieldName)
                    .build());
        }

        JavaFile javaFile = JavaFile.builder("backend.app.model", classBuilder.build())
                .build();

        Path projectRoot = Paths.get("").toAbsolutePath();
        Path outputPath = projectRoot.resolve(targetDir);
        javaFile.writeTo(outputPath);

        System.out.println("📁 클래스 생성 경로: " + outputPath);
        System.out.println("📄 생성 대상 파일명: " + className + ".java");
    }

    private static TypeName mapToTypeName(String type) {
        return switch (type.toLowerCase()) {
            case "int", "integer" -> TypeName.INT;
            case "long" -> TypeName.LONG;
            case "double" -> TypeName.DOUBLE;
            case "boolean" -> TypeName.BOOLEAN;
            default -> ClassName.get(String.class);
        };
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
