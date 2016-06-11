package com.luo.park.frame.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.luo.park.utils.Logger;
import com.luo.park.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


/**
 * 只处理文件上传下载等数据量大的请求，普通网络请求请使用Volley框架
 */
public class HttpUtil {
    private static Logger mlog = new Logger(HttpUtil.class.getSimpleName(), Log.DEBUG);
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getClient() {
        return client;
    }

    // 用一个完整url获取一个string对象
    public static void get(String urlString, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }

    // url里面带参数
    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    // 不带参数，获取json对象或者数组
    public static void get(String urlString, JsonHttpResponseHandler res) {
        client.get(urlString, res);
    }

    // 带参数，获取json对象或者数组
    public static void get(String urlString, RequestParams params, JsonHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    // 下载数据使用，会返回byte数据
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        client.get(uString, bHandler);
    }


    public static boolean checkNetwork() {
        return NetworkUtils.isNetConnected();
    }

    public static LinkedHashMap<String, Object> JsonToMap(JSONObject json) {
        Type typeOfT = new TypeToken<LinkedHashMap<String, Object>>() {
        }.getType();
        return new Gson().fromJson(json.toString(), typeOfT);
    }

    public static List<LinkedHashMap<String, Object>> JsonArrayToMapList(JSONArray json) {
        Type typeOfT = new TypeToken<List<LinkedHashMap<String, Object>>>() {
        }.getType();
        return new Gson().fromJson(json.toString(), typeOfT);
    }

    public static void debugRequest(String request) {
        if (request == null) return;
        mlog.d(request);
    }

    public static void debugHeaders(Header[] headers) {
        if (headers == null) return;
        mlog.v("--------" + "Response Headers" + "--------");
        for (Header header : headers) {
            String h = String.format(Locale.US, "%s : %s", header.getName(),
                    header.getValue());
            mlog.v(h);
        }
    }

    public static void debugStatusCode(int statusCode) {
        mlog.i("--------" + "Response Status Code" + "--------");
        String msg = String.format(Locale.getDefault(),
                "Status Code: %d", statusCode);
        mlog.i(msg);
    }

    public static void debugResponse(String response) {
        if (response == null) return;
        mlog.d("--------" + "Response Data" + "--------");
        mlog.d(response);

    }

    public static void debugThrowable(Throwable throwable) {
        if (throwable == null) return;
        mlog.w("--------" + "Response Error" + "--------");
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        mlog.w(sw);
    }


    public static void debugJson(JSONObject json) {
        if (json == null) return;
        mlog.w("--------" + "Response Json" + "--------");
        Type typeOfT = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = new Gson().fromJson(json.toString(), typeOfT);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            mlog.d(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void debugResult(int statusCode, Header[] headers, String responseString,
                                   JSONObject json, Throwable throwable) {
        debugHeaders(headers);
        debugStatusCode(statusCode);
        debugThrowable(throwable);
        debugResponse(responseString);
        debugJson(json);
    }

    public static class BaseResponseHandler extends
            JsonHttpResponseHandler {
        String mDiaglogTitle;
        String mDiaglogContent;
        Context mContext;
        private ProgressDialog mProgressDialog;

        public BaseResponseHandler() {
        }

        public BaseResponseHandler(Context context) {
            mContext = context;
        }

        public BaseResponseHandler(Context context, boolean showDefaultDialog) {
            this(context);
            if (showDefaultDialog) {
                mDiaglogContent = "加载中...";
            }
        }

        public BaseResponseHandler(Context context, String content) {
            this(context);
            mDiaglogContent = content;
        }

        public BaseResponseHandler(Context context, String title, String content) {
            this(context, content);
            mDiaglogTitle = title;
        }

        public void onSuccess(JSONObject response) {
        }

        public void onError(JSONObject errorResponse) {
        }

        @Override
        public void onStart() {
            if (mDiaglogContent != null) {
                mProgressDialog = ProgressDialog.show(mContext, mDiaglogTitle,
                        mDiaglogContent);
            }
        }

        @Override
        public void onFinish() {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            debugResult(statusCode, headers, null, response, null);
            onSuccess(response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, JSONObject errorResponse) {
            debugResult(statusCode, headers, null, errorResponse, throwable);
            onError(errorResponse);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              String responseString, Throwable throwable) {
            debugResult(statusCode, headers, responseString, null, throwable);
        }
    }
}
