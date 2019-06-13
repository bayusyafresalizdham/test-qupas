package com.apps.testprojek.data.remote;

import com.apps.testprojek.utils.ConstantUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 5/14/16.
 */
public class RetrofitHelper {
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantUtils.ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofit;
    }

}
