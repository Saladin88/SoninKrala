package co.simplon.soninkrala.dtos;


import org.springframework.web.multipart.MultipartFile;

public record AudioRecordDto(
        MultipartFile audioFile,
        String audioFileName
) {
}
