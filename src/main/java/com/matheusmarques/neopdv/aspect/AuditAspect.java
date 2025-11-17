package com.matheusmarques.neopdv.aspect;

import com.matheusmarques.neopdv.annotation.AuditableField;
import com.matheusmarques.neopdv.domain.AuditLog;
import com.matheusmarques.neopdv.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditRepo;
    private final MongoOperations mongoOperations;

    @Pointcut("execution(* com.matheusmarques.neopdv.repository.ProductRepository.save(..))")
    public void mongoSave() {}

    @Around("mongoSave()")
    public Object auditMongo(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("ASPECT CHAMOU: " + pjp.getSignature());

        Object newEntity = pjp.getArgs()[0];

        if (!newEntity.getClass().isAnnotationPresent(Document.class)) {
            return pjp.proceed();
        }

        Object id = getEntityId(newEntity);

        Object oldEntity = null;

        if (id != null) {
            oldEntity = mongoOperations.findById(id, newEntity.getClass());
        }

        Object savedEntity = pjp.proceed();

        if (oldEntity != null) {
            compareAndAudit(oldEntity, savedEntity);
        }

        return savedEntity;
    }


    private Object getEntityId(Object entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(MongoId.class)) {
                f.setAccessible(true);
                try {
                    return f.get(entity);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }


    private void compareAndAudit(Object oldEntity, Object newEntity) throws IllegalAccessException {

        Field[] fields = newEntity.getClass().getDeclaredFields();

        for (Field field : fields) {

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
        log.setEntityId(
                getEntityId(entity) != null ? getEntityId(entity).toString() : null
        );
        log.setFieldName(fieldName);
        log.setOldValue(oldValue != null ? oldValue.toString() : null);
        log.setNewValue(newValue != null ? newValue.toString() : null);
        log.setChangedAt(LocalDateTime.now());

        auditRepo.save(log);
    }
}