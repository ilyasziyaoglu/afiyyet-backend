package com.smartmenu.contactform.controller;

import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.contactform.db.entity.ContactForm;
import com.smartmenu.contactform.service.ContactFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/contactform")
public class ContactFormController {
	final private ContactFormService service;

	@PostMapping
	public ResponseEntity<ContactForm> save(@RequestBody ContactFormRequest dto) {
		ServiceResult<ContactForm> serviceResult = service.save(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
