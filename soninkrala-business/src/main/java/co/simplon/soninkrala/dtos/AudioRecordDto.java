package co.simplon.soninkrala.dtos;


import co.simplon.soninkrala.dtos.validators.AudioFormat;
import co.simplon.soninkrala.dtos.validators.AudioSize;
import org.springframework.web.multipart.MultipartFile;

public record AudioRecordDto(
        MultipartFile audioFile,
        String audioFileName
) {
}
