package com.afiyyet.comment.mapper;

import com.afiyyet.client.comment.CommentRequest;
import com.afiyyet.client.comment.CommentResponse;
import com.afiyyet.comment.db.entity.Comment;
import com.afiyyet.common.basemodel.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<CommentRequest, Comment, CommentResponse> {
}
