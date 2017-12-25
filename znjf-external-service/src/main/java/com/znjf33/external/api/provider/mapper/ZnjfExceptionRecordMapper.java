package com.znjf33.external.api.provider.mapper;

import com.znjf33.external.api.provider.domain.ZnjfExceptionRecordResultDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZnjfExceptionRecordMapper {
    int updateByPrimaryKey(Integer id);
    /**
     * 查询在线充值当天超过30分钟异常数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryAccountRechargeException();
    /**
     * 查询提现当天超过30分钟异常数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryAccountCashException();

    /**
     * 查询还款回调异常数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryAccountRePayException();

    /**
     * 查询放款回调异常数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryVerifyFullException();

    /**
     * 查询转让回调异常数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryBondException();

    /**
     * 查询处理中的异常表数据
     * @return
     */
    List<ZnjfExceptionRecordResultDO> queryByPrimaryKey();
    /**
     * 查询在线充值当天超过30分钟异常数据
     * @return
     */
    void saveZnjfExceptionRecord(List item);
}