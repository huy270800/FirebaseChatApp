package square.ball.firebasechatapp

import com.codingwithme.firebasechat.Constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import square.ball.firebasechatapp.`interface`.NotificationApi

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(NotificationApi::class.java)
        }
    }
}