package io.github.henryejemuta.journalapp.journals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.addjournal.AddJournalActivity;
import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.database.AppDatabase;
import io.github.henryejemuta.journalapp.journaldetails.JournalDetailActivity;

public class JournalsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, JournalsContract.View {
    private static final int REQUEST_ADD_JOURNAL = 1;

    private JournalsContract.UserActionsListener mActionsListener;

    private JournalAdapter mJournalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mJournalAdapter = new JournalAdapter(new ArrayList<Journal>(0), mItemListener);
        mActionsListener = new JournalsPresenter(AppDatabase.getInstance(this), this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_journals);
        fab.setOnClickListener(mActionsListener.getOnClickListener());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(mActionsListener.getOnRefreshListener());

    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionsListener.loadJournals(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a journal was successfully added, show Toast
        if (REQUEST_ADD_JOURNAL == requestCode && Activity.RESULT_OK == resultCode) {
            Toast.makeText(this, getString(R.string.successfully_saved_journal_message), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Show a gallery of all image current user have added to eJournal.", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_my_locations) {
            Toast.makeText(this, "Show a with markers on place current user have added to eJournal.", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share link to download eJournal App with friends.", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Share eJournal App APK with friends.", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Listener for clicks on journals in the RecyclerView.
     */
    JournalItemListener mItemListener = new JournalItemListener() {
        @Override
        public void onJournalClick(Journal clickedJournal) {
            mActionsListener.openJournalDetails(clickedJournal);
        }
    };

    @Override
    public void setProgressIndicator(final boolean active) {
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showJournals(List<Journal> journals) {
        mJournalAdapter.replaceData(journals);
    }

    @Override
    public void showAddJournal() {
        Intent intent = new Intent(this, AddJournalActivity.class);
        startActivityForResult(intent, REQUEST_ADD_JOURNAL);
    }

    @Override
    public void showJournalDetailUi(String journalId) {
        Intent intent = new Intent(this, JournalDetailActivity.class);
        intent.putExtra(JournalDetailActivity.EXTRA_JOURNAL_ID, journalId);
        startActivity(intent);
        finish();
    }
}
