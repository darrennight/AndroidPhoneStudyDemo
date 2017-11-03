package zenghao.com.study.listActivityFragment.model;

import zenghao.com.study.listActivityFragment.base.BaseActivity;

public class Module {
    public String moduleName;
    public String moduleIcon;
    public Class<? extends BaseActivity> moduleTargetClass;
 
    public Module(String moduleName, Class<? extends BaseActivity> moduleTargetClass) {
        this.moduleName = moduleName;
        this.moduleTargetClass = moduleTargetClass;
    } 
} 