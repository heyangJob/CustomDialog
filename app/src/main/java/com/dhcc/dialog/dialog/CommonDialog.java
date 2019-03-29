package com.dhcc.dialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.dhcc.dialog.R;

/**
 * @author 512573717@qq.com
 * @created 2019/3/29  下午2:47.
 */
public class CommonDialog extends Dialog {

    private AlertController mAlert;


    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }


    /**
     * 设置icon
     *
     * @param viewId
     * @param resouceId
     */
    public void setIcon(int viewId, int resouceId) {
        mAlert.setIcon(viewId, resouceId);
    }


    public void setIcon(int viewId, Drawable drawable) {
        mAlert.setIcon(viewId, drawable);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnclickListener(viewId, listener);
    }


    public static class Builder {

        private final AlertController.AlertParams P;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }


        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context);
            P.mThemeResId = themeResId;
        }


        public CommonDialog create() {
            CommonDialog dialog = new CommonDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public Context getContext() {
            return P.mContext;
        }


        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        // 设置布局内容的layoutId
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }


        public Builder setIcon(int viewId, int resouceId) {
            P.setIcon(viewId, resouceId);
            return this;
        }


        public Builder setIcon(int viewId, Drawable icon) {
            P.setIcon(viewId, icon);
            return this;
        }

        // 设置文本
        public Builder setText(int viewId, CharSequence text) {
            P.setText(viewId, text);
            return this;
        }


        // 设置点击事件
        public Builder setOnClickListener(int view, View.OnClickListener listener) {
            P.setOnclickListener(view, listener);
            return this;
        }

        //  点击阴影部分是否消失
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setWidth(int width) {
            P.mWidth = width;
            return this;
        }


        public Builder setHeight(int height) {
            P.mHeight = height;
            return this;
        }


        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }


        public <T extends View> T getView(int viewId) {
            return P.getView(viewId);
        }

        // 配置一些万能的参数
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }


        public void dismiss() {
            P.dismiss();
        }


        public Builder fromBottomToMiddle() {
            P.fromBottomToMiddle();
            return this;
        }


        /**
         * 从底部弹出
         *
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder fromBottom(boolean isAnimation) {
            P.fromBottom(isAnimation);
            return this;
        }

        /**
         * 添加默认动画
         *
         * @return
         */
        public Builder addDefaultAnimation() {
            P.mAnimations = R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         *
         * @param styleAnimation
         * @return
         */
        public Builder setAnimations(int styleAnimation) {
            P.mAnimations = styleAnimation;
            return this;
        }


        public CommonDialog show() {
            CommonDialog dialog;
            if (P.mDialog == null) {
                dialog = create();
            } else {
                dialog = P.mDialog;
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
            return dialog;
        }
    }
}
