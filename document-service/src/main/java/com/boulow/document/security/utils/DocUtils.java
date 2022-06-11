package com.boulow.document.security.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.boulow.document.config.BoulowProperties;
import com.boulow.document.model.DocumentType;

@Component
public class DocUtils {
	
	static final Logger log = LoggerFactory.getLogger(DocUtils.class);

	@Autowired
	BoulowProperties boulowProperties;

	public String getDestinationFolder(DocumentType type) {
		String folder = "";

		switch (type) {
		case AVATAR:
			folder = boulowProperties.getCloudFolders().getAvatar();
			break;
		case PASSPORT:
			folder = boulowProperties.getCloudFolders().getPassport();
			break;
		case LOGO:
			folder = boulowProperties.getCloudFolders().getLogo();
			break;
		case RECEIPT:
			folder = boulowProperties.getCloudFolders().getReceipt();
			break;
		case ID:
			folder = boulowProperties.getCloudFolders().getId();
			break;
		case STATEMENT:
			folder = boulowProperties.getCloudFolders().getStatement();
			break;
		case CERTIFICATE:
			folder = boulowProperties.getCloudFolders().getCertificate();
			break;
		case NOTICE:
			folder = boulowProperties.getCloudFolders().getNotice();
			break;
		case DL:
			folder = boulowProperties.getCloudFolders().getDl();
			break;
		default:
			folder = boulowProperties.getCloudFolders().getOthers();
			break;
		}
		return folder;
	}
	
	public File convertMultiPartToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }
	
	public File convertToFile(byte[] bytes, String name) {
		File temp = new File(name);
		try {
			FileUtils.writeByteArrayToFile(temp, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	public String generateResourceName(String name) {
		return new Date().getTime() + "-" + name.replace(" ", "_");
	}
}
