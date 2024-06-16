package edu.upc.dsa.android_upz_apocalypse;

import android.annotation.SuppressLint;
import android.view.View;

public interface RecyclerClickViewListener {
    public void recyclerViewListClicked(int position);

    @SuppressLint("NonConstantResourceId")
    void onClick(View view);
}
