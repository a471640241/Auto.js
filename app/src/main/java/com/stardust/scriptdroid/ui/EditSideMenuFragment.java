package com.stardust.scriptdroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stardust.scriptdroid.App;
import com.stardust.scriptdroid.DocumentActivity;
import com.stardust.scriptdroid.R;
import com.stardust.scriptdroid.droid.ConsoleActivity;
import com.stardust.scriptdroid.droid.assist.BoundsAssistant;
import com.stardust.view.ViewBinder;
import com.stardust.view.ViewBinding;

import static com.stardust.scriptdroid.ui.AssistModeSwitchNotification.KEY_ASSIST_MODE_NOTIFICATION;

/**
 * Created by Stardust on 2017/2/4.
 */

public class EditSideMenuFragment extends com.stardust.app.Fragment {


    private FunctionListRecyclerView.OnFunctionClickListener mOnFunctionClickListener;
    private AssistClipListRecyclerView.OnClipClickListener mOnClipClickListener;

    public static EditSideMenuFragment setFragment(AppCompatActivity activity, int viewId) {
        EditSideMenuFragment fragment = new EditSideMenuFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(viewId, fragment).commit();
        return fragment;
    }

    private SwitchCompat mAssistServiceSwitch, mAssistServiceNotificationSwitch;

    @Nullable
    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_side_menu, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAssistServiceSwitch == null) {
            setUpUI();
        }
        syncSwitchState();
    }

    private void setUpUI() {
        setUpSwitchCompat();
        setUpFunctionList();
        setUpClipList();
        ViewBinder.bind(this);
    }

    private void setUpClipList() {
        AssistClipListRecyclerView assistClipListRecyclerView = $(R.id.assist_clip_list);
        assistClipListRecyclerView.setOnClipClickListener(mOnClipClickListener);
    }

    private void setUpFunctionList() {
        FunctionListRecyclerView functionListRecyclerView = $(R.id.function_list);
        functionListRecyclerView.setOnFunctionClickListener(mOnFunctionClickListener);

    }

    private void syncSwitchState() {
        mAssistServiceSwitch.setChecked(BoundsAssistant.isAssistModeEnable());
        mAssistServiceNotificationSwitch.setChecked(AssistModeSwitchNotification.isEnable());
    }

    private void setUpSwitchCompat() {
        mAssistServiceSwitch = $(R.id.sw_assist_service);
        mAssistServiceNotificationSwitch = $(R.id.sw_assist_service_notification);
        App.getStateObserver().register(BoundsAssistant.KEY_ASSIST_MODE_ENABLE, mAssistServiceSwitch);
        App.getStateObserver().register(KEY_ASSIST_MODE_NOTIFICATION, mAssistServiceNotificationSwitch);
    }

    @ViewBinding.Click(R.id.syntax_and_api)
    private void startSyntaxHelpActivity() {
        startActivity(new Intent(getContext(), DocumentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @ViewBinding.Click(R.id.console)
    private void startConsoleActivity() {
        startActivity(new Intent(getContext(), ConsoleActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @ViewBinding.Check(R.id.sw_assist_service)
    private void setAssistServiceEnable(boolean enable) {
        BoundsAssistant.setAssistModeEnable(enable);
    }

    @ViewBinding.Click(R.id.assist_service)
    private void toggleAssistServiceSwitch() {
        mAssistServiceSwitch.toggle();
    }

    @ViewBinding.Check(R.id.sw_assist_service_notification)
    private void setAssistServiceNotificationEnable(boolean enable) {
        AssistModeSwitchNotification.setEnable(enable);
    }

    @ViewBinding.Click(R.id.assist_service_notification)
    private void toggleAssistServiceNotificationSwitch() {
        mAssistServiceNotificationSwitch.toggle();
    }

    public EditSideMenuFragment setOnFunctionClickListener(FunctionListRecyclerView.OnFunctionClickListener onFunctionClickListener) {
        mOnFunctionClickListener = onFunctionClickListener;
        return this;
    }

    public EditSideMenuFragment setOnClipClickListener(AssistClipListRecyclerView.OnClipClickListener onClipClickListener) {
        mOnClipClickListener = onClipClickListener;
        return this;
    }
}
