package com.android.cash1.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.android.cash1.R;
import com.android.cash1.activities.MainActivity;
import com.android.cash1.activities.login.LogoutActivity;
import com.android.cash1.activities.login.PrivacyPolicyActivity;
import com.android.cash1.activities.search.FindOfficeActivity;
import com.android.cash1.activities.support.ContactActivity;
import com.android.cash1.activities.support.FaqActivity;
import com.android.cash1.activities.support.SettingsActivity;
import com.android.cash1.activities.support.TermsActivity;
import com.google.gson.JsonObject;

public class Cash1Activity extends AppCompatActivity {

    private ViewGroup mFooterContainer;
    private TextView mFooterToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupActionBar();
        setupFooter();
    }

    public void setupActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.actionbar_layout,
                null);

        actionBarLayout.findViewById(R.id.button_logout).setVisibility(View.VISIBLE);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionBarLayout, lp);
        }
    }

    public void setupFooter() {
        mFooterContainer = (ViewGroup) findViewById(R.id.footer_container);

        mFooterToggle = (TextView) findViewById(R.id.footer_toggle);
        mFooterToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFooterContainer.getVisibility() == View.GONE) {
                    openFooter();
                } else {
                    closeFooter();
                }
            }
        });
    }

    public void logout(View view) {
        startActivity(new Intent(this, LogoutActivity.class));
    }

    public void openFooter() {
        // change toggle arrow from "up" to "down"
        mFooterToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        mFooterContainer.setVisibility(View.VISIBLE);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        mFooterContainer.startAnimation(slideUp);
        mFooterToggle.startAnimation(slideUp);
    }

    public void closeFooter() {
        // change toggle arrow from "down" to "up"
        mFooterToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);

        Animation toggleAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down_toggle);
        mFooterToggle.startAnimation(toggleAnimation);

        Animation containerAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        containerAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFooterContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFooterContainer.startAnimation(containerAnimation);
    }

    public void goHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void findOffice(View view) {
        startActivity(new Intent(this, FindOfficeActivity.class));
    }

    public void showFaq(View view) {
        startActivity(new Intent(this, FaqActivity.class));
    }

    public void navigateToMainMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void contactUs(View view) {
        startActivity(new Intent(this, ContactActivity.class));
    }

    public void openSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    @SuppressWarnings("unused")
    public void showGuarantee(View view) {
        showDialog(1, "I");
    }

    public int getUserId() {
        return PreferenceManager.getDefaultSharedPreferences(this).getInt("user_id", 0);
    }

    public String getUserEmail() {
         return PreferenceManager.getDefaultSharedPreferences(this).getString("username", "");
    }

    public int getStoreId() {
        return PreferenceManager.getDefaultSharedPreferences(this).getInt("store_id", 0);
    }

    public double getLastLatitude() {
        return PreferenceManager.getDefaultSharedPreferences(this).getFloat("latitude", -1);
    }

    public double getLastLongitude() {
        return PreferenceManager.getDefaultSharedPreferences(this).getFloat("longitude", -1);
    }

    public String getRedirectViewTitle() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("redirect_view", null);
    }

    public void showLoanInProgressDialog() {
        showDialog(5, "I");
    }

    public void showBasicLoginErrorDialog() {
        showDialog(-1, "general login error", true);
    }

    public void showBasicErrorDialog() {
        showDialog(-1, "general error", true);
    }

    public void callUs() {
        callUs(new View(this));
    }

    public void callUs(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:775-321-3566"));
        startActivity(intent);
    }

    public boolean rememberMe() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("remember", false);
    }

    public boolean useCurrentLocation() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("use_location", false);
    }

    public void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("unused")
    public void showSecurityQuestionPopup() {
        showDialog(7, "E", true);
    }

    public void showIncorrectUsernameOrPasswordPopup() {
        showDialog(34, "E", false);
    }

    /**
     * Helper methods to show popup dialogs
     * @param messageId code of the message to be displayed
     * @param dialogTypeCode specifies "info" or "error" type of the dialog
     */
    public void showDialog(int messageId, String dialogTypeCode) {
        showDialog(messageId, dialogTypeCode, false);
    }

    public void showDialog(int messageId, String dialogTypeCode, boolean showCallUsButton) {
        DialogFragment dialog = new InfoDialogFragment();

        Bundle args = new Bundle();
        args.putInt("dialog_id", messageId);
        args.putString("message_type", dialogTypeCode);
        args.putString("btn_cancel_label", getString(R.string.close_button_label));
        if (showCallUsButton) {
            args.putString("btn_confirm_label", getString(R.string.call_us_button_label));
            args.putString("btn_cancel_label", "Exit");
        }
        dialog.setArguments(args);

        dialog.show(getSupportFragmentManager(), "dialog");
    }

    public String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getDeviceId() != null) {
            return telephonyManager.getDeviceId().substring(0, 9);
        } else {
            return Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID).replaceAll("\\D+","").substring(0, 9);
        }
    }

    public void showMismatchPopup() {
        showDialog(30, "E", true);
    }

    public void showAttemptsExceededPopup() {
        showDialog(3, "E", true);
    }

    public void showCreditDenialPopup() {
        showDialog(6, "I", true);
    }

    public void showCallUsPopup() {
        showDialog(24, "E", true);
    }

    public void showValidateErrorPopup() {
        showDialog(8, "E", true);
    }

    public void showQuickGuide(View view) {
        showDialog(29, "I");
    }

    public String getStringFromPrimitive(JsonObject obj, String key) {
        try {
            return obj.getAsJsonPrimitive(key).getAsString();
        } catch (ClassCastException e) {
            return "null";
        }
    }

    public String formatNumber(String number) {
        if (number.replaceAll("[^\\d.]", "").length() < 10) {
            return number;
        }
        number = number.replaceAll("[^\\d.]", "");
        return "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6, 10);
    }

    public void navigateToPrivacyPolicyActivity(View view) {
        startActivity(new Intent(this, PrivacyPolicyActivity.class));
    }

    public void navigateToTermsActivity(View view) {
        startActivity(new Intent(this, TermsActivity.class));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mFooterContainer != null && mFooterContainer.getVisibility() == View.VISIBLE) {
            closeFooter();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            if (mFooterContainer != null ) {
                if (mFooterContainer.getVisibility() == View.VISIBLE) {
                    closeFooter();
                } else {
                    openFooter();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exitPopupUpdateInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog, null);
        builder.setView(rootView);

        rootView.findViewById(R.id.spinner).setVisibility(View.GONE);
        TextView titleView = (TextView) rootView.findViewById(R.id.title);
        titleView.setVisibility(View.VISIBLE);
        titleView.setText("Update Not Saved");
        TextView messageView = (TextView) rootView.findViewById(R.id.body);
        messageView.setVisibility(View.VISIBLE);
        messageView.setText("You have not completed your update of information. Are you sure you " +
                "want to log out? Your account update information will not be saved until the " +
                "update has been completed.");

        final AlertDialog dialog = builder.create();

        Button confirmButton = (Button) rootView.findViewById(R.id.confirm);
        confirmButton.setVisibility(View.VISIBLE);
        confirmButton.setText("Log Out");
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cash1Activity.this, LogoutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        Button cancelButton = (Button) rootView.findViewById(R.id.cancel);
        cancelButton.setVisibility(View.VISIBLE);
        cancelButton.setText("Return to Update");
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
