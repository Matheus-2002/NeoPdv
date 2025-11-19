package com.matheusmarques.neopdv.service.audit;

import com.matheusmarques.neopdv.annotation.audit.AuditableField;
import com.matheusmarques.neopdv.domain.audit.AuditLog;
import com.matheusmarques.neopdv.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuditAsyncService {

    private final AuditLogRepository auditRepo;

    @Async
    public void auditEntityAsync(Object oldEntity, Object newEntity) {
        try {
            if (oldEntity != null) {
                compareAndAudit(oldEntity, newEntity);
            }
        } catch (Exception e) {
            System.err.println("Erro na auditoria async: " + e.getMessage());
        }
    }

    public Object extractId(Object entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(MongoId.class)) {
                try {
                    f.setAccessible(true);
                    return f.get(entity);
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private void compareAndAudit(Object oldEntity, Object newEntity) throws IllegalAccessException {

        for (Field field : newEntity.getClass().getDeclaredFields()) {

            if (!field.isAnnotationPresent(AuditableField.class)) continue;

            field.setAccessible(true);

            Object oldValue = field.get(oldEntity);
            Object newValue = field.get(newEntity);

            if (!Objects.equals(oldValue, newValue)) {
                saveAuditEntry(newEntity, field.getName(), oldValue, newValue);
            }
        }
    }

    private void saveAuditEntry(Object entity, String fieldName, Object oldValue, Object newValue) {

        AuditLog log = new AuditLog();
        log.setEntityName(entity.getClass().getSimpleName());
        log.setEntityId(extractId(entity) != null ? extractId(entity).toString() : null);
        log.setFieldName(fieldName);
        log.setOldValue(oldValue != null ? oldValue.toString() : null);
        log.setNewValue(newValue != null ? newValue.toString() : null);
        log.setChangedAt(LocalDateTime.now());

        auditRepo.save(log);
    }
}
