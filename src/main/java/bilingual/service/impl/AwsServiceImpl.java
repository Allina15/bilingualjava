package bilingual.service.impl;

import bilingual.service.AwsService;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final S3Client s3;

    @Value("${aws.bucket.path}")
    private String BUCKET_PATH;
    @Value("${aws.bucket.name}")
    private String BUCKET_NAME;

    @Override
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .contentType(String.valueOf(getMetaData(file)))
                .key(key)
                .build();
        s3.putObject(put, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("File successfully uploaded");
        return Map.of(
                "link", BUCKET_PATH + key);
    }

    private ObjectMetadata getMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    @Override
    public <S3ObjectInputStream> ResponseEntity<ByteArrayResource> downloadFile(String fileLink) {
        try {
            log.info("Downloading file...");
            String key = fileLink.substring(BUCKET_PATH.length());
            ResponseInputStream<GetObjectResponse> s3Object = s3.getObject(GetObjectRequest.builder().bucket(BUCKET_NAME).key(key).build());

            byte[] content = IOUtils.toByteArray(s3Object);

            ByteArrayResource resource = new ByteArrayResource(content);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + key);
            headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));

            log.info("Download successfully completed!");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (S3Exception | IOException e) {
            log.error("Error downloading file: {}", e.getMessage());
            throw new IllegalStateException("Error downloading file");
        }
    }

    @Override
    public Map<String, String> deleteFile(String fileName){
        try {
            s3.deleteObject(DeleteObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(fileName)
                    .build());
            return Map.of(
                    "message", "File " + fileName + " is successfully deleted"
            );
        } catch (S3Exception e) {
            throw new IllegalStateException("Error deleting file: " + e.awsErrorDetails().errorMessage());
        }
    }
}