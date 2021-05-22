package com.news.voicenews.mapper;

import com.news.voicenews.dto.res.SessionRes;
import com.news.voicenews.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface SessionMapper {
    SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

    SessionRes toSessionRes(Session session);
}
