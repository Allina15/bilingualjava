package bilingual.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface AwsService {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;
    Map<String, String> deleteFile(String fileName);
    <S3ObjectInputStream> ResponseEntity<ByteArrayResource> downloadFile(String fileLink);
}
