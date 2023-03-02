package cn.j3code.common.handler;

import cn.j3code.common.annotation.ResponseResult;
import cn.j3code.config.vo.SuccessInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.common.handler
 * @createTime 2022/11/26 - 15:45
 * @description
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        final var method = methodParameter.getMethod();
        final var clazz = Objects.requireNonNull(method, "method is null").getDeclaringClass();

        // 只处理 ResponseResult 标注的类或方法
        var annotation = clazz.getAnnotation(ResponseResult.class);
        if (Objects.isNull(annotation)) {
            annotation = method.getAnnotation(ResponseResult.class);
        }

        //如果是FileSystemResource 则不拦截
        if (method.getAnnotatedReturnType().getType().getTypeName()
                .equals(FileSystemResource.class.getTypeName())) {
            return false;
        }
        return annotation != null && !annotation.ignore();
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object data,
                                  MethodParameter mediaType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse serverHttpResponse) {
        // 处理结果集
        var successInfo = SuccessInfo.builder()
                .data(data)
                .build();

        if ((data instanceof String) && !MediaType.APPLICATION_XML_VALUE.equals(mediaType.toString())) {
            ObjectMapper om = new ObjectMapper();
            serverHttpResponse.getHeaders().set("Content-Type", "application/json");
            return om.writeValueAsString(successInfo);
        }

        if (Objects.isNull(data) && MediaType.TEXT_HTML_VALUE.equals(mediaType.toString())) {
            ObjectMapper om = new ObjectMapper();
            serverHttpResponse.getHeaders().set("Content-Type", "application/json");
            return om.writeValueAsString(successInfo);
        }

        return successInfo;
    }
}
