package ru.rsue.moldavanova.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import ru.rsue.moldavanova.Address

interface AddressDepositoryApi {
     @GET("api/address")
    fun getAddressesAsync() : Deferred<Response<List<Address>>>


}