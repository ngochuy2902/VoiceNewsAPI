package com.news.voicenews.mapper;

import com.news.voicenews.dto.res.UserRes;
import com.news.voicenews.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRes toUserRes(User user);

    List<UserRes> toUserListRes(List<User> users);
}
