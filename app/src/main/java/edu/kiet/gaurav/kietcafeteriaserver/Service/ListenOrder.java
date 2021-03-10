package edu.kiet.gaurav.kietcafeteriaserver.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import edu.kiet.gaurav.kietcafeteriaserver.Model.Request;
import edu.kiet.gaurav.kietcafeteriaserver.OrderStatus;
import edu.kiet.gaurav.kietcafeteriaserver.R;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference orders;

    //press ctrl+o

    @Override
    public void onCreate() {
        super.onCreate();
        db=FirebaseDatabase.getInstance();
        orders=db.getReference("Requests");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public ListenOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //Trigger here
        Request request = dataSnapshot.getValue(Request.class);
        if(request.getStatus().equals("0"))
            showNotification(dataSnapshot.getKey(),request);

    }

    private void showNotification(String key, Request request) {
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        PendingIntent contentIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getBaseContext());

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("KIETcafe")
                .setContentInfo("New Order")
                .setContentText("You have new Order #"+key)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent);

        NotificationManager manager=(NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //if you want to many notification show, you need give unique Id for each Notification
        int randomInt=new Random().nextInt(9999-1)+1;
        manager.notify(randomInt,builder.build());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

