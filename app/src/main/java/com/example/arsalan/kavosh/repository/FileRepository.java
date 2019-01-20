package com.example.arsalan.kavosh.repository;

import android.content.Context;
import android.util.Log;

import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.model.Token;
import com.example.arsalan.kavosh.retrofit.ApiInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.core.content.ContextCompat;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


@Singleton  // informs Dagger that this class should be constructed once
public class FileRepository {

    private final Executor executor;
    private final String TAG = "FileRepository";
    @Inject
    Retrofit mRetrofit;

    @Inject
    Token mToken;

    @Inject
    public FileRepository(Executor executor) {
        this.executor = executor;

    }


    public FileDownloaded getLocalFile(Context context, String fileUrl, String fileName) {
        FileDownloaded mFile = new FileDownloaded();
        File file = new File(context.getFilesDir(),
                fileName);
        if (file.exists()) {
            mFile.setLocalPath(file.getPath());
            mFile.setFinished(true);
        } else {
            downloadFileFromUrl(context, fileUrl, fileName, mFile);
        }
        // return a LiveData directly from the database.
        return mFile;
    }

    private void downloadFileFromUrl(Context context, String fileUrl, String fileName, FileDownloaded mFile) {
        Log.d("refreshAudioMemos", "!audioMemoExist , token:" + mToken.getAccessToken());

        executor.execute(() -> {
            Call<ResponseBody> call = mRetrofit.create(ApiInterface.class).downloadFileWithDynamicUrlSync(fileUrl);

            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {

                    Log.d(TAG, "server contacted and has file");

                    writeResponseBodyToDisk(context, response.body(), fileName, mFile);

                } else {
                    Log.d(TAG, "run: response.error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeResponseBodyToDisk(Context context, ResponseBody body, String fileName, FileDownloaded fileDownloaded) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(ContextCompat.getDataDir(context).getPath() + File.separator + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    fileDownloaded.setProgress((int) (100 * fileSizeDownloaded / fileSize));

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                fileDownloaded.setLocalPath(futureStudioIconFile.getPath());
                fileDownloaded.setFinished(true);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
        }
    }
}
