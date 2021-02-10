package com.example.nextoliveproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nextoliveproject.Helper.RoundedImageView;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.ManageChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ConversationListAdapter extends BaseAdapter {
    //  String[] result;

    Context context;

    Typeface typeface;

    // int[] imageId;
    ArrayList<HashMap<String, Object>> dataList;

    private static LayoutInflater inflater = null;

    String listType = "";


    public ConversationListAdapter(Activity mainActivity, ArrayList<HashMap<String, Object>> notificationList) {
        // TODO Auto-generated constructor stub
        dataList = notificationList;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IRAN-Sans.ttf");

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView favoriteImage;
        RoundedImageView userImage;
        TextView userTitle, lastMessage;
        Button unreadCount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.conv_list, null);
        try {
            holder.userTitle = (TextView) rowView.findViewById(R.id.userTitle);
            holder.lastMessage = (TextView) rowView.findViewById(R.id.lastMessage);
            holder.userImage = (RoundedImageView) rowView.findViewById(R.id.userImage);
            holder.unreadCount = (Button) rowView.findViewById(R.id.unreadCount);
            LinearLayout superView = (LinearLayout) rowView.findViewById(R.id.superView);
            //  UTILITY.setBackgroundAsContainerBox(superView, shp);
            final HashMap<String, Object> chatRow = dataList.get(position);

            if (chatRow != null) {
                holder.userTitle.setText((chatRow.get("UserName") != null && !chatRow.get("UserName").toString().equals("null") ? chatRow.get("UserName").toString() : ""));
                holder.lastMessage.setText(Html.fromHtml(chatRow.get("Message") != null && !chatRow.get("Message").toString().equals("null") ? chatRow.get("Message").toString() : ""));
                //Picasso.with(context).load("http://84.241.33.46:130//Profile/" + (chatRow.get("UserId") != null ? chatRow.get("UserId").toString() : "") + "/profile.jpg").networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.user_icon_header).placeholder(R.drawable.user_icon_header).into(holder.userImage);
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (chatRow.get("UserId") != null && !chatRow.get("UserId").toString().equals("null")) {
                            Intent i = new Intent(context, ManageChatActivity.class);
                            i.putExtra("FriendId", Integer.valueOf(chatRow.get("UserId").toString()));
                            i.putExtra("FriendName", (chatRow.get("UserName") != null && !chatRow.get("UserName").toString().equals("null") ? chatRow.get("UserName").toString() : ""));
                            context.startActivity(i);
                        }
                    }
                });
                //  UTILITY.setCircularButtonStyle(holder.unreadCount, shp);
                if (chatRow.get("UnreadCount") != null && !chatRow.get("UnreadCount").toString().equals("null") && Integer.valueOf(chatRow.get("UnreadCount").toString()) > 0) {
                    holder.unreadCount.setText(chatRow.get("UnreadCount").toString());
                } else {
                    holder.unreadCount.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            int a = 0;
        }
        return rowView;
    }
}