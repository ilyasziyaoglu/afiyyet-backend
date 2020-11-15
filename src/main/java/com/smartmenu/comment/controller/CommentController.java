package com.smartmenu.comment.controller;

import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.client.comment.CommentResponse;
import com.smartmenu.comment.service.CommentService;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/comment")
public class CommentController extends AbstractBaseController {
	private CommentService service;

	public CommentController(final CommentService service) {
		this.service = service;
	}

	public CommentService getService() {
		return service;
	}

	@GetMapping("/get-comments-by-brand")
	public ResponseEntity<ServiceResult<List<CommentResponse>>> getCommentsByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<CommentResponse>> serviceResult = service.getCommentsByBrand(token);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}

	@PostMapping("/insert-comment")
	public ResponseEntity<ServiceResult<Boolean>> getCommentsByBrand(@RequestBody CommentRequest dto) {
		ServiceResult<Boolean> serviceResult = getService().insertComment(dto);
		return new ResponseEntity<>(serviceResult, HttpStatus.OK);
	}
}
