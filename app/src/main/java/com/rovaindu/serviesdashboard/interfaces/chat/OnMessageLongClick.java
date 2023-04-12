package com.rovaindu.serviesdashboard.interfaces.chat;




import com.rovaindu.serviesdashboard.model.chat.BaseMessage;

import java.util.List;

public interface OnMessageLongClick
{
    void setLongMessageClick(List<BaseMessage> baseMessage);
}
