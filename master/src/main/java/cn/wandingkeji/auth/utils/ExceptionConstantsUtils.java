package cn.wandingkeji.auth.utils;

import cn.wandingkeji.auth.contants.ResultData;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

public class ExceptionConstantsUtils {

    /**
     * 打印错误信息
     * @param log
     * @param e
     */
    public static ResultData printErrorMessage(Logger log, Exception e, String printMsg) {
        String message = ExceptionUtils.getStackTrace(e);
        log.info(printMsg);
        log.info(message);
        if(printMsg == null) {
            return ConstantUtils.printErrorMessage();
        }
        return ConstantUtils.printErrorMessage(printMsg);
    }

    public static ResultData printErrorMessage(Logger log, String printMsg) {
        log.info(printMsg);
        if(printMsg == null) {
            return ConstantUtils.printErrorMessage();
        }
        return ConstantUtils.printErrorMessage(printMsg);
    }

    public static ResultData printSuccessMessage(Logger log, String printMsg) {
        log.info(printMsg);
        if(printMsg == null) {
            return ConstantUtils.printErrorMessage();
        }
        return ConstantUtils.printSuccessMessage(printMsg, null);
    }

    public static <T> ResultData printSuccessMessage(Logger log, String printMsg , T data) {
        log.info(printMsg);
        if(printMsg == null) {
            return ConstantUtils.printErrorMessage();
        }
        return ConstantUtils.printSuccessMessage(printMsg, data);
    }

    /**
     * 打印错误信息
     * @param log
     * @param e
     */
    public static void printSysErrorMessage(Logger log, Exception e, String printMsg) {
        String message = ExceptionUtils.getStackTrace(e);
        log.info(message);
        log.info(printMsg);
    }

    /**
     * 打印错误信息
     * @param log
     * @param printMsg
     */
    public static void printSysErrorMessage(Logger log, String printMsg) {
        log.info(printMsg);
        log.info(printMsg);
    }

}
