package cn.j3code.admire.scheduled;

import cn.j3code.admire.service.CommodityService;
import cn.j3code.common.annotation.DistributedLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author J3
 * @package cn.j3code.admire.scheduled
 * @createTime 2023/2/9 - 9:49
 * @description
 */
@Component
@Slf4j
@AllArgsConstructor
public class CommodityScheduled {

    private final CommodityService commodityService;


    @DistributedLock
    @Scheduled(cron = "11 0/11 * * * ?")
    public void setCommodityLookNumber(){
        try {
            log.info("开始回填商品查看人数");
            commodityService.setCommodityLookNumber();
        } catch (Exception e) {
            //错误处理
            log.error("回填商品查看人数失败！", e);
        }
    }

    @DistributedLock
    @Scheduled(cron = "13 0/13 * * * ?")
    public void setCommodityBuyNumber(){
        commodityService.setCommodityBuyNumber();
    }
}
