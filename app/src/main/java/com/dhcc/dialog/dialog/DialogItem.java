package com.dhcc.dialog.dialog;

import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;

/**
 * @author 512573717@qq.com
 * @created 2019/3/29  下午4:29.
 * 一个View对应的内容（设置文字 点击事件 图片）
 */
public class DialogItem {
    private CharSequence text;
    private OnClickListener listener;
    private int resourceId;
    private Drawable drawable;

    public void setText(CharSequence text) {
        this.text = text;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public CharSequence getText() {
        return text;
    }

    public OnClickListener getListener() {
        return listener;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
