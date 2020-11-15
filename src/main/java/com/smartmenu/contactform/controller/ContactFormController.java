package com.smartmenu.contactform.controller;

import com.smartmenu.client.contactform.ContactFormRequest;
import com.smartmenu.client.contactform.ContactFormResponse;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.contactform.service.ContactFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<ServiceResult<ContactFormResponse>> save(@RequestBody ContactFormRequest dto) {
		ServiceResult<ContactFormResponse> serviceResult = service.insert(dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
