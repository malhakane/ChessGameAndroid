/*
package equipe3.vintagechess.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RequestGET<T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    public RequestGET(int method,
                      String url,
                      Response.ErrorListener listener,
                      Class<T> clazz,
                      Map<String, String> headers,
                      Response.Listener<T> listener1) {
        super(method, url, listener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener1;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    RequestQueue queue = MyVolley.getRequestQueue();
GsonRequest<MyClass> myReq = new GsonRequest<MyClass>(Method.GET,
                                                    "http://JSONURL/",
                                                    TagList.class,
                                                    createMyReqSuccessListener(),
                                                    createMyReqErrorListener());

            queue.add(myReq);


            private Response.Listener<MyClass> createMyReqSuccessListener() {
    return new Response.Listener<MyClass>() {
        @Override
        public void onResponse(MyClass response) {
           // Do whatever you want to do with response;
           // Like response.tags.getListing_count(); etc. etc.
        }
    };
}
and

private Response.ErrorListener createMyReqErrorListener() {
    return new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            // Do whatever you want to do with error.getMessage();
        }
    };
}



}
*/