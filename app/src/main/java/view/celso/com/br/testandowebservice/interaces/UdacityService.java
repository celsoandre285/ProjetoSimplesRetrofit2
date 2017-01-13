package view.celso.com.br.testandowebservice.interaces;

import retrofit2.Call;

import retrofit2.http.GET;
import view.celso.com.br.testandowebservice.models.UdacityCatalog;

/**
 * Created by celso on 13/01/2017.
 */

public interface UdacityService {
    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/";
    @GET("courses")
    Call<UdacityCatalog> listCatalog();
}
