package jdr.tvtracker.activities.main.fragments.episodeItem;

public abstract class Item {
    static final int TYPE_EPISODE = 1;
    public static final int TYPE_HEADER = 0;

    public abstract int getType();
}
