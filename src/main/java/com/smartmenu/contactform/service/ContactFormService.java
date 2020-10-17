package com.smartmenu.contactform.service;

import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.client.contactform.ContactFormResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.contactform.db.repository.ContactFormRepository;
import com.smartmenu.contactform.mapper.ContactFormMapper;
import com.smartmenu.contactform.mapper.ContactFormUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

	public ServiceResult<ContactForm> save(ContactFormRequest request) {
		try {
			ContactForm entity = getRepository().save(getMapper().toEntity(request));
			return new ServiceResult<>(entity, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Entity can not save. Error message: " + e.getMessage());
		}
	}
}
