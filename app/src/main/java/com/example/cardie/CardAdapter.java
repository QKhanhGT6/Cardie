package com.example.cardie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cardie.CardClass;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends PagerAdapter {
    private List<CardClass> mData;
    private LayoutInflater layoutInflater;
    private Context mContext;
    ViewGroup Container;
    View view;
    String[] imageurl;
    int c=0;

    static CardView cardView;

    public CardAdapter(List<CardClass> models,Context context)
    {
        this.mData = models;
        this.mContext = context;
        /*     layoutInflater = LayoutInflater.from(context);*/
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    private View initObject(ViewGroup container, int position)
    {
        layoutInflater = LayoutInflater.from(mContext);
        final View view1 = layoutInflater.inflate(R.layout.card_layout,container,false);

        cardView = view1.findViewById(R.id.CardTemplate);
        /*        String mDrawableName = mData.get(position).getCardImageUrl(); //R.drawable.bunny_Sweden*/

        ImageView imgView = view1.findViewById(R.id.iv_CardBackground);
        /*        imgView.setImageResource(mDrawableName);*/

        System.out.println(mData.get(position).getCardImage());

        Picasso.get()
                .load(mData.get(position).getCardImage())
                .fit()
                .centerCrop()
                .into(imgView);

        TextView cardName = view1.findViewById(R.id.tv_CardPhrase);
        TextView cardType = view1.findViewById(R.id.tv_CardType);
        TextView cardDef = view1.findViewById(R.id.tv_CardDefinition);

        cardName.setText(mData.get(position).getCardPhrase());
        cardType.setText(mData.get(position).getCardType());
        cardDef.setText(mData.get(position).getCardDefinition());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout infoLayout = view1.findViewById(R.id.basicInfo);
                if (infoLayout.isShown()) {
                    infoLayout.setVisibility(View.INVISIBLE);
                    cardDef.setVisibility(View.VISIBLE);
                }
                else {
                    infoLayout.setVisibility(View.VISIBLE);
                    cardDef.setVisibility(View.INVISIBLE);
                }
            }

        });

//        LinearLayout cardNameLayout = view1.findViewById()
        return view1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        view = initObject(container,position);
        view.setTag("currentCardView"+ position);
        container.addView(view);
        Container = container;
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

//    public void getCurrentView(int position)
//    {
//
//    }
}