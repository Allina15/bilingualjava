package bilingual.api;

import bilingual.service.impl.AwsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/awsFile")
@Tag(name = "API for aws files")
@CrossOrigin
public class AwsApi {

    private final AwsServiceImpl awsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload files to database")
    Map<String, String> uploadFile(@RequestParam MultipartFile multipartFile) throws IOException {
        return awsService.uploadFile(multipartFile);
    }

    @DeleteMapping
    @Operation(summary = "Delete files from database")
    Map<String, String> deleteFileFrom(@RequestParam String fileName) {
        return awsService.deleteFile(fileName);
    }

    @GetMapping
    @Operation(summary = "Download file", description = "Download file from S3")
    public <S3ObjectInputStream> ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(name = "fileLink") String fileLink) {
        return awsService.downloadFile(fileLink);
    }
}