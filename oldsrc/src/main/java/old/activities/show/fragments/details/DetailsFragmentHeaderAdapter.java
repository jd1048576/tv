package jdr.tvtracker.activities.show.fragments.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jdr.tvtracker.R;
import jdr.tvtracker.data.entities.Cast;
import jdr.tvtracker.utils.GlideApp;
import jdr.tvtracker.utils.GlideRequest;
import jdr.tvtracker.utils.GlideRequests;

class DetailsFragmentHeaderAdapter extends Adapter<ViewHolder> {
    private final List<Cast> cast;
    private final Context context;

    class PersonViewHolder extends ViewHolder {
        final TextView character;
        final TextView name;
        final ImageView profile;

        PersonViewHolder(View view) {
            super(view);
            this.profile = (ImageView) view.findViewById(R.id.detailsFragmentHeaderListViewProfileImageView);
            this.name = (TextView) view.findViewById(R.id.detailsFragmentHeaderListViewNameTextView);
            this.character = (TextView) view.findViewById(R.id.detailsFragmentHeaderListViewCharacterTextView);
        }
    }

    DetailsFragmentHeaderAdapter(Context context, List<Cast> cast) {
        this.context = context;
        this.cast = cast;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_fragment_details_header_list_view_update, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonViewHolder profileViewHolder = (PersonViewHolder) holder;
        Cast profile = (Cast) this.cast.get(position);
        GlideRequests with = GlideApp.with(this.context);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getString(R.string.high_res_profile_url));
        stringBuilder.append(profile.getProfilePath());
        GlideRequest placeholder = with.load(stringBuilder.toString()).placeholder((int) R.drawable.ic_placeholder_profile);
        GlideRequests with2 = GlideApp.with(this.context);
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.context.getString(R.string.low_res_profile_url));
        stringBuilder2.append(profile.getProfilePath());
        placeholder.thumbnail(with2.load(stringBuilder2.toString())).into(profileViewHolder.profile);
        profileViewHolder.name.setText(profile.getName());
        profileViewHolder.character.setText(profile.getCharacter());
    }

    public int getItemCount() {
        return this.cast.size();
    }
}
