package com.xotril.vendacar.domain.repository

import com.xotril.vendacar.domain.model.AuditLog

interface AuditLogRepository {
    fun save(auditLog: AuditLog): AuditLog
}
