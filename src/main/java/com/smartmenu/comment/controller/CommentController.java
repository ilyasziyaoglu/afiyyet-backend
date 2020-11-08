package com.smartmenu.comment.controller;

import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.client.comment.CommentResponse;
import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.comment.mapper.CommentMapper;
import com.smartmenu.comment.service.CommentService;
import com.smartmenu.common.basemodel.controller.AbstractBaseController;
import com.smartmenu.common.basemodel.service.ServiceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RestController
@RequestMapping(value = "/comment")
public class CommentController extends AbstractBaseController<CommentRequest, Comment, CommentResponse, CommentMapper, CommentService> {
	private CommentService service;
	private CommentMapper mapper;

	public CommentController(final CommentService service, final CommentMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	public CommentService getService() {
		return service;
	}

	public CommentMapper getMapper() {
		return mapper;
	}

	@GetMapping("/get-comments-by-brand")
	public ResponseEntity<List<Comment>> getCommentsByBrand(@RequestHeader(HEADER_TOKEN) String token) {
		ServiceResult<List<Comment>> serviceResult = getService().getCommentsByBrand(token);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping("/insert-comment")
	public ResponseEntity<Boolean> getCommentsByBrand(@RequestBody CommentRequest dto) {
		ServiceResult<Boolean> serviceResult = getService().insertComment(dto);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}
