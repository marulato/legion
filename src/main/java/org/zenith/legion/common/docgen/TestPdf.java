package org.zenith.legion.common.docgen;

import java.util.HashMap;
import java.util.Map;

public class TestPdf extends PdfTemplateGenerator {
    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @Override
    public String getTemplate() {
        return "test.ftl";
    }
}
