package cn.j3code.admire.service.impl;

import cn.j3code.admire.mapper.PayLogMapper;
import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.po.Order;
import cn.j3code.admire.po.PayLog;
import cn.j3code.admire.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author J3code
 * @description 针对表【ap_pay_log】的数据库操作Service实现
 * @createDate 2023-02-04 12:01:56
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog>
        implements PayLogService {

    @Override
    public void saveBy(Order order, Commodity commodity) {
        PayLog payLog = new PayLog();
        payLog.setUserId(order.getUserId());
        payLog.setOrderId(order.getId());
        payLog.setChangeMoney(order.getMoney());
        payLog.setType(commodity.getName().contains("注册") ? 2 : 1);
        save(payLog);
    }
}




