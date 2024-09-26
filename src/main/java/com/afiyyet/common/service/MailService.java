package com.afiyyet.common.service;

import com.afiyyet.common.MailEvent;
import jakarta.annotation.PostConstruct;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;
	final protected MessageSource messageSource;

	@PostConstruct
	protected void init2() {
		Velocity.setProperty(RuntimeConstants.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
		Velocity.setProperty(RuntimeConstants.INPUT_ENCODING, StandardCharsets.UTF_8.name());
		Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//		Velocity.init();
	}

	public void send(MailEvent mailEvent) throws Throwable {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessage.setSubject(mailEvent.getSubject(), "UTF-8");
			mimeMessage.setFrom(new InternetAddress(mailEvent.getFrom()));
			if (CollectionUtils.isNotEmpty(mailEvent.getTo())){
				mimeMessage.setRecipients(Message.RecipientType.TO, getToMailAddresses(mailEvent.getTo()));
			}
			if (CollectionUtils.isNotEmpty(mailEvent.getBcc())) {
				mimeMessage.setRecipients(Message.RecipientType.BCC, getToMailAddresses(mailEvent.getBcc()));
			}
			if (CollectionUtils.isNotEmpty( mailEvent.getCc())) {
				mimeMessage.setRecipients(Message.RecipientType.CC, getToMailAddresses(mailEvent.getCc()));
			}

			MimeMultipart content = new MimeMultipart();

			if (StringUtils.isNotEmpty(mailEvent.getVm())) {
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(mergeTemplate(mailEvent.getVm(), mailEvent.getDataModel()), "text/html; charset=UTF-8");
				content.addBodyPart(html);
			}

			if (!mailEvent.getFiles().isEmpty()) {
				mailEvent.getFiles().forEach(file -> {
					try {
						MimeBodyPart sendFile = new MimeBodyPart();
						sendFile.attachFile(file);
						sendFile.setFileName(mailEvent.getFileName() + "." + FilenameUtils.getExtension(file.getPath()));
						content.addBodyPart(sendFile);
					} catch (IOException | MessagingException e) {
						e.printStackTrace();
					}
				});
			}

			mimeMessage.setContent(content);
			mimeMessage.saveChanges();
			mailSender.send(mimeMessage);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String mergeTemplate(String vm, Map<String, Object> data) {
		VelocityContext context = new VelocityContext();

		// Common attributes
		context.put("math", new MathTool());
		context.put("locale", Locale.getDefault());
		context.put("number", 123.123);
		context.put("dateFormat", 123.123);
		context.put("noArgs", new Object[] {});
		context.put("messageSource", messageSource);

		if (!data.isEmpty()) {
			data.forEach(context::put);
		}

		String vmPath = "templates/" + vm + ".vm";
		Template template = Velocity.getTemplate(vmPath, StandardCharsets.UTF_8.name());
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}

	private InternetAddress[] getToMailAddresses(Set<String> emails) throws Throwable {
		List<InternetAddress> validToMails = new ArrayList<>();
		for (String e : emails) {
			if (validateEmailByRegex(e)) {
				validToMails.add(new InternetAddress(e));
			}
		}
		return validToMails.toArray(new InternetAddress[0]);
	}

	public boolean validateEmailByRegex(String email) {
		try {
			if (StringUtils.isEmpty(email)) {
				return false;
			}
			String emailPattern = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(emailPattern);
			return pattern.matcher(email).matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
