package ltw.groupjava.app.controller;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.AppResource;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.service.AppResourceService;
import ltw.groupjava.app.service.StorageService;
import ltw.groupjava.app.service.UserService;
import ltw.groupjava.app.utils.UuidUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ResourceController {
    
    private final StorageService storageService;
    private final AppResourceService resourceService;
    
    private final UserService userService;

    @GetMapping("/upload")
    public String getUploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String handleUpload(@RequestParam("file") MultipartFile file) {
        boolean error;
        if (!file.isEmpty()) {
            User uploader = getLoggedUser();
            LocalDateTime now = LocalDateTime.now();
            Path path = storageService.getPath(now);
            // save info to db
            AppResource appResource = resourceService.init(uploader, file, path.toString(), now);
            appResource = resourceService.save(appResource);
            error = !storageService.saveFile(appResource.getId().toString().toLowerCase(), file, path);
        } else {
            error = true;
        }
        return error ? "error" : "oke";
    }

    @PostMapping("/upload/multiple")
    @ResponseBody
    public String handleUpload(@RequestParam("files") MultipartFile[] files) {
        // TODO: method need to be written
        return "oke";
    }

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return userService.findByUsername(loggedUser);
    }

    @GetMapping("/storage")
    @ResponseBody
    public ResponseEntity<Resource> getResource(@RequestParam("rid") String rid) {
        UUID id = UuidUtils.parse(rid);
        if (id != null ) {
            AppResource res = resourceService.findById(id);
            if (res != null) {
                Resource file = storageService.getFile(res);
                return ResponseEntity.ok().header(
                                HttpHeaders.CONTENT_TYPE, res.getType())
                        .body(file);
            }
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @GetMapping("/storage/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadResource(@RequestParam("rid") String rid) {
        AppResource res = resourceService.findById(UUID.fromString(rid));
        if (res != null) {
            Resource file = storageService.getFile(res);
            return ResponseEntity.ok().header(
                            HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + res.getName() + "\"")
                    .body(file);
        }
        return ResponseEntity.internalServerError().body(null);
    }
}
