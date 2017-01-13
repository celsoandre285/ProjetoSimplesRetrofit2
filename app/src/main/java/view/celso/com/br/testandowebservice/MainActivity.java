package view.celso.com.br.testandowebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import view.celso.com.br.testandowebservice.interaces.UdacityService;
import view.celso.com.br.testandowebservice.models.Course;
import view.celso.com.br.testandowebservice.models.Instructor;
import view.celso.com.br.testandowebservice.models.UdacityCatalog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://www.udacity.com/public-api/v0/courses

        //Declarando o retrofit
        Retrofit retrofit = new Retrofit.Builder()
                //URL de BASE
                .baseUrl(UdacityService.BASE_URL)
                //Adicionando o conversor um converter Gson //TER EXTREMA ATENÇÃO
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build())
                .build();

        //Implementando Interface
        UdacityService service = retrofit.create(UdacityService.class);
        Call<UdacityCatalog> requestCatalog = service.listCatalog();

        //Chamando metodo Assicrono
        requestCatalog.enqueue(new Callback<UdacityCatalog>() {
            //Obtendo Resposta
            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                //Verificando se houve resposta
                if(!response.isSuccessful()){
                    //caso a resposta seja vazia / ou tenha erro no servidor
                    Log.i("TAG", "Erro "+ response.code() );
                }else{
                    //caso tenha retorno
                    UdacityCatalog catalog = response.body();

                    for(Course c : catalog.courses){
                        Log.i("TAG","Cursos: " +c.title );
                        for(Instructor i : c.instructors ){
                            Log.i("TAG","Instrutores: " +i.name );
                        }
                        //Separador
                        Log.i("TAG","-------------------**-----------------------**---------------**" );
                    }
                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {
                // Caso nao tenha erro na conexao
                Log.i("TAG", t.getMessage() );
            }
        });
    }
}
