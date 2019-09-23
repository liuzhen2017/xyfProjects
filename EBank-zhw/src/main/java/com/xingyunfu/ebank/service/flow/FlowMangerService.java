package com.xingyunfu.ebank.service.flow;

import com.xingyunfu.ebank.constant.FlowConstants;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.flow.EbankFlowDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.flow.FlowPageQueryDTO;
import com.xingyunfu.ebank.dto.paycenter.placeorder.PlaceOrderRespDTO;
import com.xingyunfu.ebank.dto.paycenter.transfer.TransferOrderRespDTO;
import com.xingyunfu.ebank.mapper.flow.EbankFlowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.xingyunfu.ebank.constant.FlowConstants.flowStatus_1;
import static com.xingyunfu.ebank.constant.FlowConstants.flowStatus_2;

@Slf4j
@Service
public class FlowMangerService {
    @Autowired private EbankFlowMapper ebankFlowMapper;

    /**
     * 记录商品/套餐购买流水
     */
    public void add(EbankAccountDTO account, PlaceOrderRespDTO orderResp, String flowType, String ebankOrderNo){
        ebankFlowMapper.insert(new EbankFlowDTO(account, orderResp, flowType, FlowConstants.flowSource_product, ebankOrderNo));
    }

    /**
     * 记录转账流水，创建后立即关闭流水
     */
    public void add(EbankAccountDTO account, TransferOrderRespDTO transferOrderResp, String flowType, String ebankOrderNo, Boolean success){
        ebankFlowMapper.insert(new EbankFlowDTO(account, transferOrderResp, flowType, FlowConstants.flowSource_transfer,
                ebankOrderNo, success));
    }

    /**
     * 记录线下手动转账流水
     */
    public void add(EbankAccountDTO account, BigDecimal amount, String flowType, String ebankOrderNo){
        ebankFlowMapper.insert(new EbankFlowDTO(account, amount, flowType, FlowConstants.flowSource_transfer, ebankOrderNo));
    }

    /**
     * 记录钱包支付流水
     */
    public void addSuccess(EbankAccountDTO account, BigDecimal amount, String flowType, String ebankOrderNo){
        EbankFlowDTO flow = new EbankFlowDTO(account, amount, flowType, FlowConstants.flowSource_transfer, ebankOrderNo);
        flow.setStatus(flowStatus_1);
        ebankFlowMapper.insert(flow);
    }

    /**
     * 更新流水状态，等待支付，支付成功，支付失败
     */
    public void updateStatus(String ebankOrderNo, Boolean success){
        EbankFlowDTO flow = new EbankFlowDTO();
        flow.setEbankOrderNo(ebankOrderNo);
        flow.setStatus(success?flowStatus_1:flowStatus_2);
        ebankFlowMapper.update(flow);
    }

    /**
     * 分页条件查询流水
     */
    public PageDTO<EbankFlowDTO> pageQuery(FlowPageQueryDTO pageQuery){
        Integer pageNo = pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize();

        pageQuery.setPageNo((pageNo-1)*pageSize);
        return new PageDTO<>(pageNo, pageSize, ebankFlowMapper.pageQueryCount(pageQuery),
                ebankFlowMapper.pageQuery(pageQuery));
    }
}
