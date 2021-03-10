package edu.kiet.gaurav.kietcafeteriaserver.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Gaurav on 19-01-2021.
 */

public class RetrofitClient {
    private static Retrofit retrofit= null;

    public static Retrofit getClient (String baseUrl)
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
