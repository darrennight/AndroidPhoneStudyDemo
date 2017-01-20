package zenghao.com.study.commonActivity;

import rx.Observable;

public class WebModel {

    /**
     * 假设修改名字时，每次都成功
     */
    public static Observable<BaseResponse> modifyName(String content) {
        BaseResponse response = new BaseResponse(0, "", null);
        return Observable.just(response);
    }

    /**
     * 假设修改地址时，每次都失败
     */
    public static Observable<BaseResponse> modifyAddress(String content) {
        BaseResponse response = new BaseResponse(1, "", null);
        return Observable.just(response);
    }

    /**
     * 假设修改签名时，content长度为奇数就成功，偶数就失败
     */
    public static Observable<BaseResponse> modifyDesc(String content) {
        int status = content.length() % 2 == 1 ? 0 : 1;
        BaseResponse response = new BaseResponse(status, "", null);
        return Observable.just(response);
    }
}