package cn.wandingkeji.auth.contants;


import lombok.Data;

@Data
public class ResultData<T> {

    private String code;
    private String msg;
    private String subCode;
    private String subMsg;

    private T data;

}
