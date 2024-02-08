package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class HealthArticleLVAdapter extends ArrayAdapter<HealthArticle> {

    private Context context;
    private ArrayList<HealthArticle> articles=new ArrayList<>();
    private int layout;

    public HealthArticleLVAdapter(@NonNull Context context, int resource, ArrayList<HealthArticle> articles) {
        super(context, resource,articles);
        this.context = context;
        this.articles = articles;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent, false);
            holder = new HealthArticleLVAdapter.ViewHolder();
            holder.articleCard = (CardView) convertView.findViewById(R.id.articleCard);
            holder.thumbnailDisp = (TextView) convertView.findViewById(R.id.articleThumbnail);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.thumbnailDisp.setText(articles.get(position).getThumbnail());
        holder.articleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HealthArticleReadActivity.class);
                intent.putExtra("url",articles.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder{
        CardView articleCard;
        TextView thumbnailDisp;
    }
}
