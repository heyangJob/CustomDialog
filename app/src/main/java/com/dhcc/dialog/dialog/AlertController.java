package com.dhcc.dialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.dhcc.dialog.R;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author 512573717@qq.com
 * @created 2019/3/29  下午2:48.
 */
public class AlertController {

    private CommonDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(CommonDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    /**
     * 获取Dialog的Window
     *
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 获取Dialog
     *
     * @return
     */
    public CommonDialog getDialog() {
        return mDialog;
    }


    public <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }


    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }


    public void setIcon(int viewId, int resouceId) {
        mViewHelper.setIcon(viewId, resouceId);
    }


    public void setIcon(int viewId, Drawable drawable) {
        mViewHelper.setIcon(viewId, drawable);
    }


    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnclickListener(viewId, listener);
    }


    public static class AlertParams {
        public Context mContext;
        // 点击空白是否能够取消  默认点击阴影可以取消
        public boolean mCancelable = true;
        // 设置主题
        public int mThemeResId;
        // dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog Dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // dialog Key监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        // 布局View
        public View mView;
        // 布局layout id
        public int mViewLayoutResId;
        // 宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 位置
        public int mGravity = Gravity.CENTER;
        // 显示动画
        public int mAnimations;
        public int mWindowFlags;
        public HashMap<Integer, DialogItem> mItems;

        private DialogViewHelper viewHelper;
        public CommonDialog mDialog;


        public AlertParams(Context context) {
            this.mContext = context;
        }


        public void setWidth(int width) {
            this.mWidth = width;
        }


        public void setHeight(int width) {
            this.mWidth = width;
        }


        public void setIcon(int id, int resouceId) {

            if (viewHelper != null) {
                viewHelper.setIcon(id, resouceId);
                return;
            }

            DialogItem item = findItem(id);
            item.setResourceId(resouceId);
            mItems.put(id, item);

        }

        /**
         * 第一次设置将Id和参数保存
         * 后面直接存缓存取
         *
         * @param id
         * @param drawable
         */
        public void setIcon(int id, Drawable drawable) {

            if (viewHelper != null) {
                viewHelper.setIcon(id, drawable);
                return;
            }

            DialogItem item = findItem(id);
            item.setDrawable(drawable);
            mItems.put(id, item);
        }


        public void setText(int id, CharSequence text) {
            if (viewHelper != null) {
                viewHelper.setText(id, text);
                return;
            }

            DialogItem item = findItem(id);
            item.setText(text);
            mItems.put(id, item);
        }


        public void setOnclickListener(int id, View.OnClickListener listener) {
            if (viewHelper != null) {
                viewHelper.setOnclickListener(id, listener);
                return;
            }

            DialogItem item = findItem(id);
            item.setListener(listener);
            mItems.put(id, item);
        }

        /**
         * 查找id 是否保存到HashMap
         *
         * @param id
         * @return
         */
        public DialogItem findItem(int id) {

            DialogItem item = null;
            if (mItems.containsKey(id)) {
                item = mItems.get(id);
            } else {
                item = new DialogItem();
            }
            return item;
        }


        public <T extends View> T getView(int viewId) {
            if (viewHelper != null) {
                return viewHelper.getView(viewId);
            }
            return null;
        }


        public void fromBottom(boolean isAnimation) {
            if (isAnimation) {
                mAnimations = R.style.dialog_from_bottom_anim;
            }
            mWindowFlags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            mGravity = Gravity.CENTER | Gravity.BOTTOM;
        }


        public void fromBottomToMiddle() {
            mAnimations = R.style.dialog_from_bottom_anim;
            mWindowFlags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        }


        public void dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }

        /**
         * 绑定和设置参数
         *
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            mDialog = mAlert.getDialog();
            // 1. 设置Dialog布局  DialogViewHelper
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }

            // 给Dialog 设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            // 设置 Controller的辅助类
            mAlert.setViewHelper(viewHelper);

            // 2.设置文本
            if (mItems != null && mItems.size() > 0) {
                Iterator<Integer> iter = mItems.keySet().iterator();

                while (iter.hasNext()) {
                    Integer viewId = iter.next();
                    DialogItem dialogItem = mItems.get(viewId);
                    // 设置文本
                    viewHelper.setText(viewId, dialogItem.getText());
                    // 设置点击
                    viewHelper.setOnclickListener(viewId, dialogItem.getListener());
                    // 设置图片
                    viewHelper.setIcon(viewId, dialogItem.getDrawable());
                    viewHelper.setIcon(viewId, dialogItem.getResourceId());
                }
                mItems.clear();
                mItems = null;
            }

            // 4.配置自定义的效果  全屏  从底部弹出    默认动画
            Window window = mAlert.getWindow();
            window.addFlags(mWindowFlags);
            // 设置位置
            window.setGravity(mGravity);
            // 设置动画
            if (mAnimations != 0) {
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }

    }
}
