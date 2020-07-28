package org.zenith.legion.sysadmin.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.zenith.legion.common.persistant.exec.SQLExecutor;
import org.zenith.legion.common.utils.FileNameGenerator;
import org.zenith.legion.common.utils.StringUtils;
import org.zenith.legion.sysadmin.entity.FailedEmail;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class ExternalEmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;
    private static final Logger log = LoggerFactory.getLogger(ExternalEmailService.class);

    public void saveFailedEmail(String sentFrom, List<String> sentTo, List<String> cc,
                                String subject, String content,String originalName,
                                String attachedId, Exception e) {
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
        failedEmail.setLastFailedTime(new Date());
        failedEmail.setCc(ccs.toString());
        failedEmail.setContent(content.getBytes(StandardCharsets.UTF_8));
        failedEmail.setAttachedFileName(originalName);
        failedEmail.setAttachedFileId(attachedId);
        failedEmail.setFailedReason(ExceptionUtils.getRootCauseMessage(e));
        SQLExecutor.save(failedEmail);
    }

    public void sendEmail(String sentFrom, List<String> sentTo, List<String> cc, String subject,
                                 String content,String originalName, byte[] attachment) {
        String attachedId = FileNameGenerator.getEmailAttachmentName(originalName);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            if (StringUtils.isNotBlank(sentFrom)) {
                mimeMessageHelper.setFrom(sentFrom);
            }
            if (sentTo != null && !sentTo.isEmpty()) {
                String[] sendTo = sentTo.toArray(new String[sentTo.size()]);
                mimeMessageHelper.setTo(sendTo);
            }
            if (cc != null && !cc.isEmpty()) {
                String[] ccTo = cc.toArray(new String[cc.size()]);
                mimeMessageHelper.setCc(ccTo);
            }
            if (StringUtils.isNotBlank(subject)) {
                mimeMessageHelper.setSubject(subject);
            }
            if (StringUtils.isNotBlank(content)) {
                mimeMessageHelper.setText(content, true);
            }
            if (attachment != null && attachment.length > 0) {
                mimeMessageHelper.addAttachment(attachedId, new ByteArrayResource(attachment));
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Email sent FAILED", e);
            saveFailedEmail(sentFrom, sentTo, cc, subject, content, originalName, attachedId, e);
        }

    }
}
