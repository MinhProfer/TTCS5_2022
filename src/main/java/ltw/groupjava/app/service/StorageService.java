package ltw.groupjava.app.service;

import ltw.groupjava.app.entity.AppResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public interface StorageService {
    boolean saveFile(String fileName, MultipartFile file, Path dest);
    void saveFiles(List<MultipartFile> files, Path dest);
    Path getPath(LocalDateTime now);

    Resource getFile(AppResource res);
}