package ltw.groupjava.app.repository;

import ltw.groupjava.app.entity.AppResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppResourceRepo extends JpaRepository<AppResource, UUID> {
}
