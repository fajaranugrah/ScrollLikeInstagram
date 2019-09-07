package com.example.fajarramadhan.scrolllikeinstagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CustomeUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<Items> userMessagesList;
    private LayoutInflater mLayoutInflater;
    private static final int ROW_TYPE_LOAD_EARLIER_MESSAGES = 0;
    private static final int ROW_TYPE_SENDER = 1;
    private static final int ROW_TYPE_SENDER_IMAGE = 2;
    private static final int ROW_TYPE_RECEIVER = 3;
    private static final int ROW_TYPE_RECEIVER_IMAGE = 4;
    private static final int ROW_TYPE_TITLE = 5;
    private static final int ROW_TYPE_DATE = 6;
    private static final int ROW_TYPE_RECEIVER_VIDEO = 7;
    private String userId;
    private boolean isLoadEarlierMsgs;
    private CustomeUserAdapter.LoadEarlierMessages mLoadEarlierMessages;
    private int visibleThreshold =10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    //private OnLoadMoreListener onLoadMoreListener;

    public CustomeUserAdapter(Context context, String userId, List<Items> userMessagesList) {
        mContext = context;
        this.userMessagesList = userMessagesList;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.userId = userId;

        /*EmojiCompat.Config config = new BundledEmojiCompatConfig(mContext);
        EmojiCompat.init(config);*/
        //mLoadEarlierMessages = (LoadEarlierMessages) mContext;

        /*if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    try {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        int totalScrollitem = lastVisibleItem + visibleThreshold;

                        if (!loading && lastVisibleItem >= totalItemCount-1) {
                            // End has been reached
                            // Do something
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }*/

    }

    /*public void setLoaded() {
        loading = false;
    }*/

    /*public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ROW_TYPE_LOAD_EARLIER_MESSAGES:
                return new CustomeUserAdapter.ProgressViewHolder(mLayoutInflater.inflate(R.layout
                        .progress_item, parent, false));
            case ROW_TYPE_SENDER:
                return new CustomeUserAdapter.SenderMsgViewHolder(mLayoutInflater.inflate(R.layout.row_type_sender,
                        parent, false));
            case ROW_TYPE_SENDER_IMAGE:
                return new CustomeUserAdapter.SenderImgViewHolder(mLayoutInflater.inflate(R.layout.row_type_sender_image,
                        parent, false));
            case ROW_TYPE_RECEIVER:
                return new CustomeUserAdapter.ReceiverMsgViewHolder(mLayoutInflater.inflate(R.layout
                        .row_type_receiver, parent, false));
            case ROW_TYPE_RECEIVER_IMAGE:
                return new CustomeUserAdapter.ReceiverImgViewHolder(mLayoutInflater.inflate(R.layout
                        .row_type_receiver_image, parent, false));
            case ROW_TYPE_RECEIVER_VIDEO:
                return new CustomeUserAdapter.ReceiverVideoViewHolder(mLayoutInflater.inflate(R.layout
                        .row_type_receiver_video, parent, false));
            case ROW_TYPE_TITLE:
                return new CustomeUserAdapter.TitleViewHolder(mLayoutInflater.inflate(R.layout
                        .row_type_title, parent, false));
            case ROW_TYPE_DATE:
                return new CustomeUserAdapter.DateViewHolder(mLayoutInflater.inflate(R.layout
                        .row_type_date, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String messageChat;
        String dateCurrent = DateUtils.dateTimeConvert(DateUtils.getCurrentDate());;
        if (position<userMessagesList.size()){
            dateCurrent = DateUtils.convertToNormalTimezone(
                    userMessagesList.get(position).getCreatedDate(), "MMM dd, yyyy");
        }

        String dateBefore = DateUtils.dateTimeConvert(DateUtils.getCurrentDate());

        switch (getItemViewType(position)) {
            case ROW_TYPE_LOAD_EARLIER_MESSAGES:
                CustomeUserAdapter.ProgressViewHolder loadEarlierMsgsViewHolder =
                        (CustomeUserAdapter.ProgressViewHolder) holder;
                if (isLoadEarlierMsgs) {
                    loadEarlierMsgsViewHolder.progressBar.setVisibility(View.VISIBLE);
                } else {
                    loadEarlierMsgsViewHolder.progressBar.setVisibility(View.GONE);
                }
                break;
            case ROW_TYPE_DATE:
                CustomeUserAdapter.DateViewHolder dateViewHolder =
                        (CustomeUserAdapter.DateViewHolder) holder;
                if (isLoadEarlierMsgs) {
                    dateViewHolder.date.setVisibility(View.GONE);
                } else {
                    dateViewHolder.date.setVisibility(View.VISIBLE);
                    dateViewHolder.date.setText(DateUtils.convertToNormalTimezone(
                            userMessagesList.get(position-1).getCreatedDate(), "MMM dd, yyyy"));
                }
                break;
            case ROW_TYPE_SENDER:
                CustomeUserAdapter.SenderMsgViewHolder senderMsgViewHolder = (CustomeUserAdapter.SenderMsgViewHolder) holder;
                //messageChat = EmojiUtil.decode(userMessagesList.get(position).getContent());
                if (userMessagesList.get(position).getContent().toString().contains("\n")){
                    messageChat = EmojiUtil2.decode(userMessagesList.get(position).getContent());
                } else {
                    messageChat = EmojiUtil.decode(userMessagesList.get(position).getContent());
                }
                // set data for your sender chat bubble

                if (messageChat.toLowerCase().contains("**hire to show**")){
                    //senderMsgViewHolder.msg.setText(addClickablePart("*w","m*",mContext,messageChat,"#ff8026c2",new Intent(mContext, RapidShopActivity.class)));
                    senderMsgViewHolder.msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Intent i = new Intent(mContext,RapidShopActivity.class);
                            mContext.startActivity(i);*/
                        }
                    });
                }
                else {
                    senderMsgViewHolder.msg.setText(messageChat);
                    senderMsgViewHolder.msg.setOnClickListener(null);
                }

                if (userMessagesList.get(position).getContentType().equals("sending")){
                    senderMsgViewHolder.time.setText("Sending...");
                }
                else {
                    senderMsgViewHolder.time.setText(DateUtils
                            .convertToNormalTimezone(
                                    userMessagesList.get(position).getCreatedDate(), "h:mm a"));

                    if(position < userMessagesList.size()-1){

                        if(userMessagesList.get(position + 1).getCreatedDate()!=null){
                            dateBefore = DateUtils.convertToNormalTimezone(
                                    userMessagesList.get(position + 1).getCreatedDate(), "MMM dd, yyyy");
                        }

                        if(dateBefore.equals(dateCurrent)){
                            senderMsgViewHolder.date.setVisibility(View.GONE);
                        }
                        else {
                            senderMsgViewHolder.date.setText(dateCurrent);
                            senderMsgViewHolder.date.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        senderMsgViewHolder.date.setVisibility(View.GONE);
                    }

                }



                break;
            case ROW_TYPE_SENDER_IMAGE:
                CustomeUserAdapter.SenderImgViewHolder senderImgViewHolder = (CustomeUserAdapter.SenderImgViewHolder) holder;
                if (userMessagesList.get(position).getContentType().equals("sending")){
                    /*if(MyApplication.chatImageBitmap!=null){
                        senderImgViewHolder.img.setImageBitmap(ImageProcessingUtil
                                .getResizedBitmap(MyApplication.chatImageBitmap,400));
                    }*/
                    senderImgViewHolder.time.setText("Sending...");
                }
                else {
                    Picasso.with(mContext).
                            load(userMessagesList.get(position).getContent())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .placeholder(R.drawable.default_loader)
                            .into(senderImgViewHolder.img);
//                    Glide.with(mContext)
//                            .load(userMessagesList.get(position).getContent())
//                            .skipMemoryCache(true)
//                            .crossFade()
//                            .override(400,400)
//                            .placeholder(R.drawable.default_loader)
//                            .into(senderImgViewHolder.img);
                    senderImgViewHolder.time.setText(DateUtils
                            .convertToNormalTimezone(
                                    userMessagesList.get(position).getCreatedDate(), "h:mm a"));
                    senderImgViewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                /*MyApplication.updateMessageList = false;
                                Intent intent = new Intent(mContext, ChatImageViewerActivity.class);
                                intent.putExtra(PHOTO_TYPE, "chat_image");
                                intent.putExtra(PHOTO_URL, userMessagesList.get(holder.getAdapterPosition()).getContent());
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);*/
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    if(position < userMessagesList.size()-1){

                        if(userMessagesList.get(position + 1).getCreatedDate()!=null){
                            dateBefore = DateUtils.convertToNormalTimezone(
                                    userMessagesList.get(position + 1).getCreatedDate(), "MMM dd, yyyy");
                        }

                        if(dateBefore.equals(dateCurrent)){
                            senderImgViewHolder.date.setVisibility(View.GONE);
                        }
                        else {
                            senderImgViewHolder.date.setText(dateCurrent);
                            senderImgViewHolder.date.setVisibility(View.VISIBLE);
                        }

                    }
                    else {
                        senderImgViewHolder.date.setVisibility(View.GONE);
                    }
                }


                break;
            case ROW_TYPE_RECEIVER:
                CustomeUserAdapter.ReceiverMsgViewHolder receiverMsgViewHolder = (CustomeUserAdapter.ReceiverMsgViewHolder) holder;
                //decode emoji
                //MyApplication.fromChatSupport = true;
                // set data for your receiver chat bubble
                //messageChat = EmojiUtil.decode(userMessagesList.get(position).getContent());
                if (userMessagesList.get(position).getContent().toString().contains("\n")){
                    messageChat = EmojiUtil2.decode(userMessagesList.get(position).getContent());
                } else {
                    messageChat = EmojiUtil.decode(userMessagesList.get(position).getContent());
                }
                if (messageChat.toLowerCase().contains("**hire to show**")){
                    /*receiverMsgViewHolder.msg.setText(addClickablePart("*w","m*",mContext,messageChat,"#ff8026c2",new Intent(mContext, RapidShopActivity.class)));
                    receiverMsgViewHolder.msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(mContext,RapidShopActivity.class);
                            mContext.startActivity(i);
                        }
                    });*/
                }
                else {
                    receiverMsgViewHolder.msg.setText(messageChat);
                    receiverMsgViewHolder.msg.setOnClickListener(null);
                }
                receiverMsgViewHolder.time.setText(DateUtils
                        .convertToNormalTimezone(
                                userMessagesList.get(position).getCreatedDate(), "h:mm a"));

                if(position < userMessagesList.size()-1){

                    if(userMessagesList.get(position + 1).getCreatedDate()!=null){
                        dateBefore = DateUtils.convertToNormalTimezone(
                                userMessagesList.get(position + 1).getCreatedDate(), "MMM dd, yyyy");
                    }

                    if(dateBefore.equals(dateCurrent)){
                        receiverMsgViewHolder.date.setVisibility(View.GONE);
                    }
                    else {
                        receiverMsgViewHolder.date.setText(dateCurrent);
                        receiverMsgViewHolder.date.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    receiverMsgViewHolder.date.setVisibility(View.GONE);
                }
                break;
            case ROW_TYPE_RECEIVER_IMAGE:
                CustomeUserAdapter.ReceiverImgViewHolder receiverImgViewHolder = (CustomeUserAdapter.ReceiverImgViewHolder) holder;
                Picasso.with(mContext).
                        load(userMessagesList.get(position).getContent())
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .placeholder(R.drawable.default_loader)
                        .into(receiverImgViewHolder.img);
                receiverImgViewHolder.time.setText(DateUtils
                        .convertToNormalTimezone(
                                userMessagesList.get(position).getCreatedDate(), "h:mm a"));
                receiverImgViewHolder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            /*MyApplication.updateMessageList = false;
                            Intent intent = new Intent(mContext, ChatImageViewerActivity.class);
                            intent.putExtra(PHOTO_TYPE, "chat_image");
                            intent.putExtra(PHOTO_URL, userMessagesList.get(holder.getAdapterPosition()).getContent());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);*/
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                if(position < userMessagesList.size()-1){
                    if(userMessagesList.get(position + 1).getCreatedDate()!=null){
                        dateBefore = DateUtils.convertToNormalTimezone(
                                userMessagesList.get(position + 1).getCreatedDate(), "MMM dd, yyyy");
                    }

                    if(dateBefore.equals(dateCurrent)){
                        receiverImgViewHolder.date.setVisibility(View.GONE);
                    }
                    else {
                        receiverImgViewHolder.date.setText(dateCurrent);
                        receiverImgViewHolder.date.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    receiverImgViewHolder.date.setVisibility(View.GONE);
                }

                break;
            case ROW_TYPE_RECEIVER_VIDEO:
                final ReceiverVideoViewHolder receiverVideoViewHolder = (ReceiverVideoViewHolder) holder;
                receiverVideoViewHolder.videoView.setVideoPath(userMessagesList.get(position).getContent());
                receiverVideoViewHolder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        receiverVideoViewHolder.videoView.seekTo(1);
                    }
                });
                //receiverVideoViewHolder.videoView.start();
                /*Picasso.with(mContext).
                        load(userMessagesList.get(position).getContent())
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .placeholder(R.drawable.default_loader)
                        .into(receiverVideoViewHolder.videoView);*/
//                Glide.with(mContext)
//                        .load(userMessagesList.get(position).getContent())
//                        .skipMemoryCache(true)
//                        .crossFade()
//                        .override(400,400)
//                        .placeholder(R.drawable.default_loader)
//                        .into(receiverImgViewHolder.img);
                receiverVideoViewHolder.time.setText(DateUtils
                        .convertToNormalTimezone(
                                userMessagesList.get(position).getCreatedDate(), "h:mm a"));
                receiverVideoViewHolder.videoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("clickVideo", "click video");
                        try {
                            //MyApplication.updateMessageList = false;
                            Intent intent = new Intent(mContext, ChatVideoViewerActivity.class);
                            intent.putExtra("type", "chat_video");
                            intent.putExtra("url_video", userMessagesList.get(holder.getAdapterPosition()).getContent());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                if(position < userMessagesList.size()-1){
                    if(userMessagesList.get(position + 1).getCreatedDate()!=null){
                        dateBefore = DateUtils.convertToNormalTimezone(
                                userMessagesList.get(position + 1).getCreatedDate(), "MMM dd, yyyy");
                    }

                    if(dateBefore.equals(dateCurrent)){
                        receiverVideoViewHolder.date.setVisibility(View.GONE);
                    }
                    else {
                        receiverVideoViewHolder.date.setText(dateCurrent);
                        receiverVideoViewHolder.date.setVisibility(View.VISIBLE);
                    }

                }
                else {
                    receiverVideoViewHolder.date.setVisibility(View.GONE);
                }

                break;
            case ROW_TYPE_TITLE:
                CustomeUserAdapter.TitleViewHolder titleViewHolder = (CustomeUserAdapter.TitleViewHolder) holder;
                try {
                    if (userMessagesList.get(position).getContent().contains("type")){
                        JSONObject title = new JSONObject(userMessagesList.get(position).getContent());

                        if(title.getInt("type")==1){
                            if(title.getString("title").contains("[")&&title.getString("title").contains("]")){
                                titleViewHolder.title.setTextColor(mContext.getResources().getColor(R.color.black));
                                //titleViewHolder.title.setText(addClickablePart("[","]",mContext,title.getString("title"),"#39b54a",null));
                            }
                            else {
                                titleViewHolder.title.setTextColor(Color.parseColor("#39b54a"));
                                titleViewHolder.title.setText(title.getString("title")+" ");
                            }

                            if (title.has("hint")){
                                if (title.getString("hint")!=null){
                                    titleViewHolder.title.setTextColor(Color.parseColor("#39b54a"));
                                    titleViewHolder.title.setText(title.getString("title")+" ");
                                }
                            }

                            if (title.has("subtitle")) {
                                if (title.getString("subtitle") != null) {
                                    titleViewHolder.title_subtitle.setVisibility(View.VISIBLE);
                                    titleViewHolder.title_subtitle.setText(title.getString("subtitle"));
                                } else {
                                    titleViewHolder.title_subtitle.setVisibility(View.GONE);
                                }
                            }

                        }

                        if(!title.getString("offerAmount").equals("0")){
                            titleViewHolder.price.setTextColor(Color.parseColor("#39b54a"));
                            titleViewHolder.price.setText(title.getString("offerCurrency")
                                    +" "+ CurrencyUtil.offerAmountDefault(title.getString("offerAmount")));
                            titleViewHolder.price.setVisibility(View.VISIBLE);
                        }
                        else {
                            titleViewHolder.price.setVisibility(View.GONE);
                        }
                    }
                    else {
                        titleViewHolder.price.setVisibility(View.GONE);
                        //titleViewHolder.title.setText(addClickablePart("[","]",mContext,userMessagesList.get(position).getContent(),"#39b54a",null));
                    }


                    titleViewHolder.time.setText(
                            DateUtils.convertToNormalTimezone(
                                    userMessagesList.get(position).getCreatedDate(), "MMMM dd, yyyy, h:mm a"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public List<Items> getItems() {
        return userMessagesList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < userMessagesList.size()) {
            if (userMessagesList.get(position).getUserInfo().getId().equals(userId)) {
                if (userMessagesList.get(position).getContentType().equalsIgnoreCase("text")||
                        userMessagesList.get(position).getContentType().equalsIgnoreCase("sending")){
                    if (userMessagesList.get(position).getContent().contains("[")&&
                            userMessagesList.get(position).getContent().contains("]")){
                        return ROW_TYPE_TITLE; // title row;
                    }
                    else {
                        return ROW_TYPE_SENDER; // sender row;
                    }

                }
                else if(userMessagesList.get(position).getContentType().equalsIgnoreCase("image")||
                        userMessagesList.get(position).getContentType().equalsIgnoreCase("sendimage")){
                    return ROW_TYPE_SENDER_IMAGE; // sender row;
                }
                else {
                    return ROW_TYPE_TITLE; // title row;
                }

            }else {
                if (userMessagesList.get(position).getContentType().equalsIgnoreCase("text")){
                    if (userMessagesList.get(position).getContent().contains("[")&&
                            userMessagesList.get(position).getContent().contains("]")){
                        return ROW_TYPE_TITLE; // title row;
                    }
                    else {
                        return ROW_TYPE_RECEIVER; // receiver row;
                    }
                }
                else if(userMessagesList.get(position).getContentType().equalsIgnoreCase("image")){
                    return ROW_TYPE_RECEIVER_IMAGE; // receiver row;
                }
                else if(userMessagesList.get(position).getContentType().equalsIgnoreCase("video")){
                    return ROW_TYPE_RECEIVER_VIDEO; // receiver row;
                }
                else {
                    return ROW_TYPE_TITLE; // title row;
                }
            }

        } else {
            if (isLoadEarlierMsgs){
                return ROW_TYPE_LOAD_EARLIER_MESSAGES; // row load earlier messages
            }
            else {
                return ROW_TYPE_DATE;
            }

        }
    }

    public interface LoadEarlierMessages {
        void onLoadEarlierMessages();
    }


    public void setLoadEarlierMsgs(boolean isLoadEarlierMsgs) {
        this.isLoadEarlierMsgs = isLoadEarlierMsgs;
    }

    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        private ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public static class SenderMsgViewHolder extends RecyclerView.ViewHolder {
        private TextView time, date;
        private EmojiTextView msg;
        private SenderMsgViewHolder(View itemView) {
            super(itemView);
            msg = (EmojiTextView) itemView.findViewById(R.id.textViewMessageSender);
            time = (TextView) itemView.findViewById(R.id.textViewTime);
            date = (TextView) itemView.findViewById(R.id.display_date);
        }
    }

    public static class ReceiverMsgViewHolder extends RecyclerView.ViewHolder {

        private TextView time,date;
        private EmojiTextView msg;
        private ReceiverMsgViewHolder(View itemView) {
            super(itemView);
            msg = (EmojiTextView) itemView.findViewById(R.id.textViewMessageReceive);
            time = (TextView) itemView.findViewById(R.id.textViewTime);
            date = (TextView) itemView.findViewById(R.id.display_date);
        }
    }

    private static class SenderImgViewHolder extends RecyclerView.ViewHolder {

        private TextView time,date;
        private ImageView img;
        private SenderImgViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageViewMessageSender);
            time = (TextView) itemView.findViewById(R.id.textViewTimeSender);
            date = (TextView) itemView.findViewById(R.id.display_date_Sender);
        }

    }

    private static class ReceiverImgViewHolder extends RecyclerView.ViewHolder {

        private TextView time,date;
        private ImageView img;
        private ReceiverImgViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageViewMessageReceive);
            time = (TextView) itemView.findViewById(R.id.textViewTimeReceive);
            date = (TextView) itemView.findViewById(R.id.display_date_Receive);
        }
    }

    private static class ReceiverVideoViewHolder extends RecyclerView.ViewHolder {

        private TextView time,date;
        private VideoView videoView;
        private ReceiverVideoViewHolder(View itemView) {
            super(itemView);
            videoView = (VideoView) itemView.findViewById(R.id.videoViewMessageReceive);
            time = (TextView) itemView.findViewById(R.id.textViewTimeReceiveVideo);
            date = (TextView) itemView.findViewById(R.id.display_date_receive_video);
        }
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView time,title,price,title_subtitle;
        private TitleViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_desc);
            price = (TextView) itemView.findViewById(R.id.title_price);
            time = (TextView) itemView.findViewById(R.id.title_date);
            title_subtitle = (TextView) itemView.findViewById(R.id.title_subtitle);
        }
    }

    private static class DateViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private DateViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.display_date_initial);
        }
    }

    private SpannableStringBuilder addClickablePart(String start, String end, final Context ctx, String str, String color, final Intent i) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int startCount = start.length();
        int endCount = end.length();

        int plusChar =0;

        if (endCount>1){
            plusChar = endCount;
        }
        else if (end.equals("]")){
            plusChar+=1;
        }


        int idx1 = str.indexOf(start);
        int idx2 = 0;
        while (idx1 != -1) {
            idx2 = str.indexOf(end, idx1) + plusChar;

            final String clickString = str.substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    //todo
					/*if (i!=null){
						ctx.startActivity(i);
					}*/
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);

                }
            }, idx1, idx2, 0);
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor(color)), idx1, idx2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            idx1 = str.indexOf(start, idx2);
        }

        return ssb;
    }


}
