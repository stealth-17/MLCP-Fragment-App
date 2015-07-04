package com.ilp.innovations.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;


public class BookingUpdaterService extends IntentService {

    public static final String BROADCAST_ACTION_UPDATE_LEVELS="com.ilp.innovations.UPDATE_LEVELS";
    public static final String BROADCAST_ACTION_SLOT_ALLOCATION="com.ilp.innovations.SLOT_ALLOCATION";
    public static final String BROADCAST_ACTION_CONFIRM_PARKING="com.ilp.innovations.CONFIRM_PARKING";
    public static final String BROADCAST_ACTION_CLEAR_SLOT="com.ilp.innovations.CLEAR_SLOT";
    public static final String BROADCAST_ACTION_CHANGE_SLOT="com.ilp.innovations.CHANGE_SLOT";
    public static final String ACTION_UPDATE_LEVELS="getFloors";
    public static final String ACTION_SLOT_ALLOCATION="getBookingDetails";
    public static final String ACTION_CONFIRM_PARKING="confirmParking";
    public static final String ACTION_CLEAR_SLOT="clearSlot";
    public static final String ACTION_CHANGE_SLOT="changeSlot";


    private static final String SERVER_ADDR = "192.168.1.100";
    private String BROADCAST_ACTION;

    public BookingUpdaterService() {
        super("BookingUpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("myTag","Service started");
        if (intent != null) {
            final String action = intent.getAction();
            Map<String,String> params = new HashMap<String,String>();
            if(action.equals(ACTION_UPDATE_LEVELS)) {
                params.put("tag", ACTION_UPDATE_LEVELS);
                BROADCAST_ACTION = BROADCAST_ACTION_UPDATE_LEVELS;
            }
            else if(action.equals(ACTION_SLOT_ALLOCATION)) {
                String lastBookingId = intent.getStringExtra("bookingId");
                params.put("tag",ACTION_SLOT_ALLOCATION);
                params.put("bookingId",lastBookingId);
                BROADCAST_ACTION = BROADCAST_ACTION_SLOT_ALLOCATION;
            }
            else if(action.equals(ACTION_CHANGE_SLOT)) {
                String bookingId = intent.getStringExtra("bookingId");
                String vehicleNum = intent.getStringExtra("regNo");
                params.put("tag",ACTION_CHANGE_SLOT);
                params.put("bookingId",bookingId);
                params.put("regNo",vehicleNum);
                BROADCAST_ACTION = BROADCAST_ACTION_CHANGE_SLOT;
            }
            else if(action.equals(ACTION_CONFIRM_PARKING)) {
                String bookingId = intent.getStringExtra("bookingId");
                params.put("tag","confirm");
                params.put("bookingId",bookingId);
                BROADCAST_ACTION = BROADCAST_ACTION_CONFIRM_PARKING;
            }
            else if(action.equals(ACTION_CLEAR_SLOT)) {
                String slotId = intent.getStringExtra("slotId");
                params.put("tag",ACTION_CLEAR_SLOT);
                params.put("slotId",slotId);
                BROADCAST_ACTION = BROADCAST_ACTION_CLEAR_SLOT;
            }
            new HttpRequestTask(params).execute(SERVER_ADDR);
        }
    }

    private class HttpRequestTask extends AsyncTask<String, String, String> {

        private Map<String,String> params;

        public HttpRequestTask(Map<String,String> data) {
            this.params = data;
        }

        protected String doInBackground(String... urls) {

            String response=null;
            try {
                String url = "http://" + urls[0] + "/mlcp/index.php";
                Log.d("myTag", url);
                response = HttpRequest.post(url)
                        .accept("application/json")
                        .form(params)
                        .body();
                Log.d("myTag","Response-->"+response);

            } catch (HttpRequest.HttpRequestException exception) {
                exception.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  response;
        }


        protected void onPostExecute(String response) {
            Intent notification = new Intent();
            Log.d("myTag",BROADCAST_ACTION);
            notification.setAction(BROADCAST_ACTION);
            /*
            *The below commented code is reqd when the intent has to start an activity which
            *is already in stopped state
            */
            //notification.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            notification.putExtra("response",response);
            sendBroadcast(notification);

        }
    }

}
