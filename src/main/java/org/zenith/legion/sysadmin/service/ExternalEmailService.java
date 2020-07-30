package org.zenith.legion.sysadmin.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.consts.AppConsts;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.ConfigUtils;
import org.zenith.legion.common.utils.FileNameGenerator;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.dao.ExternalEmailDAO;
import org.zenith.legion.sysadmin.entity.EmailEntity;
import org.zenith.legion.sysadmin.entity.FailedEmail;
import org.zenith.legion.sysadmin.entity.FileNet;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class ExternalEmailService {

    private JavaMailSenderImpl mailSender;
    private ExternalEmailDAO externalEmailDAO;
    private FileNetService fileNetService;

    private static final Logger log = LoggerFactory.getLogger(ExternalEmailService.class);

    @Autowired
    public ExternalEmailService(
            JavaMailSenderImpl mailSender,
            ExternalEmailDAO externalEmailDAO,
            FileNetService fileNetService) {

        this.mailSender = mailSender;
        this.externalEmailDAO = externalEmailDAO;
        this.fileNetService = fileNetService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFailedEmail(String sentFrom, List<String> sentTo, List<String> cc,
                                String subject, String content, MultipartFile attachment, Exception e) throws Exception {
        FailedEmail failedEmail = new FailedEmail();
        failedEmail.setSentFrom(sentFrom);
        failedEmail.setSubject(subject);
        StringBuilder sentTos = new StringBuilder();
        for (String to : sentTo) {
            sentTos.append(to).append(";");
        }
        if (sentTos.length() > 0) {
            sentTos.deleteCharAt(sentTos.lastIndexOf(";"));
        }
        failedEmail.setSentTo(sentTos.toString());
        StringBuilder ccs = new StringBuilder();
        for (String ccTo : cc) {
            ccs.append(ccTo).append(";");
        }
        if (ccs.length() > 0) {
            ccs.deleteCharAt(ccs.lastIndexOf(";"));
        }
        failedEmail.setFailedTimes(1);
        failedEmail.setStatus(AppConsts.EMIAL_STATUS_NOT_SENT);
        failedEmail.setLastFailedTime(new Date());
        failedEmail.setCc(ccs.toString());
        failedEmail.setContent(content.getBytes(StandardCharsets.UTF_8));
        failedEmail.setFailedReason(ExceptionUtils.getRootCauseMessage(e));
        if (attachment != null) {
            failedEmail.setIsHasAttachment(AppConsts.YES);
        }
        saveFailedEmail(failedEmail);
        fileNetService.saveToFileNet(FileNetService.getEmailStoragePath(failedEmail.getSentFrom()), attachment);
    }

    public void sendEmail(String sentFrom, List<String> sentTo, List<String> cc, String subject,
                          String content, MultipartFile attachment) throws Exception {
        if (sentTo == null || sentTo.isEmpty() ||
                (StringUtils.isEmpty(subject) && StringUtils.isEmpty(content) && attachment == null)) {
            return;
        }
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            if (StringUtils.isNotBlank(sentFrom)) {
                mimeMessageHelper.setFrom(sentFrom);
            } else {
                mimeMessageHelper.setFrom(ConfigUtils.get("legion.server.mail.host"));
            }
            String[] sendTo = sentTo.toArray(new String[0]);
            mimeMessageHelper.setTo(sendTo);
            if (cc != null && !cc.isEmpty()) {
                String[] ccTo = cc.toArray(new String[0]);
                mimeMessageHelper.setCc(ccTo);
            }
            if (StringUtils.isNotBlank(subject)) {
                mimeMessageHelper.setSubject(subject);
            } else {
                mimeMessageHelper.setSubject("(No Subject)");
            }
            if (StringUtils.isNotBlank(content)) {
                mimeMessageHelper.setText(content, true);
            } else {
                mimeMessageHelper.setText("(No Content)");
            }
            if (attachment != null) {
                mimeMessageHelper.addAttachment(attachment.getOriginalFilename(), new ByteArrayResource(attachment.getBytes()));
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Email sent FAILED", e);
            saveFailedEmail(sentFrom, sentTo, cc, subject, content, attachment, e);
            throw e;
        }

    }

    public void saveFailedEmail(FailedEmail failedEmail) {
        if (failedEmail != null) {
            failedEmail.createAuditValues(AppContext.getAppContextFromCurrentThread());
            externalEmailDAO.saveFailedEmail(failedEmail);
        }
    }
}
