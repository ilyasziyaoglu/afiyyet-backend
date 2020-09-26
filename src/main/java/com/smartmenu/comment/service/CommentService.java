package com.smartmenu.comment.service;

import com.smartmenu.brand.db.entity.Brand;
import com.smartmenu.brand.db.repository.BrandRepository;
import com.smartmenu.comment.db.entity.Comment;
import com.smartmenu.comment.db.repository.CommentRepository;
import com.smartmenu.comment.mapper.CommentMapper;
import com.smartmenu.comment.mapper.CommentUpdateMapper;
import com.smartmenu.client.comment.CommentRequest;
import com.smartmenu.client.comment.CommentResponse;
import com.smartmenu.common.basemodel.service.AbstractBaseService;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.user.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Service
@RequiredArgsConstructor
public class CommentService extends AbstractBaseService<CommentRequest, Comment, CommentResponse, CommentMapper> {
	final private CommentRepository repository;
	final private CommentMapper mapper;
	final private CommentUpdateMapper updateMapper;
	final private BrandRepository brandRepository;

	@Override
	public CommentRepository getRepository() {
		return repository;
	}

	@Override
	public CommentMapper getMapper() {
		return mapper;
	}

	@Override
	public CommentUpdateMapper getUpdateMapper() {
		return updateMapper;
	}

	@Override
	public ServiceResult<Comment> save(String token, CommentRequest request) {
		ServiceResult<Comment> serviceResult = new ServiceResult<>();
		try {
			Comment entityToSave = getMapper().toEntity(request);
			Brand brand = brandRepository.getOne(request.getBrand().getId());
			entityToSave.setBrand(brand);
			Comment entity = getRepository().save(entityToSave);
			serviceResult.setValue(entity);
			serviceResult.setHttpStatus(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setMessage("Entity can not save. Error message: " + e.getMessage());
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return serviceResult;
	}

	public ServiceResult<List<Comment>> getCommentsByBrand(String token) {
		ServiceResult<List<Comment>> serviceResult = new ServiceResult<>();
		try {
			List<Comment> entityList = getRepository().findAllByBrand(getUser(token).getBrand());
			entityList = entityList.stream().sorted(Comparator.comparing(Comment::getCreatedDate, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
			serviceResult.setValue(entityList);
			serviceResult.setHttpStatus(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			serviceResult.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			serviceResult.setMessage(e.getMessage());
		}
		return serviceResult;
	}
}
