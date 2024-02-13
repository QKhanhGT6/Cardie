package com.example.cardie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardie.SetClass;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.MyViewHolder> {
    private Context mContext;
    private List<SetClass> mData;
    static SetListener listener;

    public SetAdapter(Context mContext, List<SetClass> mData, SetListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.set_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SetClass mDrawable = mData.get(position);
        try {
            Picasso.get()
                    .load(mDrawable.getSetImage())
                    .fit()
                    .centerCrop()
                    .into(holder.set_img_url);
        } catch (Exception e){

        }
        //SetImg turned off for testing API
//        int mDrawableName = mData.get(position).getSetImgUrl(); //R.drawable.bunny_Sweden
//        Drawable d = ResourcesCompat.getDrawable(mContext.getResources(),mDrawableName,null);
//        holder.set_card.setBackground(d);


        holder.set_card.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_trans));
        holder.set_title.setText(mData.get(position).getSetName());
//        //holder.imageView1.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.backgroundbluelight,null));
        holder.set_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSetClick(mDrawable);
            }
        });

        holder.practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPracticeButtonClick(mDrawable.getSetID(), mDrawable.getSetName());
            }
        });

        Context context = holder.imageView1.getContext();

        switch (mDrawable.getDifficulty(context)) {
            case 1: {
                //holder.imageView1.setBackgroundResource(R.drawable.circlefull);
                //holder.imageView1.setColorFilter(Color.RED);
                holder.imageView1.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.greendiff,null));
                break;
            }
            case 2: {
                holder.imageView1.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.greendiff,null));
                holder.imageView2.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.backgroundOrange,null));
                break;
            }
            case 3: {
                holder.imageView1.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.greendiff,null));
                holder.imageView2.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.backgroundOrange,null));
                holder.imageView3.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.backgroundPurple,null));
                break;
            }
            default: break;
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView set_title;
        TextView set_intro;
        CardView set_card;
        ImageView set_img_url;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        Button practice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            set_title =  itemView.findViewById(R.id.myset_title);
            set_card =  itemView.findViewById(R.id.cardview);
            set_img_url = itemView.findViewById(R.id.iv_setbackground);
            imageView1 = itemView.findViewById(R.id.iv_diff1);
            imageView2 = itemView.findViewById(R.id.iv_diff2);
            imageView3 = itemView.findViewById(R.id.iv_diff3);
            practice = itemView.findViewById(R.id.btn_practice);


        }

    }
}

