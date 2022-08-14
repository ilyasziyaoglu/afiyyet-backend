package com.afiyyet.comment.controller;

import com.afiyyet.client.comment.CommentRequest;
import com.afiyyet.client.comment.CommentResponse;
import com.afiyyet.comment.service.CommentService;
import com.afiyyet.common.basemodel.controller.AbstractBaseController;
import com.afiyyet.common.basemodel.service.ServiceResult;
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
