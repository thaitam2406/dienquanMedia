package com.dienquan.tieulam.customizeUI;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.leftorright.lor.R;


public class CustomDialog extends Dialog {
    Context mContext;

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }

    public CustomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private View separatorView;
        private View iconDialog;

        private OnClickListener positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIcon(View iconDialog) {
            try {
                this.iconDialog = iconDialog;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return this;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            try {
                this.message = message;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            try {
                this.message = (String) context.getText(message);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            try {
                this.title = (String) context.getText(title);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            try {
                this.title = title;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return this;
        }

        /**
         * Set a custom content view for the Dialog. If a message is set, the
         * contentView is not added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            try {
                this.positiveButtonText = (String) context
                        .getText(positiveButtonText);
                this.positiveButtonClickListener = listener;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            try {
                this.positiveButtonText = positiveButtonText;
                this.positiveButtonClickListener = listener;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            try {
                this.negativeButtonText = (String) context
                        .getText(negativeButtonText);
                this.negativeButtonClickListener = listener;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            try {
                this.negativeButtonText = negativeButtonText;
                this.negativeButtonClickListener = listener;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            return this;
        }

        public void hideSaperatorView() {
            separatorView.setVisibility(View.GONE);
        }

        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
            View layout = inflater.inflate(R.layout.dialog_custom, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            // set the dialog title
            try {
                ((TextViewRipple) layout.findViewById(R.id.title)).setText(title);
            } catch (Exception e) {
                // TODO: handle exception
            }
            separatorView = layout.findViewById(R.id.separator);
            // set the confirm button
            if (positiveButtonText != null) {
                ((TextViewRipple) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.positiveButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((TextViewRipple) layout.findViewById(R.id.negativeButton))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.negativeButton)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(
                                            dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextViewRipple) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.message))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.message))
                        .addView(contentView,
                                new LayoutParams(
                                        LayoutParams.WRAP_CONTENT,
                                        LayoutParams.WRAP_CONTENT));
            }
            dialog.setContentView(layout);
//			dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.mipmap.ic_relay);
            return dialog;

        }


    }


}

