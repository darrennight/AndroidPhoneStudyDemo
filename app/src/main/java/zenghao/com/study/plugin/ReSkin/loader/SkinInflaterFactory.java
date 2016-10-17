package zenghao.com.study.plugin.ReSkin.loader;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import zenghao.com.study.plugin.ReSkin.attr.base.AttrFactory;
import zenghao.com.study.plugin.ReSkin.attr.base.DynamicAttr;
import zenghao.com.study.plugin.ReSkin.attr.base.SkinAttr;
import zenghao.com.study.plugin.ReSkin.config.SkinConfig;
import zenghao.com.study.plugin.ReSkin.entity.SkinItem;
import zenghao.com.study.plugin.ReSkin.utils.SkinL;
import zenghao.com.study.plugin.ReSkin.utils.SkinListUtils;

public class SkinInflaterFactory implements LayoutInflaterFactory {
 
    /**
     * 存储那些有皮肤更改需求的View及其对应的属性的集合 
     */ 
    private List<SkinItem> mSkinItems = new ArrayList<>();
 
    private AppCompatActivity mAppCompatActivity;
 
    @Override 
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
 
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);

        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
        View view = delegate.createView(parent, name, context, attrs);
 
        if (view instanceof TextView) {
            //字体替换存储
            TextViewRepository.add((TextView) view);
        } 
 
        if (isSkinEnable) {
            if (view == null) {
                view = ViewProducer.createViewFromTag(context, name, attrs);
            } 
            if (view == null) {
                return null; 
            } 
            parseSkinAttr(context, attrs, view);
        } 
        return view;
    } 
 
    public void setAppCompatActivity(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    } 
 
    /** 
     * 搜集可更换皮肤的属性 
     * 
     * @param context 
     * @param attrs 
     * @param view 
     */ 
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();//存储View可更换皮肤属性的集合
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //遍历当前View的属性
            String attrName = attrs.getAttributeName(i);//属性名
            String attrValue = attrs.getAttributeValue(i);//属性值
 

            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue; 
            }

            SkinL.i("Aattrname===", "Aattrname:" + attrName);
            if (attrValue.startsWith("@")) {//也就是引用类型，形如@color/red
                try { 
                    int id = Integer.parseInt(attrValue.substring(1));//资源的id
                    String entryName = context.getResources().getResourceEntryName(id);//入口名，例如text_color_selector
                    String typeName = context.getResources().getResourceTypeName(id);//类型名，例如color、background

                    if(entryName.startsWith("color_skin")){
                        //过滤资源文件
                        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                        if (mSkinAttr != null) {
                            viewAttrs.add(mSkinAttr);
                        }
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } 
            } 
        } 
        if (!SkinListUtils.isEmpty(viewAttrs)) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;
            mSkinItems.add(skinItem);
            if (SkinManager.getInstance().isExternalSkin()) {//如果当前皮肤来自于外部 
                skinItem.apply();
            } 
        } 
    } 
 
    /** 
     * 应用皮肤 
     */ 
    public void applySkin() { 
 
        if (SkinListUtils.isEmpty(mSkinItems)) {
            return; 
        } 
 
        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue; 
            } 
            si.apply();
        } 
    } 
 
    /** 
     * 清除有皮肤更改需求的View及其对应的属性的集合 
     */ 
    public void clean() { 
        if (SkinListUtils.isEmpty(mSkinItems)) {
            return; 
        } 
        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue; 
            } 
            si.clean();
        } 
    } 
 
    public void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    } 
 
    /** 
     * 动态添加那些有皮肤更改需求的View，及其对应的属性 
     * 
     * @param context 
     * @param view 
     * @param attrName       属性名 
     * @param attrValueResId 属性资源id 
     */ 
    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        skinItem.apply();
        addSkinView(skinItem);
    } 
 
    /** 
     * 动态添加那些有皮肤更改需求的View，及其对应的属性集合 
     * 
     * @param context 
     * @param view 
     * @param pDAttrs 
     */ 
    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
 
        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        } 
 
        skinItem.attrs = viewAttrs;
        skinItem.apply();
        addSkinView(skinItem);
    } 
 
 
    public void dynamicAddFontEnableView(TextView textView) {
        TextViewRepository.add(textView);
    } 
 
} 