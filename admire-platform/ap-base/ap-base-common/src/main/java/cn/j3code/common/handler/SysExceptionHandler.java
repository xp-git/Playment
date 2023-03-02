package cn.j3code.common.handler;

import cn.j3code.config.exception.TokenAuthException;
import cn.j3code.config.exception.ApCodeException;
import cn.j3code.config.exception.ApException;
import cn.j3code.config.vo.FailInfo;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.handler
 * @createTime 2022/11/26 - 16:01
 * @description
 */
@Slf4j
@RestControllerAdvice
public class SysExceptionHandler {

    /**
     * 最大的兜底错误处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public FailInfo exception(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        var failInfo = FailInfo.builder().exception(ex.getMessage()).build();
        return failInfo;
    }

    /**
     * 参数绑定错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public FailInfo exception(BindException ex) {
        String defaultMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        log.error("Exception_info:{}", defaultMessage);
        log.error("Exception_info:", ex);
        var failInfo = FailInfo.builder().exception(defaultMessage).build();
        return failInfo;
    }

    @ExceptionHandler(value = ApCodeException.class)
    public FailInfo ldCodeException(ApCodeException ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        FailInfo failInfo = new FailInfo(ex.getCode(), ex.getMessage());
        return failInfo;
    }

    @ExceptionHandler(value = ApException.class)
    public FailInfo sysException(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        var failInfo = FailInfo.builder().exception(ex.getMessage()).build();
        return failInfo;
    }

    @ExceptionHandler(value = TokenAuthException.class)
    public FailInfo tokenAuthException(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        var failInfo = FailInfo.builder().exception(ex.getMessage()).build();
        return failInfo;
    }


    @ExceptionHandler(value = MysqlDataTruncation.class)
    public FailInfo mysqlDataTruncation(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        return new FailInfo(500, ex.getMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public FailInfo dataIntegrityViolationException(Exception ex) {
        log.error("Exception_info:{}", ex.getMessage());
        log.error("Exception_info:", ex);
        String message = ex.getMessage();
        String[] split = message.split("\r\n###");
        for (String str : split) {
            if (str.trim().isBlank() || str.trim().contains("Error")){
                continue;
            }
            String[] split1 = str.split(":");
            if (split1.length > 0) {
                message = split1[split1.length - 1].trim();
            }
        }
        return new FailInfo(500, message);
    }
}
