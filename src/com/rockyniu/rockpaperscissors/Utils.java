package com.rockyniu.rockpaperscissors;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

public class Utils {

	/**
	 * Logs the given throwable and shows an error alert dialog with its
	 * message.
	 * 
	 * @param activity
	 *            activity
	 * @param tag
	 *            log tag to use
	 * @param t
	 *            throwable to log and show
	 */
	public static void logAndShow(Activity activity, String tag, Throwable t) {
		Log.e(tag, "Error", t);
		String message = t.getMessage();
//		if (t instanceof GoogleJsonResponseException) {
//			GoogleJsonError details = ((GoogleJsonResponseException) t)
//					.getDetails();
//			if (details != null) {
//				message = details.getMessage();
//			}
//		} else if (t.getCause() instanceof GoogleAuthException) {
//			message = ((GoogleAuthException) t.getCause()).getMessage();
//		} else if (t instanceof IOException) {
//			if (t.getMessage() == null) {
//				message = "IOException";
//			}
//		}
		showError(activity, message);
	}

	/**
	 * Shows an error alert dialog with the given message.
	 * 
	 * @param activity
	 *            activity
	 * @param message
	 *            message to show or {@code null} for none
	 */
	public static void showError(Activity activity, String message) {
		String errorMessage = getErrorMessage(activity, message);
		showErrorInternal(activity, errorMessage);
	}

	private static String getErrorMessage(Activity activity, String message) {
		Resources resources = activity.getResources();
		if (message == null) {
			return resources.getString(R.string.error);
		}
		return resources.getString(R.string.error_format, message);
	}

	private static void showErrorInternal(final Activity activity,
			final String errorMessage) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
						.show();
			}
		});
	}
	
	
	/**
	 * Shows an alert dialog with the given message.
	 * click is need to dismiss the message.
	 * 
	 * @param activity
	 *            activity
	 * @param message
	 *            message to show or {@code null} for none
	 * @param title
	 * 			  title of dialog
	 */
	private static void showNeedClickDialog(Activity activity, String message, String title){
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alertDialog.show();
		return;
	}
	
}