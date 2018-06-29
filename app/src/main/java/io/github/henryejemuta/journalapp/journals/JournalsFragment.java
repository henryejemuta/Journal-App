package io.github.henryejemuta.journalapp.journals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.addjournal.AddJournalActivity;
import io.github.henryejemuta.journalapp.common.Journal;
import io.github.henryejemuta.journalapp.journaldetails.JournalDetailActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class JournalsFragment extends Fragment implements JournalsContract.View {
    private static final int REQUEST_ADD_JOURNAL = 1;

    private JournalsContract.UserActionsListener mActionsListener;

    private JournalsAdapter mListAdapter;

    public JournalsFragment() {
        // Requires empty public constructor
    }

    public static JournalsFragment newInstance() {
        return new JournalsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new JournalsAdapter(new ArrayList<Journal>(0), mItemListener);
//        mActionsListener = new JournalsPresenter(Injection.provideJournalsRepository(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionsListener.loadJournals(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If a journal was successfully added, show snackbar
        if (REQUEST_ADD_JOURNAL == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), getString(R.string.successfully_saved_journal_message),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.journals_view, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rv_journal_list);
        recyclerView.setAdapter(mListAdapter);

        int numColumns = getContext().getResources().getInteger(R.integer.num_journals_columns);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numColumns));

        // Set up floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_j_add_journal);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionsListener.addNewJournal();
            }
        });

        // Pull-to-refresh
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.srl_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionsListener.loadJournals(true);
            }
        });
        return root;
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

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.srl_refresh_layout);

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
        mListAdapter.replaceData(journals);
    }

    @Override
    public void showAddJournal() {
        Intent intent = new Intent(getContext(), AddJournalActivity.class);
        startActivityForResult(intent, REQUEST_ADD_JOURNAL);
    }

    @Override
    public void showJournalDetailUi(String journalId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        Intent intent = new Intent(getContext(), JournalDetailActivity.class);
        intent.putExtra(JournalDetailActivity.EXTRA_JOURNAL_ID, journalId);
        startActivity(intent);
    }


    private static class JournalsAdapter extends RecyclerView.Adapter<JournalsAdapter.ViewHolder> {

        private List<Journal> mJournals;
        private JournalItemListener mItemListener;

        public JournalsAdapter(List<Journal> journals, JournalItemListener itemListener) {
            setList(journals);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View journalView = inflater.inflate(R.layout.journal_item_view, parent, false);

            return new ViewHolder(journalView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Journal journal = mJournals.get(position);

            viewHolder.title.setText(journal.getTitle());
            viewHolder.description.setText(journal.getDescription());
        }

        public void replaceData(List<Journal> journals) {
            setList(journals);
            notifyDataSetChanged();
        }

        private void setList(List<Journal> journals) {
            mJournals = checkNotNull(journals);
        }

        @Override
        public int getItemCount() {
            return mJournals.size();
        }

        public Journal getItem(int position) {
            return mJournals.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView title;

            public TextView description;
            private JournalItemListener mItemListener;

            public ViewHolder(View itemView, JournalItemListener listener) {
                super(itemView);
                mItemListener = listener;
                title = (TextView) itemView.findViewById(R.id.tv_jd_title);
                description = (TextView) itemView.findViewById(R.id.tv_jd_description);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Journal journal = getItem(position);
                mItemListener.onJournalClick(journal);

            }
        }
    }

    public interface JournalItemListener {

        void onJournalClick(Journal clickedJournal);
    }

}
