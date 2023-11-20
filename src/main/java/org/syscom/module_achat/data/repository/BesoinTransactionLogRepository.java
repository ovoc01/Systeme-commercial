package org.syscom.module_achat.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.syscom.module_achat.data.entity.BesoinTransactionLog;

public interface BesoinTransactionLogRepository extends JpaRepository<BesoinTransactionLog, Integer> {
        
} 