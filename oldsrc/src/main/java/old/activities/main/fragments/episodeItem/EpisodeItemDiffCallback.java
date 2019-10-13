package jdr.tvtracker.activities.main.fragments.episodeItem;

import android.support.v7.util.DiffUtil.Callback;

import java.util.List;

class EpisodeItemDiffCallback extends Callback {
    private final List<Item> newItemList;
    private final List<Item> oldItemList;

    EpisodeItemDiffCallback(List<Item> oldItemList, List<Item> newItemList) {
        this.oldItemList = oldItemList;
        this.newItemList = newItemList;
    }

    public int getOldListSize() {
        return this.oldItemList.size();
    }

    public int getNewListSize() {
        return this.newItemList.size();
    }

    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean z = false;
        if (((Item) this.oldItemList.get(oldItemPosition)).getType() != ((Item) this.newItemList.get(newItemPosition)).getType()) {
            return false;
        }
        if (((Item) this.oldItemList.get(oldItemPosition)).getType() == 0) {
            return ((HeaderItem) this.oldItemList.get(oldItemPosition)).getHeader().equals(((HeaderItem) this.newItemList.get(newItemPosition)).getHeader());
        }
        if (((EpisodeItem) this.oldItemList.get(oldItemPosition)).getId() == ((EpisodeItem) this.newItemList.get(newItemPosition)).getId()) {
            z = true;
        }
        return z;
    }

    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (((Item) this.oldItemList.get(oldItemPosition)).getType() == 0) {
            return ((Item) this.oldItemList.get(oldItemPosition)).equals(this.newItemList.get(newItemPosition));
        }
        return ((Item) this.oldItemList.get(oldItemPosition)).equals(this.newItemList.get(newItemPosition));
    }
}
