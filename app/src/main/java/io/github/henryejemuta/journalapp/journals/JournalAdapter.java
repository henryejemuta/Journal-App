package io.github.henryejemuta.journalapp.journals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.henryejemuta.journalapp.R;
import io.github.henryejemuta.journalapp.common.Journal;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder> {

    private List<Journal> mJournals;
    private JournalItemListener mItemListener;

    public JournalAdapter(List<Journal> journals, JournalItemListener itemListener) {
        setList(journals);
        mItemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View journalView = inflater.inflate(R.layout.journal_item_view, parent, false);

        return new ViewHolder(journalView, mItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
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
            title = (TextView) itemView.findViewById(R.id.journal_detail_title);
            description = (TextView) itemView.findViewById(R.id.journal_detail_description);
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
