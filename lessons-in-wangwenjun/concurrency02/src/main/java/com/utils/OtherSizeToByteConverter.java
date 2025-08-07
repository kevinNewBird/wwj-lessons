package com.utils;

import com.exceptions.ConvertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: TODO <BR>
 *
 * @author: zhao.song
 * @date: 2020/8/11 10:50
 * @version: 1.0
 */
public class OtherSizeToByteConverter implements Converter<Number, Long> {

    private static Logger logger = LoggerFactory.getLogger(OtherSizeToByteConverter.class);

    public enum TO_BYTES {
        BYTE(1), KB(1024), MB(1024 * 1024), GB(1024 * 1024 * 1024);
        private long toBytes;

        private TO_BYTES(int toBytes) {
            this.toBytes = toBytes;
        }
    }

    private TO_BYTES currentSizeUnit = TO_BYTES.BYTE;

    public OtherSizeToByteConverter(TO_BYTES currentSizeUnit) {
        this.currentSizeUnit = currentSizeUnit;
    }

    @Override
    public Long convert(Number source) throws ConvertException {
        long result = source.longValue() * currentSizeUnit.toBytes;
        if (result < 0) {
            return 0L;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(new OtherSizeToByteConverter(TO_BYTES.MB).convert(1));
        } catch (ConvertException e) {
            logger.error("转换异常！");
            e.printStackTrace();
        }
    }
}
