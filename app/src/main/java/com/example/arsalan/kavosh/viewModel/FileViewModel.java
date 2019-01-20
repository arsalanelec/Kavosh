package com.example.arsalan.kavosh.viewModel;

import android.content.Context;

import com.example.arsalan.kavosh.model.FileDownloaded;
import com.example.arsalan.kavosh.repository.FileRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class FileViewModel extends ViewModel {
    private FileRepository fileRepository;
    private FileDownloaded fileDownloaded;

    @Inject //  parameter is provided by Dagger 2
    public FileViewModel(FileRepository fileRepository) {
        this.fileRepository = fileRepository;

    }

    public void initial(Context context, String fileUrl, String fileName) {
        fileDownloaded = fileRepository.getLocalFile(context, fileUrl, fileName);
    }

    public FileDownloaded getFileDownloaded() {
        return fileDownloaded;
    }
}
