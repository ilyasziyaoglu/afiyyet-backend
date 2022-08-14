package com.afiyyet.contactform.mapper;

import com.afiyyet.client.contactform.ContactFormRequest;
import com.afiyyet.common.basemodel.mapper.BaseUpdateMapper;
import com.afiyyet.contactform.db.entity.ContactForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Component
@RequiredArgsConstructor
public class ContactFormUpdateMapper implements BaseUpdateMapper<ContactFormRequest, ContactForm> {

	@Override
	public ContactForm toEntityForUpdate(ContactFormRequest request, ContactForm entity) {
		if (entity == null) {
			entity = new ContactForm();
		}

		return entity;
	}
}
