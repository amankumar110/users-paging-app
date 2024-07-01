package in.amankumar110.usersapp.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AlertHelper {

    public static void alert(Context context,String title, String message,boolean cancellable, String positiveText, DialogInterface.OnClickListener listener,String negativeText, DialogInterface.OnClickListener negativeListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCancelable(cancellable);

        if (title!=null && !title.isEmpty()) {
            builder.setTitle(title);
        }

        if (message!=null && !message.isEmpty()) {
            builder.setMessage(message);
        }

        if (positiveText!=null && !positiveText.isEmpty() && listener!=null) {
            builder.setPositiveButton(positiveText, listener);
        }

        if (negativeText!=null && !negativeText.isEmpty() && negativeListener!=null) {
            builder.setNegativeButton(negativeText, negativeListener);
        }

        builder.show();
    }
}
