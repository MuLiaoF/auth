package cn.wandingkeji.auth.utils;

import cn.wandingkeji.auth.contants.ResultData;
import cn.wandingkeji.auth.contants.ResultEnum;
import com.baomidou.mybatisplus.plugins.Page;

public class ConstantUtils {


    public static final String SUCCESS_CODE = "000000";

    public static final String ERROR_CODE = "100000";

    public static final String SUCCESS_MESSAGE = "SUCCESS";

    public static final String ERROR_MESSAGE = "ERROR";

    public static final String MD5_TYPE = "md5";

    public static final String TOP_COMPANY = "1";



    public static <T> ResultData<T> printMessage(String code, String msg, String subMsg,T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setSubCode(code);
        resultData.setSubMsg(subMsg);
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> printSuccessMessage(String message, T data) {
        return printMessage(SUCCESS_CODE, SUCCESS_MESSAGE, message, data);
    }

    public static <T> ResultData<T> printErrorMessage(String message) {
        return printMessage(ERROR_CODE, ERROR_MESSAGE , message, null);
    }


    public static <T> ResultData<T> printErrorMessage() {
        return printErrorMessage("网络出现异常");
    }

    /**
     * 返回对象
     */
    public static <T> ResultData<T> Success(Object obj, ResultEnum resultEnum) {
        /*ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setData(obj);
        resultVO.setMsg(resultEnum.getMsg());
        resultVO.setCode(resultEnum.getCode());*/
        return null;
    }

    /**
     * 返回对象
     */
    public static <T> ResultData<T> Success(Object obj) {
       /* ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setData(obj);
        resultVO.setMsg(ResultEnum.HANDLE_SUCCESS.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_SUCCESS.getCode());*/
        return null;
    }

    /**
     * 返回成功信息
     */
    public static <T> ResultData<T> Success(ResultEnum resultEnum) {
        return Success(null, resultEnum);
    }

    /**
     * 返回成功信息
     */
    public static <T> ResultData<T> Success() {
        /*ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setMsg(ResultEnum.HANDLE_SUCCESS.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_SUCCESS.getCode());*/
        return null;
    }

    /**
     * 返回错误信息
     */
    public static <T> ResultData<T> Error(ResultEnum resultEnum) {
        /*ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());*/
        return null;
    }

    public static <T> ResultData<T> Error(String code, String msg) {
       /* ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);*/
        return null;
    }

    /**
     * 返回分页对象
     */
    public static <T> ResultData<T> TableSuccess(Page page) {
       /* ResultVO<Object> resultVO = new ResultVO<>();

        TableVO<List> tableVO = new TableVO<>();
        tableVO.setPage(page.getCurrent());
        tableVO.setTotal((int) page.getTotal());
        tableVO.setRows(page.getRecords());

        resultVO.setData(tableVO);
        resultVO.setMsg(ResultEnum.HANDLE_SUCCESS.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_SUCCESS.getCode());*/
        return null;
    }

    /**
     * 返回错误信息
     */
    public static <T> ResultData<T> HandlerError(String code, String msg) {
        /*ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);*/
        return null;
    }

}
