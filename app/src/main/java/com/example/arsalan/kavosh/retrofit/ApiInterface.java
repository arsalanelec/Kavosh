package com.example.arsalan.kavosh.retrofit;

/**
 * Created by Arsalan on 03-10-2017.
 */

import com.example.arsalan.kavosh.model.AudioMemo;
import com.example.arsalan.kavosh.model.Contexture;
import com.example.arsalan.kavosh.model.ExcavationItem;
import com.example.arsalan.kavosh.model.ExcavationItemSupervisorHistory;
import com.example.arsalan.kavosh.model.Feature;
import com.example.arsalan.kavosh.model.Found;
import com.example.arsalan.kavosh.model.Layer;
import com.example.arsalan.kavosh.model.LayerFeature;
import com.example.arsalan.kavosh.model.Photo;
import com.example.arsalan.kavosh.model.Project;
import com.example.arsalan.kavosh.model.RetroResponse;
import com.example.arsalan.kavosh.model.Supervisor;
import com.example.arsalan.kavosh.model.SurFound;
import com.example.arsalan.kavosh.model.Survey;
import com.example.arsalan.kavosh.model.SurveyProject;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;


public interface ApiInterface {


    @FormUrlEncoded
    @POST("oauth/token")
    Call<Token> getToken(@Field("grant_type") String grantType, @Field("client_id") int clientId, @Field("client_secret") String clientSecret, @Field("scope") String scope, @Field("username") String username, @Field("password") String password);

    @GET("api/user_detail")
    Call<User> getCurrentUserDetail(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("api/user_find")
    Call<User> findUserByNatId(@Header("Authorization") String token, @Field("national_id") String nationalId);


    @FormUrlEncoded
    @POST("api/register")
    Call<Token> registerUser(@Field("name") String name, @Field("password") String password, @Field("email") String email, @Field("mobile") String mobile, @Field("national_id") String nationId, @Field("degree_edu") int eduDegree);


    @GET("api/projects")
    Call<List<Project>> getProjectList(@Header("Authorization") String token);

    @GET("api/projects/{projectId}/users")
    Call<List<Supervisor>> getProjectSupervisors(@Header("Authorization") String token, @Path("projectId") String parentId);

    @Multipart
    @POST("api/projects/add_user")
    Call<RetroResponse> addProjectSupervisor(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);


    @Multipart
    @POST("api/excavations")
    Call<RetroResponse> addExcavation(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @GET("api/excavations/{excavationProjectId}/items")
    Call<List<ExcavationItem>> getExcavationItemList(@Header("Authorization") String token, @Path("excavationProjectId") String projectId);

    //Survey
    @Multipart
    @POST("api/surveys/items/store")
    Call<RetroResponse> updateOrCreateSurvey(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @GET("api/surveys/items/{survey}")
    Call<Survey> getSurvey(@Header("Authorization") String token, @Path("survey") String surveyId);

    @GET("api/surveys/{surveyProject}/items")
    Call<List<Survey>> getSurveyProjectItems(@Header("Authorization") String token, @Path("surveyProject") String surveyId);

    //SurveyProject
    @GET("api/surveys/{surveyProject}")
    Call<SurveyProject> getSurveyProject(@Header("Authorization") String token, @Path("surveyProject") String surveyId);


    @Multipart
    @POST("api/surveys")
    Call<RetroResponse> addSurveyProject(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    //
    @Multipart
    @POST("api/excavation_items")
    Call<RetroResponse> saveExcavationItem(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);


    @DELETE("api/excavation_items/{excavationItemId}")
    Call<RetroResponse> deleteExcavationItem(@Header("Authorization") String token, @Path("excavationItemId") String ItemId);

    @GET("api/excavation_items/{excavationItemId}/supervisors")
    Call<List<ExcavationItemSupervisorHistory>> getExcavationItemSupervisorList(@Header("Authorization") String token, @Path("excavationItemId") String ItemId);

    @GET("api/excavation_items/{excavationItemId}/layers_features/")
    Call<List<LayerFeature>> getLayerFeatureList(@Header("Authorization") String token, @Path("excavationItemId") String itemId);

    @Multipart
    @POST("api/layer_feature")
    Call<RetroResponse> createLayerFeature(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/layers")
    Call<RetroResponse> createLayer(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @DELETE("api/layers/{layer}")
    Call<RetroResponse> deleteLayer(@Header("Authorization") String token, @Path("layer") String layerId);

    @GET("api/layers/{layer}")
    Call<Layer> getLayer(@Header("Authorization") String token, @Path("layer") String layerId);

    //Feature
    @Multipart
    @POST("api/features")
    Call<RetroResponse> createFeature(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @DELETE("api/features/{feature}")
    Call<RetroResponse> deleteFeature(@Header("Authorization") String token, @Path("feature") String featureId);

    @GET("api/features/{feature}")
    Call<Feature> getFeature(@Header("Authorization") String token, @Path("feature") String featureId);

    //بافت
    @GET("api/layers/{layer}/contexture/")
    Call<Contexture> getContexture(@Header("Authorization") String token, @Path("layer") String LayerId);

    @Multipart
    @POST("api/contexture")
    Call<RetroResponse> createOrUpdateContexture(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    //یافته
    @GET("api/layers/{layer}/founds/")
    Call<List<Found>> getFounds(@Header("Authorization") String token, @Path("layer") String LayerId);

    @Multipart
    @POST("api/found")
    Call<RetroResponse> createOrUpdateFound(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @DELETE("api/found/{found}")
    Call<RetroResponse> deleteFound(@Header("Authorization") String token, @Path("found") String ItemId);

    //SurFounds
    @GET("api/surveys/items/{survey}/founds/")
    Call<List<SurFound>> getSurFounds(@Header("Authorization") String token, @Path("survey") String surveyId);

    @Multipart
    @POST("api/surveys/items/found")
    Call<RetroResponse> createOrUpdateSurFound(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params);

    @DELETE("api/surveys/items/found/{found}")
    Call<RetroResponse> deleteSurFound(@Header("Authorization") String token, @Path("found") String ItemId);

    //////


    @GET("api/photos/{parentId}")
    Call<List<Photo>> getPhotoList(@Header("Authorization") String token, @Path("parentId") String parentId);

    @Multipart
    @POST("api/photo")
    Call<RetroResponse> uploadPhoto(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part image);

    @DELETE("api/photo/{photo}")
    Call<RetroResponse> deletePhoto(@Header("Authorization") String token, @Path("photo") String layerId);

    @GET("api/audios/{parentId}")
    Call<List<AudioMemo>> getAudioList(@Header("Authorization") String token, @Path("parentId") String parentId);

    @Multipart
    @POST("api/audio")
    Call<RetroResponse> uploadAudio(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part audio);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

/*
    @FormUrlEncoded
    @POST("/api/users/getprofile")
    Call<RetUserProfile> getProfile(@Header("Authorization") String token, @Field("username") long username);


    @Multipart
    @POST("/api/users/editprofile")
    Call<RetroResult> editProfile(@Header("Authorization") String token, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part image, @Part MultipartBody.Part thumb);
*/


}