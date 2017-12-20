package com.znjf33.external.api.provider.mapper;

import com.znjf33.external.api.provider.domain.znjfExceptionRecordDO;

public interface znjfExceptionRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(znjfExceptionRecordDO record);

    int insertSelective(znjfExceptionRecordDO record);

    znjfExceptionRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(znjfExceptionRecordDO record);

    int updateByPrimaryKey(znjfExceptionRecordDO record);
}