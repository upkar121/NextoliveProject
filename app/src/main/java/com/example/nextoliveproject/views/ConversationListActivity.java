package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nextoliveproject.database.SaredPreference.SharedData;
import com.example.nextoliveproject.MainActivity;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.ChatDatabase;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConversationListActivity extends AppCompatActivity {
    ListView listView;

    Context context;

    int count;

    Activity activity;

    ArrayList<HashMap<String, Object>> conversationsList = new ArrayList<>();

    ChatDatabase db;

    String UserId = "", lastReadTime = "";

    SharedPreferences shp;

    Typeface typeface;

    FloatingActionButton addButton;

    LinearLayout superView;

    Boolean hasNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        context = ConversationListActivity.this;
        activity = this;
        superView = (LinearLayout) findViewById(R.id.superView);
        if (!Utility.CheckIfUserLoggedIn(activity)) {
            Intent i = new Intent(activity, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        db = ChatDatabase.getInstance(this);

        UserId = SharedData.User_Id(context);
        //


        listView = (ListView) findViewById(R.id.listView);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);

    }

    public void NavigateToFriendsList(View v) {

//        Intent i = new Intent(activity, ContactToSendMessageActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onResume() {

        super.onResume();

//        ArrayList<HashMap<String, Object>> chatsList = db.GetChatList(UserId);
//        if (chatsList != null && chatsList.size() > 0) {
//            ArrayList<HashMap<String, Object>> getLastView = db.GetLastReadMessage(Integer.valueOf(UserId));
//            if (getLastView != null && getLastView.size() > 0) {
//                lastReadTime = Utility.ParseMillisTotDate(Long.valueOf(getLastView.get(0).get("LastReadTime").toString()));
//                db.SaveLastRead(Integer.valueOf(UserId));
//                new GetChatsByDateTime().execute();
//            } else {
//                db.SaveLastRead(Integer.valueOf(UserId));
//                new GetChats().execute();
//            }
//        } else {
//            db.SaveLastRead(Integer.valueOf(UserId));
//            new GetChats().execute();
//        }

        // GetRecentChats();
    }

//    private void GetRecentChats() {
//
//        ArrayList<HashMap<String, Object>> chatsList = db.GetChatList(UserId);
//        conversationsList = new ArrayList<>();
//        for (int i = 0; i < chatsList.size(); i++) {
//            conversationsList.add(i, chatsList.get(i));
//        }
//        ConversationListAdapter customAdapter = new ConversationListAdapter(activity, conversationsList);
//        listView.setAdapter(customAdapter);
//    }


//    public class GetChatsByDateTime extends AsyncTask<String, String, JSONObject> {
//
//        ProgressDialog pdialog;
//
//        JSONObject json_object;
//
//        @Override
//        protected void onPreExecute() {
//
//            pdialog = new ProgressDialog(activity);
//            pdialog.setMessage(UTILITY.SetProgressDialogTypeface(activity, getResources().getString(R.string.please_wait)));
//            pdialog.setCancelable(false);
//            pdialog.show();
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//
//            json_object = null;
//            try {
//                json_object = Server_URL.GetChatsByDateTime(activity, lastReadTime);
//            } catch (Exception ex) {
//            }
//            return json_object;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject object) {
//            //if (conversationsList != null && conversationsList.size() > 0)
//            pdialog.cancel();
//            try {
//                if (object != null) {
//                    if (object.getString("Status").equals("1")) {
//                        JSONArray jsonArray = object.getJSONArray("data");
//                        if (jsonArray != null && jsonArray.length() > 0) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObject = jsonArray.getJSONObject(i);
//                                HashMap<String, Object> chatObj = new HashMap<>();
//                                int friendId = jObject.getInt("receiverId");
//                                String friendName = jObject.getJSONObject("receiver").getString("firstName") + " " + jObject.getJSONObject("receiver").getString("lastName");
//                                String friendImage = jObject.getJSONObject("receiver").getString("image");
//                                db.SaveUser(UserId, friendId, friendName);
//                                JSONArray chatsArray = jObject.getJSONArray("chatHistoryAPIViewModel");
//                                if (chatsArray != null && chatsArray.length() > 0) {
//                                    for (int j = 0; j < chatsArray.length(); j++) {
//                                        JSONObject messageObject = chatsArray.getJSONObject(j);
//                                        db.SaveNewMessage(messageObject.getInt("id"), messageObject.getInt("userId"), messageObject.getInt("receiverId"), messageObject.getString("message"), UTILITY.ParseDateToMillis(messageObject.getString("sendMessageDate")), messageObject.getInt("deliveryStatus"));
//                                    }
//                                }
//                                //db.SaveNewMessage(getIntent().getIntExtra("SenderId", 0), Integer.valueOf(UserId), getIntent().getStringExtra("Message"));
//                            }
//                        }
//                    } else {
//                        //Toast.makeText(activity, object.getString("message"), Toast.LENGTH_LONG).show();
//                        CustomToast.toast(activity, object.getString("message"), 1);
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
//                System.out.println(e);
//                //Toast.makeText(activity, getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
//            } finally {
////                if (pdialog.isShowing())
////                    pdialog.cancel();
//                GetRecentChats();
//            }
//        }
//    }


//    public class GetChats extends AsyncTask<String, String, JSONObject> {
//
//        ProgressDialog pdialog;
//
//        JSONObject json_object;
//
//        @Override
//        protected void onPreExecute() {
//
//            pdialog = new ProgressDialog(activity);
//            pdialog.setMessage(UTILITY.SetProgressDialogTypeface(activity, getResources().getString(R.string.please_wait)));
//            pdialog.setCancelable(false);
//            pdialog.show();
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... params) {
//
//            json_object = null;
//            try {
//                json_object = Server_URL.GetChats(activity, UserId);
//            } catch (Exception ex) {
//            }
//            return json_object;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject object) {
//            //if (conversationsList != null && conversationsList.size() > 0)
//            pdialog.cancel();
//            try {
//                if (object != null) {
//                    if (object.getString("Status").equals("1")) {
//                        JSONArray jsonArray = object.getJSONArray("data");
//                        if (jsonArray != null && jsonArray.length() > 0) {
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jObject = jsonArray.getJSONObject(i);
//                                HashMap<String, Object> chatObj = new HashMap<>();
//                                int friendId = jObject.getInt("receiverId");
//                                String friendName = jObject.getJSONObject("receiver").getString("firstName") + " " + jObject.getJSONObject("receiver").getString("lastName");
//                                String friendImage = jObject.getJSONObject("receiver").getString("image");
//                                db.SaveUser(UserId, friendId, friendName);
//                                JSONArray chatsArray = jObject.getJSONArray("chatHistoryAPIViewModel");
//                                if (chatsArray != null && chatsArray.length() > 0) {
//                                    for (int j = 0; j < chatsArray.length(); j++) {
//                                        JSONObject messageObject = chatsArray.getJSONObject(j);
//                                        db.SaveNewMessage(messageObject.getInt("id"), messageObject.getInt("userId"), messageObject.getInt("receiverId"), messageObject.getString("message"), UTILITY.ParseDateToMillis(messageObject.getString("sendMessageDate")), messageObject.getInt("deliveryStatus"));
//                                    }
//                                }
//                                //db.SaveNewMessage(getIntent().getIntExtra("SenderId", 0), Integer.valueOf(UserId), getIntent().getStringExtra("Message"));
//                            }
//                        }
//                    } else {
//
//                        Toast.makeText(context, object.getString("message"), Toast.LENGTH_SHORT).show();
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
//                System.out.println(e);
//                //Toast.makeText(activity, getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
//            } finally {
////                if (pdialog.isShowing())
////                    pdialog.cancel();
//                GetRecentChats();
//            }
//        }
//    }

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
}