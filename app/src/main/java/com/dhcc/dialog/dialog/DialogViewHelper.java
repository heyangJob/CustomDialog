package com.dhcc.dialog.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * @author 512573717@qq.com
 * @created 2019/3/29  下午2:47.
 * Dialog  辅助类
 */
public class DialogViewHelper {

    private View mContentView = null;
    // 防止霸气侧漏
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }


    public DialogViewHelper(Context context, int layoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }


    /**
     * 获取ContentView
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 设置布局View
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public DialogViewHelper setText(int viewId, CharSequence text) {
        // 每次都 findViewById   减少findViewById的次数
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
        return this;
    }


    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 设置图标
     */
    public DialogViewHelper setIcon(int viewId, int resouceId) {
        if (resouceId != 0) {
            View view = getView(viewId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(resouceId);
            } else {
                view.setBackgroundResource(resouceId);
            }
        }
        return this;
    }


    /**
     * 设置图标
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public DialogViewHelper setIcon(int viewId, Drawable drawable) {
        if (drawable != null) {
            View view = getView(viewId);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(drawable);
            } else {
                view.setBackground(drawable);
            }
        }
        return this;
    }


    /**
     * 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);

        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }

        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }


    /**
     * 显示指定的View
     *
     * @param viewId
     * @return
     */
    public DialogViewHelper setViewVisible(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }


    /**
     * 隐藏指定的View
     *
     * @param viewId
     * @return
     */
    public DialogViewHelper setViewGone(int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

}
