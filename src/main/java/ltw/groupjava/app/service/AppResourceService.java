package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.AppResource;
import ltw.groupjava.app.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AppResourceService {
    AppResource save(AppResource AppResource);

    AppResource init(User uploader, MultipartFile file, String dest, LocalDateTime createdTime);

    AppResource findById(UUID fromString);
}
