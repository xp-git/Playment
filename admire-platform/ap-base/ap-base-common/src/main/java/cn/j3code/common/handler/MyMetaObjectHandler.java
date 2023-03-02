package cn.j3code.common.handler;

import cn.j3code.config.util.SecurityUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.handler
 * @createTime 2022/11/26 - 21:50
 * @description
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(SecurityUtil.getUserThreadLocal().get())) {
            this.strictInsertFill(metaObject, "updater", SecurityUtil::getNickname, String.class);
            this.strictInsertFill(metaObject, "creator", SecurityUtil::getNickname, String.class);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        if (Objects.nonNull(SecurityUtil.getUserThreadLocal().get())) {
            this.strictInsertFill(metaObject, "updater", SecurityUtil::getNickname, String.class);
        }
    }

}
