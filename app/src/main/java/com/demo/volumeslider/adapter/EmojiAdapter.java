package com.demo.volumeslider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.demo.volumeslider.R;

import java.util.ArrayList;

import com.demo.volumeslider.Utl;
import com.demo.volumeslider.view.OnclickInterface;


public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.Holder> {
    ArrayList<View> allview;
    Context mContext;
    OnclickInterface onMyCommonItem;
    int selected;
    Boolean updateall;

    
    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout laymain;
        ImageView setthumb;
        ImageView setxxx;

        public Holder(View view) {
            super(view);
            this.laymain = (LinearLayout) view.findViewById(R.id.laymain);
            this.setthumb = (ImageView) view.findViewById(R.id.sethumb);
            this.setxxx = (ImageView) view.findViewById(R.id.setxxx);
        }
    }

    public EmojiAdapter(Context context, int i, ArrayList<View> arrayList, OnclickInterface onclickInterface) {
        this.allview = new ArrayList<>();
        this.selected = -1;
        this.updateall = true;
        this.mContext = context;
        this.selected = i;
        this.allview = arrayList;
        this.onMyCommonItem = onclickInterface;
        this.updateall = true;
    }

    @Override 
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new Holder(LayoutInflater.from(this.mContext).inflate(R.layout.emoji_adapter, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(Holder holder, final int i) {
        Utl.setLLayout(this.mContext, holder.laymain, 1040, 600);
        RequestManager with = Glide.with(this.mContext);
        with.load("file:///android_asset/emoji/" + (i + 1) + ".webp").into(holder.setthumb);
        holder.laymain.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                EmojiAdapter.this.onMyCommonItem.OnMyClick1(i, EmojiAdapter.this.allview.get(i));
            }
        });
        if (i == this.selected) {
            holder.setxxx.setVisibility(View.VISIBLE);
        } else {
            holder.setxxx.setVisibility(View.GONE);
        }
    }

    @Override 
    public int getItemCount() {
        return this.allview.size();
    }

    public void setselected(int i) {
        this.updateall = false;
        this.selected = i;
        notifyDataSetChanged();
    }
}
