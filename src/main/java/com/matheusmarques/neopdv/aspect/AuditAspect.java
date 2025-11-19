package com.matheusmarques.neopdv.aspect;
import com.matheusmarques.neopdv.service.audit.AuditAsyncService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditAsyncService auditAsyncService;
    private final MongoOperations mongoOperations;

    @Pointcut("execution(* com.matheusmarques.neopdv.repository.ProductRepository.save(..))")
    public void mongoSave() {}

    @Around("mongoSave()")
    public Object auditMongo(ProceedingJoinPoint pjp) throws Throwable {

        Object newEntity = pjp.getArgs()[0];

        if (!newEntity.getClass().isAnnotationPresent(Document.class)) {
            return pjp.proceed();
        }

        Object entityId = auditAsyncService.extractId(newEntity);

        Object oldEntity = null;
        if (entityId != null) {
            oldEntity = mongoOperations.findById(entityId, newEntity.getClass());
        }

        Object savedEntity = pjp.proceed();

        auditAsyncService.auditEntityAsync(oldEntity, savedEntity);

        return savedEntity;
    }
}