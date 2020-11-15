package com.smartmenu.contactform.service;

import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.client.contactform.ContactFormResponse;
import com.smartmenu.common.MailEvent;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.service.MailService;
import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.contactform.db.repository.ContactFormRepository;
import com.smartmenu.contactform.mapper.ContactFormMapper;
import com.smartmenu.contactform.mapper.ContactFormUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class ContactFormService extends AbstractBaseService<ContactFormRequest, ContactForm, ContactFormResponse, ContactFormMapper> {
	final private ContactFormRepository repository;
	final private ContactFormMapper mapper;
	final private ContactFormUpdateMapper updateMapper;
	final private MailService mailService;

	@Override
	public ContactFormRepository getRepository() {
		return repository;
	}

	@Override
	public ContactFormMapper getMapper() {
		return mapper;
	}

	@Override
	public ContactFormUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	public ServiceResult<ContactFormResponse> insert(ContactFormRequest request) {
		try {
			ContactForm entity = getRepository().save(getMapper().toEntity(request));
			Set<String> toMailSet = new HashSet<>();
			toMailSet.add("info@evercode.net");
			toMailSet.add("ilyasziyaoglu@gmail.com");
			toMailSet.add("emilmammadiov303@gmail.com");

			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("contactForm", request);

			MailEvent mailEvent = new MailEvent();
			mailEvent.setTo(toMailSet);
			mailEvent.setFrom("by.computer.engineer@gmail.com");
			mailEvent.setVm("ContactForm");
			mailEvent.setDataModel(dataModel);
			mailEvent.setSubject("Smartmenu Contact Form");

			try {
				mailService.send(mailEvent);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}

			return new ServiceResult<>(mapper.toResponse(entity), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(e);
		}
	}
}
