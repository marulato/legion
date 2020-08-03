package org.zenith.legion.general.service;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.utils.DateUtils;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.general.entity.BatchJob;
import org.zenith.legion.general.ex.UnsupportedXMLFormatException;

import java.io.File;
import java.text.ParseException;
import java.util.*;

@Service
public class BatchJobConfigurationService {

    public static final List<String> XML_ELEMENTS = List.of(
            "batchJobConfiguration", "batchJob", "batchJobId",
            "fullName", "group", "description", "class", "cron",
            "startAt", "endAt", "interval", "dependencies",
            "dependency", "parameters", "param", "key", "value", "missFireStatus");

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
        if (!checkValidXmlFormat(root)) {
            throw new UnsupportedXMLFormatException("The given XML file format does not meet the requirements");
        }
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
                        batchJob.setCron(element.getText().trim());
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
                    case "interval":
                        String unit = element.attributeValue("unit");
                        batchJob.setIntervalUnit(unit);
                        batchJob.setInterval(Integer.parseInt(element.getText().trim()));
                    case "dependencies":
                        List<Element> dependencies = element.elements();
                        for (Element dependency : dependencies) {
                            if (dependency.getText().trim().equals(batchJob.getBatchJobId())
                                    || StringUtils.isBlank(dependency.getText())) {
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

    public StringBuilder validateBatchJob(BatchJob batchJob, List<BatchJob> batchJobList) {
        StringBuilder errorMsg = new StringBuilder();
        if (batchJob != null && batchJobList != null) {
            if (StringUtils.isBlank(batchJob.getBatchJobId()) || batchJob.getBatchJobId().length() > 32) {
                errorMsg.append("Batch job id invalid | ");
            }
            if (StringUtils.isBlank(batchJob.getFullName()) || batchJob.getFullName().length() > 128) {
                errorMsg.append("Full name invalid | ");
            }
            if (StringUtils.isBlank(batchJob.getGroup()) || batchJob.getGroup().length() > 32) {
                errorMsg.append("Group name invalid | ");
            }
            if (StringUtils.isBlank(batchJob.getClassName())) {
                errorMsg.append("Class name can not be NULL | ");
            } else {
                try {
                    Class.forName(batchJob.getClassName());
                } catch (Exception e) {
                    errorMsg.append("Class NOT Found: [").append(batchJob.getClassName()).append("] | ");
                }
            }
            if (!CronExpression.isValidExpression(batchJob.getCron())) {
                errorMsg.append("Invalid Cron Expression: [").append(batchJob.getCron()).append("] | ");
            }
            if (batchJob.getEndAt().before(batchJob.getStartAt())) {
                errorMsg.append("End date can't be before start date | ");
            }
            if (StringUtils.isBlank(batchJob.getIntervalUnit())) {
                errorMsg.append("Interval unit can't be NULL | ");
            } else if (!"M".equalsIgnoreCase(batchJob.getIntervalUnit()) &&
                    !"H".equalsIgnoreCase(batchJob.getIntervalUnit()) &&
                    !"D".equalsIgnoreCase(batchJob.getIntervalUnit())) {
                errorMsg.append("Invalid interval unit | ");
            }
            boolean isDepValid = false;
            if (batchJob.getDependencies().isEmpty()) {
                isDepValid = true;
            } else {
                for (String depen : batchJob.getDependencies()) {
                    for (BatchJob job : batchJobList) {
                        if (job.getBatchJobId().equals(depen)) {
                            isDepValid = true;
                            break;
                        }
                    }
                    if (!isDepValid) {
                        errorMsg.append("Dependency definition NOT found: [").append(depen).append("] | ");
                        break;
                    }
                }
            }
            if (StringUtils.isBlank(batchJob.getMissFireStatus())) {
                errorMsg.append("Miss fire status can't be NULL | ");
            } else if(!"Y".equalsIgnoreCase(batchJob.getMissFireStatus())
                    && !"N".equalsIgnoreCase(batchJob.getMissFireStatus())) {
                errorMsg.append("Invalid miss fire status | ");
            }
        }
        if (errorMsg.length() > 0) {
            errorMsg.insert(0, batchJob.getBatchJobId() + " Found ERRORS -> ");
            errorMsg.delete(errorMsg.length() - 3, errorMsg.length());
        }
        return errorMsg;
    }

    public boolean checkValidXmlFormat(Element root) {
        boolean isValid = true;
        if ("batchJobConfiguration".equals(root.getName())) {
            List<Element> elementsUnderRoot = root.elements();
            for (Element batchElement : elementsUnderRoot) {
                if ("batchJob".equals(batchElement.getName())) {
                    List<Element> elements = batchElement.elements();
                    if (elements.size() != 12) {
                        isValid = false;
                        break;
                    }
                    for (Element child : elements) {
                        if (!XML_ELEMENTS.contains(child.getName())) {
                            isValid = false;
                            break;
                        }
                        if ("dependencies".equals(child.getName())) {
                            List<Element> dependencies = child.elements();
                            for (Element element : dependencies) {
                                if (!"dependency".equals(element.getName())) {
                                    isValid = false;
                                }
                            }
                        }
                        if ("parameters".equals(child.getName())) {
                            List<Element> params = child.elements();
                            for (Element param : params) {
                                if (!"param".equals(param.getName())) {
                                    isValid = false;
                                    break;
                                } else {
                                    List<Element> keyValuePairs = param.elements();
                                    if (keyValuePairs.size() == 2) {
                                        String first = keyValuePairs.get(0).getName();
                                        String second = keyValuePairs.get(1).getName();
                                        if (!((first.equals("key") && second.equals("value"))
                                                || (first.equals("value") && second.equals("key")))) {
                                            isValid = false;
                                        }
                                    } else {
                                        isValid = false;
                                    }
                                }
                            }
                        }
                    }
                    if (!isValid) {
                        break;
                    }
                } else {
                    isValid = false;
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }
}
