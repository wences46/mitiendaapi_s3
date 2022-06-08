package pe.todotic.mitiendaapi_s3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.todotic.mitiendaapi_s3.service.FileSystemStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private FileSystemStorageService fileSystemStorageService;


    @PostMapping("/upload")
    Map<String, String> upload(@RequestParam("file") MultipartFile multipartFile) {
        String filename = fileSystemStorageService.store(multipartFile);

        Map<String, String> result = new HashMap<>();
        result.put("filename", filename);

        return result;
    }

    @GetMapping("/{filename}")
    ResponseEntity<Resource> loadFile(@PathVariable String filename) throws IOException {
        Resource resource = fileSystemStorageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .body(resource);
    }

}
