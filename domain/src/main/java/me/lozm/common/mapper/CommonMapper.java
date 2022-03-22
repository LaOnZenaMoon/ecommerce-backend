package me.lozm.common.mapper;

import me.lozm.common.dto.PageDto;
import me.lozm.common.vo.PageVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommonMapper {

    PageVo map(PageDto pageDto);

    PageDto map(PageVo pageVo);

}
