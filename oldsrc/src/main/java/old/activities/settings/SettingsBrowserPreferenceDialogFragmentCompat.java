package jdr.tvtracker.activities.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import jdr.tvtracker.R;

public class SettingsBrowserPreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {
    private SettingsBrowserPreference settingsBrowserPreference;

    private class BrowserDialogAdapter extends Adapter<ViewHolder> {
        private final List<ResolveInfo> browserList;
        private final Context context;
        private final String persistedBrowserComponentName;

        class BrowserViewHolder extends ViewHolder {
            final ImageView browserIcon;
            final TextView browserName;
            final ConstraintLayout constraintLayout;
            final RadioButton radioButton;

            BrowserViewHolder(View view) {
                super(view);
                this.constraintLayout = (ConstraintLayout) view.findViewById(R.id.browserDialogListViewConstraintLayout);
                this.radioButton = (RadioButton) view.findViewById(R.id.browserDialogListViewRadioButton);
                this.browserIcon = (ImageView) view.findViewById(R.id.browserDialogListViewBrowserIconImageView);
                this.browserName = (TextView) view.findViewById(R.id.browserDialogListViewBrowserNameTextView);
            }
        }

        BrowserDialogAdapter(Context context, String persistedBrowserComponentName) {
            this.context = context;
            this.persistedBrowserComponentName = persistedBrowserComponentName;
            this.browserList = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("https://google.com")), 131072);
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BrowserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_dialog_browser_list_view, parent, false));
        }

        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BrowserViewHolder browserViewHolder = (BrowserViewHolder) holder;
            ResolveInfo browser = (ResolveInfo) this.browserList.get(position);
            ActivityInfo activityInfo = browser.activityInfo;
            final String browserComponentName = new ComponentName(activityInfo.packageName, activityInfo.name).flattenToString();
            browserViewHolder.constraintLayout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SettingsBrowserPreferenceDialogFragmentCompat.this.settingsBrowserPreference.setPersistedBrowserComponentName(browserComponentName);
                    SettingsBrowserPreferenceDialogFragmentCompat.this.getDialog().cancel();
                }
            });
            String str = this.persistedBrowserComponentName;
            if (str != null && str.equals(browserComponentName)) {
                browserViewHolder.radioButton.setChecked(true);
            }
            browserViewHolder.radioButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SettingsBrowserPreferenceDialogFragmentCompat.this.settingsBrowserPreference.setPersistedBrowserComponentName(browserComponentName);
                    SettingsBrowserPreferenceDialogFragmentCompat.this.getDialog().cancel();
                }
            });
            browserViewHolder.browserIcon.setImageDrawable(browser.loadIcon(this.context.getPackageManager()));
            browserViewHolder.browserName.setText(browser.loadLabel(this.context.getPackageManager()));
        }

        public int getItemCount() {
            return this.browserList.size();
        }
    }

    public static SettingsBrowserPreferenceDialogFragmentCompat newInstance(String key) {
        SettingsBrowserPreferenceDialogFragmentCompat fragment = new SettingsBrowserPreferenceDialogFragmentCompat();
        Bundle bundle = new Bundle(1);
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        if (getPreference() instanceof SettingsBrowserPreference) {
            this.settingsBrowserPreference = (SettingsBrowserPreference) getPreference();
            RecyclerView recyclerView = (RecyclerView) view;
            BrowserDialogAdapter browserDialogAdapter = new BrowserDialogAdapter(view.getContext(), this.settingsBrowserPreference.getPersistedBrowserComponentName());
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(browserDialogAdapter);
        }
    }

    public void onDialogClosed(boolean positiveResult) {
    }
}
