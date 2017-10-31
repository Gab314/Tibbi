package com.example.mylibrary.tibbi.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.tibbi.R;
import com.example.mylibrary.tibbi.Utils.Mobs;

import java.util.List;

public class RecyclerLadderAdapter extends RecyclerView.Adapter<RecyclerLadderAdapter.MyViewHolder>{
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<Mobs> mobs;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mName, mHP;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ladder_cards_ImageView);
            mName = (TextView) itemView.findViewById(R.id.ladder_cards_TextView_Name);
            mHP = (TextView) itemView.findViewById(R.id.ladder_cards_TextView_HP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public RecyclerLadderAdapter(Context context, List<Mobs> mobsList){
        mContext = context;
        mobs = mobsList;
    }
    private Context getContext(){
        return mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View mView = inflater.inflate(R.layout.ladder_cards,parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Mobs mMobs = mobs.get(position);
        ImageView imageView = holder.mImageView;
        TextView nameText = holder.mName;
        TextView hpText = holder.mHP;

        String mob_name = mMobs.getName();
        nameText.setText(mob_name);
        hpText.setText(String.valueOf(mMobs.getTotal_HP()));
    }

    @Override
    public int getItemCount() {
        return mobs.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public void clear(){
        if (mobs != null){
            mobs.clear();
            notifyDataSetChanged();
        }
    }

    public Mobs getItem(int id){
        return mobs.get(id);
    }
}
