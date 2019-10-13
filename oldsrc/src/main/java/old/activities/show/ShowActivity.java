package jdr.tvtracker.activities.show;

import android.app.AlertDialog.Builder;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

import jdr.tvtracker.R;
import jdr.tvtracker.activities.show.fragments.details.DetailsFragment;
import jdr.tvtracker.data.entities.Show;
import jdr.tvtracker.utils.IntentSender;

public class ShowActivity extends AppCompatActivity {
    private Show show;
    private ShowActivityViewModel showActivityViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);
        setupToolbar();
        this.showActivityViewModel = (ShowActivityViewModel) ViewModelProviders.of(this).get(ShowActivityViewModel.class);
        Intent intent = getIntent();
        int id = (intent.getAction() == null || !intent.getAction().equals("android.intent.action.VIEW") || intent.getData() == null) ? intent.getIntExtra("id", 0) : Integer.parseInt(intent.getData().getLastPathSegment());
        observeLiveData(id);
        if (savedInstanceState == null) {
            setupFragment();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.launchSettings) {
            showLaunchSettingsDialog();
            return true;
        } else if (itemId != R.id.share) {
            return super.onOptionsItemSelected(item);
        } else {
            new IntentSender(this).share(this.show.getName(), this.show.getId());
            return true;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void observeLiveData(int id) {
        this.showActivityViewModel.setId(id);
        this.showActivityViewModel.getShow().observe(this, new Observer<Show>() {
            public void onChanged(@Nullable Show showObserved) {
                if (showObserved != null) {
                    ShowActivity.this.show = showObserved;
                }
            }
        });
    }

    private void setupFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new DetailsFragment());
        transaction.commit();
    }

    private void showLaunchSettingsDialog() {
        Show show = this.show;
        if (show == null || !show.isFromDB()) {
            View findViewById = findViewById(R.id.coordinatorLayout);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Add ");
            stringBuilder.append(this.show.getName());
            stringBuilder.append(" to Change Launch Settings");
            Snackbar.make(findViewById, stringBuilder.toString(), 0).show();
            return;
        }
        findViewById = View.inflate(this, R.layout.dialog_launch_settings, null);
        EditText editText = (EditText) findViewById.findViewById(R.id.dialogLaunchSettingsEditText);
        CheckBox defaultCheckBox = (CheckBox) findViewById.findViewById(R.id.dialogLaunchSettingsDefaultCheckBox);
        CheckBox netflixCheckBox = (CheckBox) findViewById.findViewById(R.id.dialogLaunchSettingsNetflixCheckBox);
        CheckBox amazonCheckBox = (CheckBox) findViewById.findViewById(R.id.dialogLaunchSettingsAmazonCheckBox);
        CheckBox customCheckBox = (CheckBox) findViewById.findViewById(R.id.dialogLaunchSettingsCustomCheckBox);
        if (this.show.getLaunchId() != null) {
            editText.setText(this.show.getLaunchId());
            editText.setSelection(editText.getText().length());
        }
        final CheckBox checkBox = defaultCheckBox;
        final CheckBox checkBox2 = netflixCheckBox;
        final CheckBox checkBox3 = amazonCheckBox;
        final CheckBox checkBox4 = customCheckBox;
        setCheckBox(this.show.getLaunchSource(), checkBox, checkBox2, checkBox3, checkBox4);
        defaultCheckBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowActivity.this.setCheckBox(0, checkBox, checkBox2, checkBox3, checkBox4);
            }
        });
        netflixCheckBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowActivity.this.setCheckBox(1, checkBox, checkBox2, checkBox3, checkBox4);
            }
        });
        amazonCheckBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowActivity.this.setCheckBox(2, checkBox, checkBox2, checkBox3, checkBox4);
            }
        });
        customCheckBox.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowActivity.this.setCheckBox(3, checkBox, checkBox2, checkBox3, checkBox4);
            }
        });
        final EditText editText2 = editText;
        checkBox = netflixCheckBox;
        checkBox2 = amazonCheckBox;
        checkBox3 = customCheckBox;
        new Builder(this).setTitle(R.string.launch_settings).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                ShowActivity.this.show.setLaunchId(editText2.getText().toString());
                Show access$000 = ShowActivity.this.show;
                int i = checkBox.isChecked() ? 1 : checkBox2.isChecked() ? 2 : checkBox3.isChecked() ? 3 : 0;
                access$000.setLaunchSource(i);
                ShowActivity.this.showActivityViewModel.updateShow();
                Snackbar.make(ShowActivity.this.findViewById(R.id.coordinatorLayout), "Saved Launch Settings", 0).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id) {
                dialogInterface.cancel();
            }
        }).setView(findViewById).create().show();
    }

    private void setCheckBox(int launchSource, CheckBox defaultCheckBox, CheckBox netflixCheckBox, CheckBox amazonCheckBox, CheckBox customCheckBox) {
        boolean z = false;
        defaultCheckBox.setChecked(launchSource == 0);
        netflixCheckBox.setChecked(launchSource == 1);
        amazonCheckBox.setChecked(launchSource == 2);
        if (launchSource == 3) {
            z = true;
        }
        customCheckBox.setChecked(z);
    }
}
