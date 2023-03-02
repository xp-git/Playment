package cn.j3code.admire.service;

import cn.j3code.admire.po.Commodity;
import cn.j3code.admire.po.Order;
import cn.j3code.admire.po.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author J3code
* @description 针对表【ap_pay_log】的数据库操作Service
* @createDate 2023-02-04 12:01:56
*/
public interface PayLogService extends IService<PayLog> {
    void saveBy(Order order, Commodity commodity);
}
