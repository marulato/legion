package org.zenith.legion.sysadmin.service;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.sysadmin.entity.BatchJob;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BatchJobConfigurationService {

    public List<BatchJob> parseXml() throws Exception {
        String classPath = this.getClass().getResource("/").getPath();
        classPath = classPath.substring(1).replaceAll("%20", " ");
        if ("\\".equals(File.separator)) {
            classPath = classPath.replaceAll("/", "\\\\");
        }
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(classPath + "config/batchjob_configuration.xml"));
        Element root = document.getRootElement();
        List<Element> elementsUnderRoot = root.elements();
        List<BatchJob> batchJobs = new ArrayList<>();
        for (Element batchJobElement : elementsUnderRoot) {
            BatchJob batchJob = new BatchJob();
            List<Element> elements = batchJobElement.elements();
            for (Element element : elements) {
                switch (element.getName()) {
                    case "batchJobId":
                        batchJob.setBatchJobId(element.getText().trim());
                        break;
                    case "fullName":
                        batchJob.setFullName(element.getText().trim());
                        break;
                    case "group":
                        batchJob.setGroup(element.getText().trim());
                        break;
                    case "description":
                        batchJob.setDescription(element.getText());
                        break;
                    case "class":
                        batchJob.setClassName(element.getText().trim());
                        break;
                    case "cron":
                        if (CronExpression.isValidExpression(element.getText().trim())) {
                            batchJob.setCron(element.getText().trim());
                        } else {
                            throw new ParseException("Invalid Cron Expression: " + element.getText().trim(), 0);
                        }
                        break;
                    case "startAt":
                        Date date = DateUtils.parseDate(element.getText().trim());
                        if (date != null) {
                            batchJob.setStartAt(date);
                        } else {
                            throw new ParseException("Date Format Not Supported: " + element.getText().trim(), 0);
                        }
                        break;
                    case "endAt":
                        Date endDate = DateUtils.parseDate(element.getText().trim());
                        if (endDate != null) {
                            batchJob.setEndAt(endDate);
                        } else {
                            throw new ParseException("Date Format Not Supported: " + element.getText().trim(), 0);
                        }
                        break;
                    case "dependencies":
                        List<Element>dependencies = element.elements();
                        for (Element dependency : dependencies) {
                            if (dependency.getText().trim().equals(batchJob.getBatchJobId())) {
                                continue;
                            }
                            batchJob.getDependencies().add(dependency.getText().trim());
                        }
                        break;
                    case "parameters":
                        List<Element> params = element.elements();
                        for (Element param : params) {
                            batchJob.getParameterMap().put(param.elementText("key"),
                                    param.elementText("value"));
                        }
                        break;
                    case "missFireStatus":
                        batchJob.setMissFireStatus(element.getText().trim());
                }
            }
            batchJobs.add(batchJob);
        }
        return batchJobs;
    }
}
