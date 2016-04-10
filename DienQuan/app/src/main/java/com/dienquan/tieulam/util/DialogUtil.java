package com.dienquan.tieulam.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.leftorright.lor.R;
import com.leftorright.lor.customizeView.CustomDialog;


public class DialogUtil {

    private static CustomDialog mCustomDialog;

    public static void showOptionStringDialog(final Context ctx, String title,
                                              String sms, String btnTextOK, String btnTextCancel, final
                                              DIALOG_MODE mode, final IDialogListener listener) {
        CustomDialog.Builder customBuilder = new CustomDialog.Builder(ctx);
        customBuilder.setTitle(title).setMessage(sms);

        switch (mode) {

            default:
                break;
        }
        try {
            Dialog dialog = customBuilder.create();
            if (mode == DIALOG_MODE.UNAVAI_ISSUE_MODE
                    || mode == DIALOG_MODE.PURCHASE_BRAINTREE_SUCCESS
                    || mode == DIALOG_MODE.DIALOG_NOTIFY
                    || mode == DIALOG_MODE.DIALOG_MESSAGE_ERROR
                    || mode == DIALOG_MODE.DIALOG_MESSAGE_FINISHED) {

                customBuilder.hideSaperatorView();

            }
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    public enum DIALOG_MODE {
        LOGOUT_MODE, ADD_ACC_GG_MODE, ADD_ACC_ON_APP, PURCHASE_BRAINTREE_SUCCESS,
        UNAVAI_ISSUE_MODE, NOINTERNET_MODE, ERROR_DOWNLOADING_ISSUE_MODE, WARNING_LOGIN_MODE,
        CONFIRM_PURCHASE_BRAINTREE, DIALOG_NOTIFY, LOGIN_MODE, LOGIN_GOOGLE_MODE, DIALOG_DELETE_ISSUE,
        DIALOG_PURCHASE_CREDIT, DIALOG_MESSAGE_ERROR, DIALOG_MESSAGE_FINISHED
    }

    public interface ILogoutAction {
        void logoutAction();
    }


    public interface IDialogListener {
        void onConfirmDialog(DIALOG_MODE mode);
    }

    public static void showWarningLoginRequest(Context ctx) {
        if (mCustomDialog == null) {
            CustomDialog.Builder builder = new CustomDialog.Builder(ctx);
            builder.setTitle(ctx.getString(R.string.text_function_not_available));
            builder.setMessage(ctx.getString(R.string.text_login_request));
            builder.setPositiveButton(ctx.getString(R.string.OKText), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            mCustomDialog = builder.create();
            builder.hideSaperatorView();
        }
        if (!mCustomDialog.isShowing()) {
            mCustomDialog.show();
        }
    }

}
