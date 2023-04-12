package com.rovaindu.serviesdashboard.interfaces;





import com.rovaindu.serviesdashboard.retrofit.models.ServiesAgent;

import java.io.Serializable;

public interface OnTrackerClickListener extends Serializable {
    void onTrackerClicked(ServiesAgent agent, int position, int parentID);
}
