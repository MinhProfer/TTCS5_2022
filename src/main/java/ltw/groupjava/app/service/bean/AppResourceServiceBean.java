package ltw.groupjava.app.service.bean;

import lombok.RequiredArgsConstructor;
import ltw.groupjava.app.entity.AppResource;
import ltw.groupjava.app.entity.User;
import ltw.groupjava.app.repository.AppResourceRepo;
import ltw.groupjava.app.service.AppResourceService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppResourceServiceBean implements AppResourceService {
    private final AppResourceRepo appResourceRepo;

    @Override
    public AppResource save(AppResource AppResource) {
        return appResourceRepo.save(AppResource);
    }

    @Override
    public AppResource init(User uploader, MultipartFile file, String dest, LocalDateTime createdTime) {
        AppResource out = new AppResource();
        out.setName(file.getOriginalFilename());
        out.setType(file.getContentType());
        out.setOwner(uploader);
        out.setPath(dest);
        out.setCreatedTime(createdTime);

        return out;
    }

    @Override
    public AppResource findById(UUID id) {
        Optional<AppResource> res = appResourceRepo.findById(id);
        return res.orElse(null);
    }
}
