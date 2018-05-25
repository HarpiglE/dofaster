package com.example.dofaster;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {

    private List<GamesInfo> gamesInfoList;

    private ScoreListClickListener scoreListClickListener;

    public GamesAdapter(
            List<GamesInfo> gamesInfoList, ScoreListClickListener scoreListClickListener
    ) {
        this.gamesInfoList = gamesInfoList;
        this.scoreListClickListener = scoreListClickListener;
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
            scoreListIcon = itemView.findViewById(R.id.score_list);
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
                switch (getAdapterPosition()) {
                    case 0:
                        break;
                    case 1:
//                        Log.i("TAG", "man");
                        break;
                    case 2:
                        break;
                }
            } else {
                switch (getAdapterPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
//                        Log.i("TAG", "to");
                        break;
                }
            }
        }
    }
}
