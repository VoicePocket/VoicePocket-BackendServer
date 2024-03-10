package com.vp.voicepocket.global.common.response;

import com.vp.voicepocket.global.common.response.model.CommonResult;
import com.vp.voicepocket.global.common.response.model.ListResult;
import com.vp.voicepocket.global.common.response.model.SingleResult;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {

    public static <T> SingleResult<T> createSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public static <T> ListResult<T> createListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;
    }

    public static CommonResult createSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public static CommonResult createFailResult(int code, String message) {
        CommonResult result = new CommonResult();
        setFailResult(result, code, message);
        return result;
    }

    private static void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonCode.SUCCESS.getCode());
        result.setMessage(CommonCode.SUCCESS.getMessage());
    }

    private static void setFailResult(CommonResult result, int code, String message) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
    }

}
