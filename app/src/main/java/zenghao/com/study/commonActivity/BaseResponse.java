package zenghao.com.study.commonActivity;

public class BaseResponse<T> {
 
    private int status;//0成功，1失败
    private String msg;//失败说明
    private T data;//数据
 
    public BaseResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    } 
 
    public int getStatus() { 
        return status;
    } 
 
    public void setStatus(int status) {
        this.status = status;
    } 
 
    public String getMsg() {
        return msg;
    } 
 
    public void setMsg(String msg) {
        this.msg = msg;
    } 
 
    public T getData() { 
        return data;
    } 
 
    public void setData(T data) {
        this.data = data;
    } 
} 