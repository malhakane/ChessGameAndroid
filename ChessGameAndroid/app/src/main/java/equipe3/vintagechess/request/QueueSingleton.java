package equipe3.vintagechess.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ablam on 2016-11-08.
 */

public class QueueSingleton {

    private static QueueSingleton mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private QueueSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized QueueSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new QueueSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
