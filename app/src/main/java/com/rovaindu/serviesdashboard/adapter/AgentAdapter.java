package com.rovaindu.serviesdashboard.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.rovaindu.serviesdashboard.R;
import com.rovaindu.serviesdashboard.controller.agents.ViewAgentActivity;
import com.rovaindu.serviesdashboard.manager.ServiesAgentSharedPrefManager;
import com.rovaindu.serviesdashboard.model.UserAddress;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;
import com.rovaindu.serviesdashboard.retrofit.models.ServiesCategory;
import com.rovaindu.serviesdashboard.utils.Constants;
import com.rovaindu.serviesdashboard.utils.views.AutoFitTextView;
import com.rovaindu.serviesdashboard.utils.views.TextViewAr;


import java.io.Serializable;
import java.util.ArrayList;



public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter. AgentViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesCategory selectedCategory;
    private ArrayList<ServiesAgent> agentList;
    Context context;

    public AgentAdapter(Context context, ArrayList<ServiesAgent> agentList , ServiesCategory selectedCategory) {
        this.context = context;
        this.agentList = agentList;
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_layout_item, parent, false);

        return new  AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position) {
        final ServiesAgent agent = agentList.get(position);
        final ServiesCategory category = selectedCategory;
        holder.agentName.setText(agent.getName());
        holder.agentDesc.setText(agent.getExperience());
        holder.setAgentImage(agent.getImage());
        float rating = 0f;
        if(agent.getRatesCount() > 0) {
            rating = (float) (agent.getRate() / agent.getRatesCount());
        }
        //float rating = 3.5f;
        holder.agentRatingbar.setRating(rating);
        holder.agentRating.setText("( " +context.getResources().getString(R.string.raring) + " " + rating + " )");
        holder.commentsTXT.setText(context.getResources().getString(R.string.comments) + ": " + 0);
        holder.costTXT.setText(context.getResources().getString(R.string.agent_cost) + ": " + agent.getHourlyWage() + " " + context.getResources().getString(R.string.currency));
        String[] separated = agent.getLocation().split(",");
        Double Lat =  Double.parseDouble(separated[0]);
        Double lLat =  Double.parseDouble(separated[1]);

         String distance = holder.DistanceCalculateTest(Lat , lLat);
        holder.distanceTXT.setText(context.getResources().getString(R.string.distance) + distance);
        holder.AgentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ViewAgentActivity.class);
                i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);
                context.startActivity(i);

            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return agentList.size();
    }

    class AgentViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private ImageView agentImg;
        private TextViewAr agentName , agentRating , agentDesc;
        private AutoFitTextView commentsTXT , costTXT , distanceTXT;
        private RatingBar agentRatingbar;

        private  AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            agentImg = itemView.findViewById(R.id.agentImg);
            agentName = itemView.findViewById(R.id.agentName);
            agentRating = itemView.findViewById(R.id.agentRating);
            agentDesc = itemView.findViewById(R.id.agentDesc);
            commentsTXT = itemView.findViewById(R.id.commentsTXT);
            costTXT = itemView.findViewById(R.id.costTXT);
            distanceTXT = itemView.findViewById(R.id.distanceTXT);
            agentRatingbar = itemView.findViewById(R.id.agentRatingbar);
        }

        private void setAgentImage(String url) {
            Glide.with(context).load(url).into(agentImg);

        }

        private String DistanceCalculateTest(double Lat , double lLat) {
            UserAddress user =  ServiesAgentSharedPrefManager.getInstance(context).getUserAddressT();
            //Toast.makeText(this, "" + userAddress.getPlaceName(), Toast.LENGTH_SHORT).show();
            Log.d("ADDRESS", "init: PlaceName " + user.getPlaceName());
            Log.d("ADDRESS", "init: Latitude " + user.getLatitude());
            Log.d("ADDRESS", "init: LongLatitude " + user.getLongitude());


            Log.d("ADDRESS", "init: " + distFrom(Lat , lLat ,user.getLatitude() , user.getLongitude()) / 1000 + " KM");
            //Log.d("ADDRESS", "init: " + distance(addressData.getLatitude() , addressData.getLongitude() ,37.42199845544925 , -122.0839998498559 ,0 , 0)  + " KM");
            float dist = distFrom(Lat , lLat     ,user.getLatitude() , user.getLongitude()) / 1000;
            return   String.format("%.2f",dist) + " كم";
            //Toasty.success(ViewAgentActivity.this, context.getResources().getString(R.string.test_distance) +  distFrom(userAddress.getLatitude() , userAddress.getLongitude() ,user.getLatitude() , user.getLongitude()) / 1000 + " كم" , Toast.LENGTH_SHORT).show();
        }
    }
    private static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}
