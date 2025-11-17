package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
}
