package com.example.dofaster;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dofaster.data.GamesInfo;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private List<GamesInfo> gamesInfoList;

    private MainActivityClickListener mainActivityClickListener;

    public GamesAdapter(
            List<GamesInfo> gamesInfoList, MainActivityClickListener mainActivityClickListener
    ) {
        this.gamesInfoList = gamesInfoList;
        this.mainActivityClickListener = mainActivityClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.gamePic.setImageResource(gamesInfoList.get(position).getGamePic());
        holder.gameTitle.setText(gamesInfoList.get(position).getGameTitle());
        holder.gameCaption.setText(gamesInfoList.get(position).getGameCaption());
    }

    @Override
    public int getItemCount() {
        return gamesInfoList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView gamePic;
        private ImageView scoreListIcon;
        private TextView gameTitle;
        private TextView gameCaption;

        public ViewHolder(View itemView) {
            super(itemView);
            gamePic = itemView.findViewById(R.id.game_pic);
            scoreListIcon = itemView.findViewById(R.id.rank_list);
            gameTitle = itemView.findViewById(R.id.game_title);
            gameCaption = itemView.findViewById(R.id.game_caption);

            gamePic.setOnClickListener(this);
            scoreListIcon.setOnClickListener(this);
        }

        @SuppressLint("ResourceType")
        @Override
        public void onClick(View v) {
//            Log.i("TAG", "" + v.getId());
            if (v.getId() == 2131230775) {
                // Handling game picture clicks which calls item 0
                switch (getAdapterPosition()) {
                    case 0:
                        mainActivityClickListener.whichClicked(0, 0);
                        break;
                    case 1:
//                        Log.i("TAG", "man");
                        mainActivityClickListener.whichClicked(0, 1);
                        break;
                    case 2:
                        mainActivityClickListener.whichClicked(0, 2);
                        break;
                }
            } else {
                // Handling game ranks clicks which calls item 1
                switch (getAdapterPosition()) {
                    case 0:
                        mainActivityClickListener.whichClicked(1, 0);
                        break;
                    case 1:
                        mainActivityClickListener.whichClicked(1, 1);
                        break;
                    case 2:
                        mainActivityClickListener.whichClicked(1, 2);
//                        Log.i("TAG", "to");
                        break;
                }
            }
        }
    }
}
