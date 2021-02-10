package com.example.nextoliveproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatDatabase extends SQLiteOpenHelper {

    private final static String PENDING_CONVERSATION_DB_NAME = "PendingConversations";

    private final static String CONVERSATION_DB_NAME = "Conversations";

    private final static String FRIENDS_TABLE = "Friends";

    private final static String Notifications_TABLE = "Notifications";

    private final static int DB_VERSION = 2;

    private final static String LASTREAD_TABLE = "LastReadDate";

    private static ChatDatabase instance;

    private static final AtomicInteger openCounter = new AtomicInteger();

    private ChatDatabase(final Context context) {

        super(context, CONVERSATION_DB_NAME, null, DB_VERSION);
    }

    public static synchronized ChatDatabase getInstance(final Context c) {

        if (instance == null) {
            instance = new ChatDatabase(c.getApplicationContext());
        }
        openCounter.incrementAndGet();
        return instance;
    }

    @Override
    public void close() {

        if (openCounter.decrementAndGet() == 0) {
            super.close();
        }
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + CONVERSATION_DB_NAME + " (ID INTEGER PRIMARY KEY, SenderID INTEGER, RecieverID INTEGER, DeliveryStatus INTEGER,Message TEXT,DeliveryDate TEXT,IsDeleted INTEGER)");
        db.execSQL("CREATE TABLE " + FRIENDS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,LoggedInUserID INTEGER, UserID INTEGER,UserName TEXT)");
        db.execSQL("CREATE TABLE " + Notifications_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, RequestID INTEGER,MessageBody TEXT,LandingScreen TEXT,NotificationDate TEXT)");
        db.execSQL("CREATE TABLE " + PENDING_CONVERSATION_DB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SenderID INTEGER, RecieverID INTEGER, DeliveryStatus INTEGER,Message TEXT,DeliveryDate TEXT)");
        db.execSQL("CREATE TABLE " + LASTREAD_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LoginUserID INTEGER,LastReadTime TEXT)");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop PRIMARY KEY constraint
        db.execSQL("CREATE TABLE " + CONVERSATION_DB_NAME + "2 (ID INTEGER PRIMARY KEY, SenderID INTEGER, RecieverID INTEGER, DeliveryStatus INTEGER,Message TEXT,DeliveryDate TEXT,IsDeleted INTEGER)");
        db.execSQL("CREATE TABLE " + FRIENDS_TABLE + "2 (ID INTEGER PRIMARY KEY AUTOINCREMENT,LoggedInUserID INTEGER, UserID INTEGER,UserName TEXT)");
//        db.execSQL("INSERT INTO " + CONVERSATION_DB_NAME + "2 (SenderID,RecieverID,DeliveryStatus,Message,DeliveryDate,IsDeleted) SELECT SenderID,RecieverID,DeliveryStatus,Message,DeliveryDate,IsDeleted FROM " +
//                CONVERSATION_DB_NAME);
//        db.execSQL("INSERT INTO " + FRIENDS_TABLE + "2 (LoggedInUserID ,UserID,UserName) SELECT LoggedInUserID,UserID,UserName FROM " +
//                FRIENDS_TABLE);
        db.execSQL("DROP TABLE " + CONVERSATION_DB_NAME);
        db.execSQL("DROP TABLE " + FRIENDS_TABLE);
        db.execSQL("ALTER TABLE " + CONVERSATION_DB_NAME + "2 RENAME TO " + CONVERSATION_DB_NAME + "");
        db.execSQL("ALTER TABLE " + FRIENDS_TABLE + "2 RENAME TO " + FRIENDS_TABLE + "");
        db.execSQL("CREATE TABLE " + Notifications_TABLE + "2 (ID INTEGER PRIMARY KEY AUTOINCREMENT, RequestID INTEGER,MessageBody TEXT,LandingScreen TEXT,NotificationDate TEXT)");
//        db.execSQL("INSERT INTO " + Notifications_TABLE + "2 (RequestID,MessageBody,LandingScreen,NotificationDate) SELECT RequestID,MessageBody,LandingScreen,NotificationDate FROM " +
//                Notifications_TABLE);
        db.execSQL("DROP TABLE " + Notifications_TABLE);
        db.execSQL("ALTER TABLE " + Notifications_TABLE + "2 RENAME TO " + Notifications_TABLE + "");
        db.execSQL("DROP TABLE " + PENDING_CONVERSATION_DB_NAME);
        db.execSQL("CREATE TABLE " + PENDING_CONVERSATION_DB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SenderID INTEGER, RecieverID INTEGER, DeliveryStatus INTEGER,Message TEXT,DeliveryDate TEXT)");
//        db.execSQL("CREATE TABLE " + LASTREAD_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LoginUserID INTEGER,LastReadTime TEXT)");
//        db.execSQL("DROP TABLE " + LASTREAD_TABLE);
    }

    public Cursor CheckIfUserExists(String LoggedInUserID, int UserId) {

        Cursor c = getWritableDatabase().rawQuery("Select * from Friends where LoggedInUserID="+LoggedInUserID+" and UserId=" + UserId + "", null);
        return c;
    }

    public ArrayList<HashMap<String, Object>> GetLastReadMessage(int LoginUserID) {

        Cursor c = getWritableDatabase().rawQuery("Select * from "+LASTREAD_TABLE+" where LoginUserID=" + LoginUserID + "", null);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, Object> messageObj = new HashMap<>();
                messageObj.put("LoginUserID", c.getString(1));
                messageObj.put("LastReadTime", c.getString(2));
                result.add(messageObj);
            } while (c.moveToNext());
        }
        return result;

    }



    public int SaveLastRead(int LoginUserID) {

        ArrayList<HashMap<String, Object>> result = GetLastReadMessage(LoginUserID);
        int rowCount = result.size();
        if (rowCount == 0) {
            ContentValues values = new ContentValues();
            values.put("LoginUserID", LoginUserID);
            values.put("LastReadTime", (new Date()).getTime());
            getWritableDatabase().insert(LASTREAD_TABLE, null, values);
            rowCount=1;
        }
        else
            getWritableDatabase().rawQuery("Update "+LASTREAD_TABLE+" set LastReadTime='"+(new Date()).getTime()+"' where LoginUserID=" + LoginUserID + "", null);

        return rowCount;
    }


    public Cursor CheckIfMessageExists(int historyId) {

        Cursor c = getWritableDatabase().rawQuery("Select * from Conversations where ID=" + historyId + "", null);
        return c;
    }


    public int SaveUser(String LoggedInUserID, int UserId, String UserName) {

        Cursor c = CheckIfUserExists(LoggedInUserID,UserId);
        int rowCount = c.getCount();
        if (rowCount == 0) {
            ContentValues values = new ContentValues();
            values.put("LoggedInUserID", LoggedInUserID);
            values.put("UserId", UserId);
            values.put("UserName", UserName);

            getWritableDatabase().insert(FRIENDS_TABLE, null, values);
        }
        else
            c = getWritableDatabase().rawQuery("Update Friends set UserName='"+UserName+"' where UserId=" + UserId + "", null);

        return rowCount;
    }

    public void SaveNewMessage(int historyId, int SenderId, int ReceiverId, String Message, long datetime, int status) {
        Cursor c = CheckIfMessageExists(historyId);
        int rowCount = c.getCount();
        if (rowCount == 0) {
            ContentValues values = new ContentValues();
            values.put("Id", historyId);
            values.put("SenderId", SenderId);
            values.put("RecieverID", ReceiverId);
            values.put("Message", Message);
            values.put("DeliveryStatus", status);
            values.put("DeliveryDate", (datetime > 0 ? datetime : (new Date()).getTime()));
            long id = getWritableDatabase().insert(CONVERSATION_DB_NAME, null, values);
            System.out.println(id);
        }
        else
        {
            c = getWritableDatabase().rawQuery("Update Conversations set DeliveryDate='"+ (datetime > 0 ? datetime : (new Date()).getTime())+"' where ID=" + historyId + "", null);
        }
    }

    public ArrayList<HashMap<String, Object>> GetChatList(String UserId) {

        String query="Select UserId,UserName,\n" +
                "(Select Message from Conversations WHERE ((SenderID=Friends.UserId  AND RecieverID="+UserId+") OR ((RecieverID=Friends.UserId  AND SenderID="+UserId+"))) AND IsDeleted IS NULL Order by ID desc  LIMIT 1)," +
                "(Select Id from Conversations WHERE ((SenderID=Friends.UserId  AND RecieverID="+UserId+") OR ((RecieverID=Friends.UserId  AND SenderID="+UserId+"))) AND IsDeleted IS NULL Order by ID desc  LIMIT 1) AS 'HistoryId'," +
                "(Select Count(*) from Conversations WHERE (SenderID=Friends.UserId  AND RecieverID="+UserId+") AND IsDeleted IS NULL AND DeliveryStatus!='3' Order by ID desc  LIMIT 1) AS 'UnreadCount'" +
                " from Friends where LoggedInUserID="+UserId+" group by Friends.UserId Order by HistoryId desc";

        Cursor c = getWritableDatabase().rawQuery(query, null);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, Object> messageObj = new HashMap<>();
                messageObj.put("UserId", c.getString(0));
                messageObj.put("UserName", c.getString(1));
                messageObj.put("Message", c.getString(2));
                messageObj.put("UnreadCount", c.getString(4));
                result.add(messageObj);
            } while (c.moveToNext());
        }
        return result;
    }

    public ArrayList<HashMap<String, String>> GetPendingMessages() {

        Cursor c = getWritableDatabase().rawQuery("Select * from " + PENDING_CONVERSATION_DB_NAME + " ", null);
        int max = c.getCount();
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> messageObj = new HashMap<>();
                messageObj.put("Id", c.getString(0));
                messageObj.put("SenderId", c.getString(1));
                messageObj.put("RecieverId", c.getString(2));
                messageObj.put("DeliveryStatus", c.getString(3));
                messageObj.put("Message", c.getString(4));
                messageObj.put("DeliveryDate", c.getString(5));
                result.add(messageObj);
            } while (c.moveToNext());
        }
        return result;
    }

    public void SaveNewPendingMessage(int SenderId, int ReceiverId, String Message) {

        ContentValues values = new ContentValues();
        values.put("SenderId", SenderId);
        values.put("RecieverID", ReceiverId);
        values.put("Message", Message);
        values.put("DeliveryStatus", 0);
        values.put("DeliveryDate", (new Date()).getTime());
        long id = getWritableDatabase().insert(PENDING_CONVERSATION_DB_NAME, null, values);
        System.out.println(id);
    }

    public void DeleteMessage(String id) {

        getWritableDatabase().execSQL("Update " + CONVERSATION_DB_NAME + " Set IsDeleted=1  where ID=" + id + "");
    }

    public void DeletePendingMessage(String id) {

        getWritableDatabase().execSQL("Delete from " + PENDING_CONVERSATION_DB_NAME + " where Id=" + id + "");
    }

    public ArrayList<HashMap<String, String>> GetMessages(String UserId, int friendId) {

        Cursor c = getWritableDatabase().rawQuery("Select id,Message,DeliveryDate,SenderID,IsDeleted,DeliveryStatus from " + CONVERSATION_DB_NAME + " Where ((SenderID=" + UserId + " AND RecieverID="+friendId+")  Or (RecieverID=" + UserId + " AND SenderID=" + friendId + ")) And IsDeleted IS NULL", null);
        int max = c.getCount();
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> messageObj = new HashMap<>();
                messageObj.put("id", c.getString(0));
                messageObj.put("Message", c.getString(1));
                messageObj.put("DeliveryDate", c.getString(2));
                messageObj.put("SenderId", c.getString(3));
                messageObj.put("IsDeleted", c.getString(4));
                messageObj.put("DeliveryStatus", c.getString(5));
                result.add(messageObj);
            } while (c.moveToNext());
        }
        try
        {
            getWritableDatabase().execSQL("Update " + CONVERSATION_DB_NAME + " SET DeliveryStatus=3 Where (RecieverID=" + UserId + " AND SenderID=" + friendId + ")");
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }

        return result;
    }

    public ArrayList<HashMap<String, Object>> GetNotificationsList() {

        Cursor c = getWritableDatabase().rawQuery("Select * from " + Notifications_TABLE + " Order by NotificationDate desc", null);
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, Object> messageObj = new HashMap<>();
                messageObj.put("LandingScreen", c.getString(3));
                messageObj.put("MessageBody", c.getString(2));
                messageObj.put("Date", c.getString(4));
                messageObj.put("RequestId", c.getString(1));
                messageObj.put("Id", c.getString(0));
                result.add(messageObj);
            } while (c.moveToNext());
        }
        return result;
    }

    public void SaveNewNotification(String RequestId, String landingScreen, String Message) {

        ContentValues values = new ContentValues();
        values.put("RequestId", RequestId);
        values.put("LandingScreen", landingScreen);
        values.put("MessageBody", Message);
        values.put("NotificationDate", (new Date()).getTime());
        long id = getWritableDatabase().insert(Notifications_TABLE, null, values);
        System.out.println(id);
    }

    public void DeleteAllRecords() {

        getWritableDatabase().execSQL("Delete from " + CONVERSATION_DB_NAME);
        getWritableDatabase().execSQL("Delete from " + FRIENDS_TABLE);
        getWritableDatabase().execSQL("Delete from " + FRIENDS_TABLE);
    }


    public void DeleteAllChats(String UserId, String friendId) {
        getWritableDatabase().execSQL("Update " + CONVERSATION_DB_NAME+" SET IsDeleted=1 Where ((SenderID=" + UserId + " AND RecieverID="+friendId+")  Or (RecieverID=" + UserId + " AND SenderID=" + friendId + "))");
    }

}