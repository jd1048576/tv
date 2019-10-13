package jdr.tvtracker.activities.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import java.util.List;

import jdr.tvtracker.R;

public class SettingsBrowserPreference extends DialogPreference {
    private String persistedBrowserComponentName;

    public SettingsBrowserPreference(Context context) {
        this(context, null);
    }

    public SettingsBrowserPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.preferenceStyle);
    }

    public SettingsBrowserPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public SettingsBrowserPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDialogLayoutResource(R.layout.settings_dialog_browser);
        setNegativeButtonText("Cancel");
        setPositiveButtonText(null);
    }

    public String getPersistedBrowserComponentName() {
        return this.persistedBrowserComponentName;
    }

    public void setPersistedBrowserComponentName(String persistedBrowserComponentName) {
        this.persistedBrowserComponentName = persistedBrowserComponentName;
        persistString(persistedBrowserComponentName);
    }

    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            this.persistedBrowserComponentName = getPersistedString(getDefaultBrowserComponentName());
            return;
        }
        this.persistedBrowserComponentName = (String) defaultValue;
        persistString(this.persistedBrowserComponentName);
    }

    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    private String getDefaultBrowserComponentName() {
        List<ResolveInfo> resolveInfo = getContext().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("https://google.com")), 131072);
        if (resolveInfo == null || resolveInfo.size() <= 0) {
            return null;
        }
        return new ComponentName(((ResolveInfo) resolveInfo.get(0)).activityInfo.packageName, ((ResolveInfo) resolveInfo.get(0)).activityInfo.name).flattenToString();
    }
}
