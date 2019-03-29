##### CustomDialog

自定义万能对话框

##### 简单使用
```
// 弹出dialog  从底部并且带动画
CommonDialog.Builder builder = new CommonDialog.Builder(this);
builder.setContentView(R.layout.dialog_customer_keyboard).fromBottom(true).fullWidth().create();
builder.show();

```
