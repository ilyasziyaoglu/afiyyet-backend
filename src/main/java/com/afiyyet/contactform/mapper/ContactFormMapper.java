package com.afiyyet.contactform.mapper;

import com.afiyyet.client.contactform.ContactFormRequest;
import com.afiyyet.client.contactform.ContactFormResponse;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import com.afiyyet.contactform.db.entity.ContactForm;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ContactFormMapper extends BaseMapper<ContactFormRequest, ContactForm, ContactFormResponse> {
}
