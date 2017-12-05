package service.receiver;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Krzysztof on 05.12.2017.
 */

public class RequestResultReceiver extends ResultReceiver {

    private RequestResultListener mRequestResultListener;

    public RequestResultReceiver(Handler handler) {
        super(handler);
    }

    public void setRequestResultListener(RequestResultListener requestResultListener){
        mRequestResultListener = requestResultListener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(mRequestResultListener != null)
            mRequestResultListener.onReceiveResult(resultCode, resultData);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }
}
