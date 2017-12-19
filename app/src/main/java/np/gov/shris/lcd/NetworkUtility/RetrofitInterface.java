package np.gov.shris.lcd.NetworkUtility;


import np.gov.shris.lcd.Models.NewsStatics;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {

//    @GET("api/v1/posts/{type}")
//    Call<PostStatistics> getNewPost(
//            @Query("type") String type,
//            @Query("latitude") Double latitude,
//            @Query("longitude") Double longitude,
//            @Query("offset") Integer offset
//    );
//
    @GET("lcd/api")
    Call<NewsStatics> getNews();
//
//    @POST("api/v1/forgotpassword")
//    Call<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest userEmail);
//
//    @POST("api/v1/logout")
//    Call<ResponseBody> logout(@Header("Authorization") String authToken);
//
//    @Multipart
//    @GET("api/v1/getFile")
//    Call<ResponseBody> getFile(@Header("Authorization") String authToken,
//                               @Part("filename") RequestBody filename);
//
//    //MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//    @Multipart
//    @POST("/api/v1/register")
//    Call<SignUpResponse> register(@Part("profile_image\"; filename=\"pp.jpg\" ") RequestBody file,
//                                  @Part("name") RequestBody name,
//                                  @Part("username") RequestBody username,
//                                  @Part("email") RequestBody email,
//                                  @Part("password") RequestBody password,
//                                  @Part("phone") RequestBody phone);
//
//    @Multipart
//    @POST("api/v1/post/create")
//    Call<NewPostResponse> newPost(
//            @Header("Authorization") String authToken,
//            @Part("address") RequestBody address,
//            @Part("description") RequestBody description,
//            @Part("latitude") RequestBody latitude,
//            @Part("longitude") RequestBody longitude,
//            @Part("no_of_rooms") RequestBody noOfRoom,
//            @Part("post_type") RequestBody postType,
//            @Part("price") RequestBody price,
//            @Part("title") RequestBody title,
//            @PartMap Map<String, RequestBody> Files
//    );



}

