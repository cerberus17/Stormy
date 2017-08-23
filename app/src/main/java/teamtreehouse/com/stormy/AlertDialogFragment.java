package teamtreehouse.com.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by akipnis on 8/9/2017.
 */

public class AlertDialogFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Context context = getActivity();

    AlertDialog.Builder builder = new AlertDialog.Builder(context);

    builder.setTitle(R.string.ALERT_TITLE);
    builder.setMessage(R.string.ALERT_MESSAGE);
    builder.setPositiveButton(R.string.OK, null);

    return builder.create();
  }
}
