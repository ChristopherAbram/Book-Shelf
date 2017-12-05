package service.receiver;

import android.os.Bundle;

/**
 * Created by Krzysztof on 05.12.2017.
 */

public interface RequestResultListener {
    void onReceiveResult(int resultCode, Bundle resultData);
}
