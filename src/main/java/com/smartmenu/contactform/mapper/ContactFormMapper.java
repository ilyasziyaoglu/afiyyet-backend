package com.smartmenu.contactform.mapper;

import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.client.contactform.ContactFormResponse;
import com.smartmenu.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface ContactFormMapper extends BaseMapper<ContactFormRequest, ContactForm, ContactFormResponse> {
}
