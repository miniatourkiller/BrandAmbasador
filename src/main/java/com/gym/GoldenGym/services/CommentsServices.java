package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.CommentDto;
import com.gym.GoldenGym.dtos.ResponseDto;

public interface CommentsServices {
    public ResponseDto fetchComments();

    public ResponseDto fetchCommentsRattingsAbove(int rating);

    public ResponseDto createComment(CommentDto commentDto);

    public ResponseDto deleteComment(Long commentId);
}