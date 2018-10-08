package com.sk.xjwd.contact.chat.chatrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.moor.imkf.model.entity.FromToMessage;
import com.sk.xjwd.contact.chat.holder.BaseHolder;

/**
 * Created by longwei on 2016/3/9.
 */
public interface IChatRow {


    View buildChatView(LayoutInflater inflater, View convertView);


    void buildChattingBaseData(Context context, BaseHolder baseHolder, FromToMessage detail, int position);


    int getChatViewType();
}
