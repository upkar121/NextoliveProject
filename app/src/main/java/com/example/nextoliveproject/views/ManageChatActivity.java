package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nextoliveproject.Helper.RoundedImageView;
import com.example.nextoliveproject.database.SaredPreference.SharedData;
import com.example.nextoliveproject.MainActivity;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.ChatDatabase;
import com.example.nextoliveproject.utility.Utility;

import java.util.List;

public class ManageChatActivity extends AppCompatActivity implements View.OnClickListener {

    Activity activity;

    ///public static ChatArrayAdapter adp;

    public static ListView list;

    ImageView send;

    EditText compose;

    ChatDatabase db;

    String UserId = "", FriendName = "", FriendImage = "";

    SharedPreferences shp;

    int friendId = 0;

    Typeface typeface;

    TextView userName;

    RoundedImageView userLogo;

    Toolbar toolbar;

    ImageView back;

    TextView heading;

    LinearLayout superView;

    Button submitButton;

    ImageView deleteChat;

    public String MerchantId, MerchantName, CampaignId, CampaignName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_chat);
        activity = this;

        deleteChat = (ImageView) findViewById(R.id.deleteChat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        back = (ImageView) findViewById(R.id.back);
        superView = (LinearLayout) findViewById(R.id.superView);
        UserId = SharedData.User_Id(activity);
        compose = (EditText) findViewById(R.id.compose);
        list = (ListView) findViewById(R.id.ChatList);
        send = (ImageView) findViewById(R.id.btn);
        userName = (TextView) findViewById(R.id.userName);
        userLogo = (RoundedImageView) findViewById(R.id.userLogo);

        back.setOnClickListener(this);

        friendId = getIntent().getIntExtra("FriendId", 0);
        FriendName = getIntent().getStringExtra("FriendName");
        userName.setText(FriendName);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/IRAN-Sans.ttf");
        db = ChatDatabase.getInstance(this);
        MerchantId = getIntent().getStringExtra("MerchantId");
        MerchantName = getIntent().getStringExtra("MerchantName");
        CampaignId = getIntent().getStringExtra("CampaignId");
        CampaignName = getIntent().getStringExtra("CampaignName");
        //Picasso.with(activity).load("http://84.241.33.46:130//Profile/" + friendId + "/profile.jpg").error(R.drawable.user_icon_header).placeholder(R.drawable.user_icon_header).into(userLogo);
        ManageChats();
//        registerReceiver(broadcastReceiver, new IntentFilter(
//                PushService.BROADCAST_ACTION));


//        deleteChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
//                builder1.setMessage(UTILITY.SetProgressDialogTypeface(activity, "آیا مطمئن هستید که همه چت ها را پاک کنید؟"));
//                builder1.setCancelable(true);
//                builder1.setPositiveButton(
//                        UTILITY.SetProgressDialogTypeface(activity, getResources().getString(R.string.yes_text)),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                dialog.cancel();
//                                db.DeleteAllChats(UserId, String.valueOf(friendId));
//                                adp.MessageList.clear();
//                                adp.notifyDataSetChanged();
//                            }
//                        });
//                builder1.setNegativeButton(
//                        UTILITY.SetProgressDialogTypeface(activity, getResources().getString(R.string.no_text)),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
//        });
//        if (MerchantId != null && MerchantId.length() > 0 && MerchantName != null && MerchantName.length() > 0) {
//            //compose.setText("<a href=\"arsam.club.arsamclub://view?merchantId=" + MerchantId + "\">Click to View Merchant " + MerchantName + "</a>");
//            compose.setText("<a href=\"http://www.arsam.club.arsamclub.com/launch?id=" + MerchantId + "\">Click to View Merchant " + MerchantName + "</a>");
//
//        }
//        if (CampaignId != null && CampaignId.length() > 0 && CampaignName != null && CampaignName.length() > 0) {
//            compose.setText("<a href=\"arsam.club.arsamclubcampaign://view?FestivalId=" + CampaignId + "\">Click to View Festival " + CampaignName + "</a>");
//        }
    }


    public void ManageChats() {

//        adp = new ChatArrayAdapter(activity, R.layout.chat, "Chat", "");
//        ArrayList<HashMap<String, String>> result = db.GetMessages(UserId, friendId);
//        ArsamClubApplication.UpdteaChatStatus(result, UserId);
//        for (int i = 0; i < result.size(); i++) {
//
//            boolean isLeft = (UserId.equals(result.get(i).get("SenderId").toString()) ? false : true);
//            adp.add(i, new ChatMessage(result.get(i).get("id").toString(), !isLeft, result.get(i).get("Message").toString(), result.get(i).get("DeliveryDate").toString(), "", 0, "", "", "", "", false));
//        }
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                try {
//                    if (!TextUtils.isEmpty(compose.getText())) {
//                        int position = adp.getCount();
//                        adp.add(position, new ChatMessage("0", true, compose.getText().toString(), String.valueOf(new Date().getTime()), "", 0, "", "", "", "", true));
//                        new AsyncTaskSendChat(position).execute(UserId, String.valueOf(friendId), compose.getText().toString());
//                        compose.setText("");
//                        list.setAdapter(adp);
//                        list.setSelection(adp.getCount() - 1);
//                    }
//                } catch (Exception ex) {
//                    System.out.println(ex.toString());
//                }
//            }
//        });
//        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//        list.setAdapter(adp);
//        list.setSelection(adp.getCount() - 1);
//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                showEditPopup(i);
//                return true;
//            }
//        });
//        adp.registerDataSetObserver(new DataSetObserver() {
//
//            public void OnChanged() {
//
//                super.onChanged();
//                try {
//                    list.setSelection(adp.getCount() - 1);
//                } catch (Exception ex) {
//                }
//            }
//        });
    }

//    public void showEditPopup(final int index) {
//
//        try {
//            AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
//            LinearLayout ll = new LinearLayout(activity);
//            ll.setOrientation(LinearLayout.HORIZONTAL);
//            ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            ll.setGravity(Gravity.CENTER);
////            ll.setBackgroundColor(Color.parseColor(shp.getString(Name_Struct.backgroundColorCode, "")));
//            Button edit = new Button(activity);
//            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            btnParams.setMargins(10, 150, 0, 150);
//            edit.setLayoutParams(btnParams);
//            edit.setText("پاسخ");
//            //UTILITY.setButtonStyle(edit, shp);
////            edit.setBackground(getResources().getDrawable(R.drawable.btn_primary));
////            edit.setTextColor(getResources().getColor(R.color.colorWhite));
//            Button remove = new Button(activity);
//            remove.setLayoutParams(btnParams);
//            remove.setText("Nes");
//            //   UTILITY.setButtonStyle(remove, shp);
////            remove.setBackground(getResources().getDrawable(R.drawable.btn_primary));
////            remove.setTextColor(getResources().getColor(R.color.colorWhite));
//            ll.addView(edit);
//            ll.addView(remove);
//            builder1.setView(ll);
//            final AlertDialog alert11 = builder1.create();
//            alert11.show();
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    compose.setText("\"" + adp.getItem(index).message + "\"");
//                    compose.requestFocus();
//                    alert11.dismiss();
//                }
//            });
//            remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    String id = adp.getItem(index).id;
//                    adp.removeItem(index);
//                    db.DeleteMessage(id);
//                    adp.notifyDataSetChanged();
//                    alert11.dismiss();
//                }
//            });
//        } catch (Exception ex) {
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

//    public class AsyncTaskSendChat extends AsyncTask<String, Void, JSONObject> {
//
//        int ChatPosition = 0;
//
//        public AsyncTaskSendChat(int position) {
//
//            ChatPosition = position;
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//
//            super.onPostExecute(jsonObject);
//            System.out.println(jsonObject);
//            db.SaveUser(UserId, friendId, FriendName);
//            try {
//                if (jsonObject != null) {
//                    if (jsonObject.getString("Status").equals("1")) {
//                        int historyid = jsonObject.getJSONObject("data").getInt("historyId");
//                        String lastMessage = (jsonObject.has("lastMessage") ? jsonObject.getString("lastMessage") : "");
//                        db.SaveNewMessage(historyid, Integer.valueOf(UserId), friendId, lastMessage, 0, 1);
//                        adp.MessageList.get(ChatPosition).isPending = false;
//                        adp.MessageList.get(ChatPosition).id = String.valueOf(historyid);
//                        adp.notifyDataSetChanged();
//                    }
//                } else {
//                    Intent i = new Intent(activity, PendingChatService.class);
//                    startService(i);
//                }
//            } catch (Exception ex) {
//                System.out.println(ex.toString());
//            }
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... strings) {
//
//            final String[] params = strings;
//            JSONObject jObject = Server_URL.chatSend(activity, strings[0], strings[1], strings[2]);
//            try {
//                if (jObject != null)
//                    jObject.put("lastMessage", strings[2]);
//                else
//                    db.SaveNewPendingMessage(Integer.valueOf(UserId), friendId, strings[2]);
//            } catch (Exception ex) {
//            }
//            return jObject;
//        }
//    }


    @Override
    protected void onPause() {

        super.onPause();
//        UTILITY.isChatOpen = false;
//        UTILITY.FriendId = 0;
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Utility.isChatOpen = false;
        Utility.FriendId = 0;
    }

    @Override
    protected void onResume() {

        super.onResume();
        Utility.isChatOpen = true;
        Utility.FriendId = friendId;
    }

    @Override
    public void onBackPressed() {

        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);
        if (taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else
            super.onBackPressed();
    }

    public void LoadChats() {

        //new GetChats().execute();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            LoadChats();

        }
    };

//    public class GetChats extends AsyncTask<String, String, JSONObject> {
//
//        ProgressDialog pdialog;
//
//        JSONObject json_object;
//
//        @Override
//        protected void onPreExecute() {
//            pdialog = new ProgressDialog(activity);
//            pdialog.setMessage(UTILITY.SetProgressDialogTypeface(activity, getResources().getString(R.string.please_wait)));
//            pdialog.setCancelable(false);
//            pdialog.show();
        }

//        @Override
//        protected JSONObject doInBackground(String... params) {
//
//            json_object = null;
//            try {
//                json_object = Server_URL.getLastMessage(activity, UserId, String.valueOf(friendId), null);
//            } catch (Exception ex) {
//            }
//            return json_object;
//        }

//        @Override
//        protected void onPostExecute(JSONObject object) {
////            pdialog.cancel();
//            try {
//                if (object != null) {
//                    if (object.getString("Status").equals("1")) {
//                        JSONArray jsonArray = object.getJSONArray("data");
//                        if (jsonArray != null && jsonArray.length() > 0) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObject = jsonArray.getJSONObject(i);
//                                JSONArray chatsArray = jObject.getJSONArray("chatHistoryAPIViewModel");
//                                if (chatsArray != null && chatsArray.length() > 0) {
//                                    for (int j = 0; j < chatsArray.length(); j++) {
//                                        JSONObject messageObject = chatsArray.getJSONObject(j);
//                                        db.SaveNewMessage(messageObject.getInt("id"), messageObject.getInt("userId"), messageObject.getInt("receiverId"), messageObject.getString("message"), UTILITY.ParseDateToMillis(messageObject.getString("sendMessageDate")), 0);
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        Toast.makeText(activity, object.getString("message"), Toast.LENGTH_LONG).show();
//                       // CustomToast.toast(activity, object.getString("message"),1);
//                    }
//                } else {
//                    Toast toast = Toast.makeText(activity, getResources().getString(R.string.network_error), Toast.LENGTH_LONG);
//                    View toastView = toast.getView(); // This'll return the default View of the Toast.
//
//                    /* And now you can get the TextView of the default View of the Toast. */
//                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
//                    toastMessage.setTypeface(typeface);
//                    toast.show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                //Toast.makeText(activity, getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
//            }
//            ManageChats();
//        }