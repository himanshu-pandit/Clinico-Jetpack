package com.clinicodiagnostic.data.repository.login

import com.clinicodiagnostic.data.model.LoginOtpRequest
import com.clinicodiagnostic.data.model.LoginOtpResponse
import com.clinicodiagnostic.data.remote.RetrofitInstance
import com.clinicodiagnostic.utils.Constant
import com.clinicodiagnostic.utils.NetworkResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

class LoginRepositoryImpl: LoginRepository {

    private val retrofitInstance = RetrofitInstance.getRetrofitInstance(Constant.SUPPORT_BASE_URL, 1)

    override fun getLoginOtp(
        loginOtpRequest: LoginOtpRequest,
        callback: (NetworkResult<LoginOtpResponse>) -> Unit
    ) {

        callback(NetworkResult.Loading())

        val call = retrofitInstance?.generateOtp(loginOtpRequest)

        call?.enqueue(object: Callback<LoginOtpResponse>{
            override fun onResponse(p0: Call<LoginOtpResponse>, response: Response<LoginOtpResponse>) {

                if(response.isSuccessful && response.body() != null){
                    callback(NetworkResult.Success(response.body()!!))
                }else{
                    callback(NetworkResult.Failure(response.message(), null))
                }

            }

            override fun onFailure(p0: Call<LoginOtpResponse>, throwable: Throwable) {
                var message = ""
                message = when (throwable) {
                    is SocketException, is UnknownHostException -> {
                        "Please check your internet Connection and try again."
                    }

                    is HttpException -> {
                        "Server error: Please try again later."
                    }

                    is IOException -> {
                        "Network error: Try again later"
                    }

                    else -> {
                        "Something went wrong, Please try again later."
                    }
                }
                callback(NetworkResult.Failure(message, null))

            }

        })

    }
}