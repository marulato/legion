package org.zenith.legion.common.docgen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.zenith.legion.sysadmin.entity.EmailEntity;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class EmailTemplateGenerator implements IDocGenerator{

    @Override
    public byte[] generate() throws Exception {
        String templatePath = this.getClass().getResource("/").getPath();
        templatePath = templatePath.substring(1).replaceAll("%20", " ") + "emails/";
        String ftl = getTemplate();
        String fileName = getTemplate();
        if (ftl.contains("/")) {
            fileName = ftl.substring(ftl.lastIndexOf("/") + 1);
            templatePath += ftl.substring(0, ftl.lastIndexOf("/"));
        }
        if ("\\".equals(File.separator)) {
            templatePath = templatePath.replaceAll("/", "\\\\");
        }
        Configuration config = new Configuration(Configuration.VERSION_2_3_30);
        config.setDirectoryForTemplateLoading(new File(templatePath));
        Template template = config.getTemplate(fileName);
        StringWriter writer = new StringWriter();
        template.process(getParameters(), writer);
        return writer.toString().getBytes(StandardCharsets.UTF_8);
    }

    public abstract String getSubject();

    public EmailEntity generateEmail(List<String> sendTo, List<String> ccTo) {
        EmailEntity emailEntity = new EmailEntity();
        return emailEntity;
    }
}
