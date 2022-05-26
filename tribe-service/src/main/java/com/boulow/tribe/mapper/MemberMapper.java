package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.Member;
import com.boulow.tribe.model.dto.MemberDto;

@Mapper(
        componentModel = "spring",
        uses = {TribeMapper.class}
)
public interface MemberMapper {

	MemberDto convertToDto(Member member);
    Member convertFromDto(MemberDto memberDto);
}
