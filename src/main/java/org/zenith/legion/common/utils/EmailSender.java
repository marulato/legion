package org.zenith.legion.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.util.List;

public class EmailSender {

    private static JavaMailSenderImpl mailSender = SpringUtils.getBean(JavaMailSenderImpl.class);
    private static final Logger log = LoggerFactory.getLogger(EmailSender.class);

    public static void sendEmail(String sentFrom, List<String> sentTo, List<String> cc, String subject,
                                 String content, byte[] attachment) {
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
                mimeMessageHelper.addAttachment("", new ByteArrayResource(attachment));
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Email sent FAILED", e);
        }

    }
}
