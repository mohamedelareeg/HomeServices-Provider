package com.rovaindu.serviesdashboard.retrofit;

import com.rovaindu.serviesdashboard.retrofit.models.UpdatePassword;
import com.rovaindu.serviesdashboard.retrofit.models.UserSendComplain;
import com.rovaindu.serviesdashboard.retrofit.response.AgentCompleteRegisterResponse;
import com.rovaindu.serviesdashboard.retrofit.response.AgentRegisterResponse;
import com.rovaindu.serviesdashboard.retrofit.response.AgentsResponse;
import com.rovaindu.serviesdashboard.retrofit.response.BankResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CartResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CategoryResponse;
import com.rovaindu.serviesdashboard.retrofit.response.ChangeOrderResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CheckForgetCode;
import com.rovaindu.serviesdashboard.retrofit.response.CityResponse;
import com.rovaindu.serviesdashboard.retrofit.response.ComplainsResponse;
import com.rovaindu.serviesdashboard.retrofit.response.ConfirmOrderResponse;
import com.rovaindu.serviesdashboard.retrofit.response.ContactUsResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CountryResponse;
import com.rovaindu.serviesdashboard.retrofit.response.CurrentOrders;
import com.rovaindu.serviesdashboard.retrofit.response.DeleteOrderResponse;
import com.rovaindu.serviesdashboard.retrofit.response.ForgetPasswordResponse;
import com.rovaindu.serviesdashboard.retrofit.response.JobResponse;
import com.rovaindu.serviesdashboard.retrofit.response.MakeOrderResponse;
import com.rovaindu.serviesdashboard.retrofit.response.MyOrdersResponse;
import com.rovaindu.serviesdashboard.retrofit.response.PlanResponse;
import com.rovaindu.serviesdashboard.retrofit.response.RenewPassword;
import com.rovaindu.serviesdashboard.retrofit.response.ServiesResponse;
import com.rovaindu.serviesdashboard.retrofit.response.UpdateProfileResponse;
import com.rovaindu.serviesdashboard.retrofit.response.UserRegisterResponse;
import com.rovaindu.serviesdashboard.retrofit.response.UserResponse;
import com.rovaindu.serviesdashboard.retrofit.response.UserTokenResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @POST("api/agents/login")
    Call<UserResponse> Login(
            @Body RequestBody params
    );

    @POST("api/users/register")
    Call<UserRegisterResponse> Register(
            @Body RequestBody params
    );
    @POST("api/agents/update_profile")
    Call<UpdateProfileResponse> update_profile(
            @Body RequestBody params
    );
    @POST("api/forget_password")
    Call<ForgetPasswordResponse> forget_password(
            @Body RequestBody params
    );

    @POST("api/check_forget_code")
    Call<CheckForgetCode> check_forget_code(
            @Body RequestBody params
    );

    @POST("api/renew_password")
    Call<RenewPassword> renew_password(
            @Body RequestBody params
    );
    @POST("api/send_user_complain")
    Call<UserSendComplain> send_user_complain(
            @Body RequestBody params
    );
    @POST("api/users/change_password")
    Call<UpdatePassword> change_password(
            @Body RequestBody params
    );

    @POST("api/agents/register")
    Call<AgentRegisterResponse> RegisterAgent(
            @Body RequestBody params
    );
    @POST("api/agents/complete_registeration")
    Call<AgentCompleteRegisterResponse> CompleteRegisterAgent(
            @Body RequestBody params
    );
    @POST("api/agents/update_fcm_token")
    Call<UserTokenResponse> UpdateFCM(
            @Body RequestBody params
    );

    @GET("api/agents/orders")
    Call<CurrentOrders> orders(
    );
    @GET("api/categories")
    Call<CategoryResponse> getAllCategories();

    @GET("api/countries")
    Call<CountryResponse> getAllCountries();


    @GET("api/countries/{country}/cities")
    Call<CityResponse> getAllCities(@Path("country") int country);


    @GET("api/banks")
    Call<BankResponse> getAllBanks();

    @GET("api/plans")
    Call<PlanResponse> getAllPlans();

    @GET("api/jobs")
    Call<JobResponse> getAllJobs();


    @PUT("/api/agents/orders/{order_Id}/change_status")
    Call<ChangeOrderResponse> ChangeOrderStatus(@Path("order_Id") int order_Id,
                                                @Field("status") int status);


    @GET("api/jobs/{jobid}/services")
    Call<ServiesResponse> getAllServies(@Path("jobid") int jobid);

    @POST("api/make_order")
    Call<MakeOrderResponse> make_order(
            @Body RequestBody params
    );
    @PUT("api/confirm_order/{order_Id}")
    Call<ConfirmOrderResponse> ConfirmOrder(@Path("order_Id") int order_Id,
                                            @Body RequestBody params);
    @DELETE("api/delete_order/{order_Id}")
    Call<DeleteOrderResponse> DeleteOrder(@Path("order_Id") int order_Id,
                                          @Body RequestBody params);

    @GET("api/agents/get_by_services")
    Call<AgentsResponse> getAllAgents(
            @QueryMap Map<String, String> services
    );
    @GET("api/orders")
    Call<MyOrdersResponse> getAllOrders();

    @POST("api/contact_us")
    Call<ContactUsResponse> contact_us(
            @Body RequestBody params
    );
    @GET("api/get_user_complain")
    Call<ComplainsResponse> getAllComplain(

    );
    @GET("api/cart_page")
    Call<CartResponse> getCartPage(

    );

    /*
    //Register
    @FormUrlEncoded
    @POST("updatecode")
    Call<CodeResponse> updateCode(
            @Field("mac") String mac,
            @Field("code") String code,
            @Field("used_date") long used_date,
            @Field("end_date") long end_date,
            @Field("usr_id") int usr_id,
            @Field("course_id") int course_id
    );


    @GET("courses/{lanugage}")
    Call<Courses> getAllCourses(@Path("lanugage") int lanugage);


     */

}
