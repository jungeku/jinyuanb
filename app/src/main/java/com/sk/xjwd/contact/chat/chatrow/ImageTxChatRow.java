package com.sk.xjwd.contact.chat.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.moor.imkf.model.entity.FromToMessage;
import com.sk.xjwd.R;
import com.sk.xjwd.contact.chat.ChatActivity;
import com.sk.xjwd.contact.chat.ImageViewLookActivity;
import com.sk.xjwd.contact.chat.holder.BaseHolder;
import com.sk.xjwd.contact.chat.holder.ImageViewHolder;
import com.sk.xjwd.contact.utils.ImageUtils;

/**
 * Created by longwei on 2016/3/10.
 */
public class ImageTxChatRow extends BaseChatRow {


    public ImageTxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(final Context context, BaseHolder baseHolder, FromToMessage detail, int position) {
        final ImageViewHolder holder = (ImageViewHolder) baseHolder;
        final FromToMessage message = detail;
        if(message != null) {
            Glide.with(context).load(message.filePath)
                    .asBitmap()
                    .placeholder(R.drawable.pic_thumb_bg)
                    .error(R.drawable.image_download_fail_icon)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            final Bitmap bitmap_bg = BitmapFactory.decodeResource(context.getResources(), R.drawable.kf_chatto_bg_normal);
                            Bitmap newBitmap = ImageUtils.getRoundCornerImage(bitmap_bg, resource);
                            holder.getImageView().setImageBitmap(newBitmap);
                        }
                    });
            holder.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageViewLookActivity.class);
                    intent.putExtra("imagePath", message.filePath);
                    context.startActivity(intent);
                }
            });
            View.OnClickListener listener = ((ChatActivity)context).getChatAdapter().getOnClickListener();
            getMsgStateResId(position, holder, message, listener);
        }
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.kf_chat_row_image_tx, null);
            ImageViewHolder holder = new ImageViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, false));
        }

        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.IMAGE_ROW_TRANSMIT.ordinal();
    }
}
