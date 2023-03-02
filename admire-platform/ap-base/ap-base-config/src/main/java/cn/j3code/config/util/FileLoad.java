package cn.j3code.config.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.j3code.config.exception.ApException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;

import java.io.FileInputStream;

/**
 * @author J3（about：https://j3code.cn）
 * @package cn.j3code.config.util
 * @createTime 2022/12/7 - 23:56
 * @description
 */
@Slf4j
public class FileLoad {

    public static String read(String fileName) {
        String val = "";
        try {
            val = IoUtil.read(new FileInputStream(FileUtil.file(fileName)), CharEncoding.UTF_8);
        } catch (Exception e) {
            //错误处理
            log.error("文件路径读取失败：{}", fileName, e);
            throw new ApException(String.format("文件路径读取失败: %s", fileName));
        }
        return val;
    }
}
