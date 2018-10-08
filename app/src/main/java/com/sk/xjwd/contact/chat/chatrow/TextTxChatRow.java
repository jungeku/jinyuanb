package com.sk.xjwd.contact.chat.chatrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.moor.imkf.model.entity.FromToMessage;
import com.moor.imkf.utils.AnimatedGifDrawable;
import com.moor.imkf.utils.AnimatedImageSpan;
import com.sk.xjwd.R;
import com.sk.xjwd.contact.chat.ChatActivity;
import com.sk.xjwd.contact.chat.holder.BaseHolder;
import com.sk.xjwd.contact.chat.holder.TextViewHolder;
import com.sk.xjwd.contact.utils.FaceConversionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by longwei on 2016/3/9.
 */
public class TextTxChatRow extends BaseChatRow {

    private Context context;

    public TextTxChatRow(int type) {
        super(type);
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, FromToMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, FromToMessage detail, int position) {
        this.context = context;
        TextViewHolder holder = (TextViewHolder) baseHolder;
        FromToMessage message = detail;
        if (message != null) {

            SpannableStringBuilder content = handler(holder.getDescTextView(),
                    message.message);
            SpannableString spannableString = FaceConversionUtil.getInstace()
                    .getExpressionString(context, content + "", holder.getDescTextView());

            Pattern patten2 = Pattern.compile("((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|(((http[s]{0,1}|ftp)://|)((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)", Pattern.CASE_INSENSITIVE);

            Matcher matcher2 = patten2.matcher(spannableString);
            while (matcher2.find()) {
                String number = matcher2.group();
                int end = matcher2.start() + number.length();
                HttpClickSpan clickSpan = new HttpClickSpan(number);
                spannableString.setSpan(clickSpan, matcher2.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.startcolor));
                spannableString.setSpan(colorSpan, matcher2.start(), end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            holder.getDescTextView().setText(spannableString);
            holder.getDescTextView().setMovementMethod(LinkMovementMethod.getInstance());
            View.OnClickListener listener = ((ChatActivity) context).getChatAdapter().getOnClickListener();
            getMsgStateResId(position, holder, message, listener);
        }
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.kf_chat_row_text_tx, null);
            TextViewHolder holder = new TextViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, false));
        }

        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChatRowType.TEXT_ROW_TRANSMIT.ordinal();
    }

    private SpannableStringBuilder handler(final TextView gifTextView,
                                           String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String tempText = m.group();
            try {
                String num = tempText.substring(
                        "#[face/png/f_static_".length(), tempText.length()
                                - ".png]#".length());
                String gif = "face/gif/f" + num + ".gif";
                /**
                 * 如果open这里不抛异常说明存在gif，则显示对应的gif 否则说明gif找不到，则显示png
                 * */
                InputStream is = context.getAssets().open(gif);
                sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,
                                new AnimatedGifDrawable.UpdateListener() {
                                    @Override
                                    public void update() {
                                        gifTextView.postInvalidate();
                                    }
                                })), m.start(), m.end(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                is.close();
            } catch (Exception e) {
                String png = tempText.substring("#[".length(),
                        tempText.length() - "]#".length());
                try {
                    sb.setSpan(
                            new ImageSpan(context,
                                    BitmapFactory.decodeStream(context
                                            .getAssets().open(png))),
                            m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return sb;
    }

    class HttpClickSpan extends ClickableSpan {
        String msg;

        public HttpClickSpan(String msg) {
            this.msg = msg;
        }

        @Override
        public void onClick(View widget) {
            try {
                if (!msg.contains("http") && !msg.contains("https")) {
                    msg = "http://" + msg;
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(msg);
                    intent.setData(content_url);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
                Toast.makeText(context, R.string.url_failure, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
