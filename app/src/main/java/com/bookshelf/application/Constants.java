package com.bookshelf.application;

import com.bookshelf.data.Currency;
import com.google.android.gms.wallet.WalletConstants;

/**
 * Created by Krzysztof on 06.12.2017.
 */

public class Constants {
    public static final String API_SERVER_URL = "https://bookshelf.krzysztofabram.pl/api/";
    public static final String AUTH_SERVER_URL = "https://bookshelf.krzysztofabram.pl/auth/";
    public static final int AUTHENTICATION_CHECKUP_PERIOD = 30; // in seconds

    public static final int WALLET_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
    public static final Currency DEFAULT_CURRENCY = new Currency(Currency.Code.USD);

    public static final String SERVER = "http://commerce.krzysztofabram.pl";
    public static final String CLIENT_TOKEN = "clientToken";
}
