package com.smartmenu.contactform.mapper;

import com.smartmenu.brand.mapper.BrandMapper;
import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.common.basemodel.mapper.BaseUpdateMapper;
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
