package com.example.bookinglabor.mapper;

import com.example.bookinglabor.model.CommentSkill;

public class CommentSkillMapper {

    public static CommentSkill mapToCommentSkill(CommentSkill commentSkill){

        return CommentSkill.builder()
                .id(commentSkill.getId())
                .content(commentSkill.getContent())
                .jobDetail(commentSkill.getJobDetail())
                .build();
    }



}
