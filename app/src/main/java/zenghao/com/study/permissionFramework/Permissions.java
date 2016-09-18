package zenghao.com.study.permissionFramework;

enum Permissions {
    GRANTED,//授权 确定
    DENIED,//拒绝
    NOT_FOUND,//此权限不再android权限范围内
    NO_LONGER_ASK//拒绝 不再询问
}