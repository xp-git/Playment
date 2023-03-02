package cn.j3code.admire.scheduled;

import cn.j3code.admire.service.OrderService;
import cn.j3code.common.annotation.DistributedLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author J3
 * @package cn.j3code.admire.scheduled
 * @createTime 2023/2/9 - 9:21
 * @description
 */
@Component
@Slf4j
@AllArgsConstructor
public class OrderScheduled {

    private final OrderService orderService;


    @DistributedLock
    @Scheduled(cron = "21 0/7 * * * ?")
    public void orderStatusCheck() {
        try {
            log.info("开始回查订单状态");
            orderService.orderStatusCheck();
        } catch (Exception e) {
            //错误处理
            log.error("检查订单状态失败！", e);
        }
    }


}
