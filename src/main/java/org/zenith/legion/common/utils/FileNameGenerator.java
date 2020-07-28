package org.zenith.legion.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class FileNameGenerator {

    public static String getEmailAttachmentName(String originalName) {
        StringBuilder name = new StringBuilder();
        if (StringUtils.isNotBlank(originalName)) {
            name.append("EE$").append(Base64.encodeBase64String(originalName.getBytes(StandardCharsets.UTF_8))).append("$").
                    append(DigestUtils.sha256Hex(originalName)).append("@").append(System.nanoTime());
        }
        return name.toString();
    }
}
