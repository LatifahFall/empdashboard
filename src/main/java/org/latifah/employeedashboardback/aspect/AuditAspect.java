package org.latifah.employeedashboardback.aspect;

import org.latifah.employeedashboardback.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @AfterReturning(pointcut = "execution(* org.latifah.employeedashboardback.service..*.*(..)) && !within(org.latifah.employeedashboardback.service.AuditService)", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().getName();
        String type = inferEntityType(joinPoint);
        String id = extractEntityId(joinPoint, result);

        Map<String, Object> details = new HashMap<>();
        details.put("method", method);
        auditService.logAction(method, type, id, details, true);
    }

    @AfterThrowing(pointcut = "execution(* org.latifah.employeedashboardback.service..*.*(..)) && !within(org.latifah.employeedashboardback.service.AuditService)", throwing = "ex")
    public void logFailure(JoinPoint joinPoint, Exception ex) {
        String method = joinPoint.getSignature().getName();
        String type = inferEntityType(joinPoint);
        String id = extractEntityId(joinPoint, null);

        Map<String, Object> details = new HashMap<>();
        details.put("method", method);
        details.put("error", ex.getMessage());

        auditService.logAction(method, type, id, details, false);
    }

    private String inferEntityType(JoinPoint jp) {
        return jp.getTarget().getClass().getSimpleName().replace("Service", "").toUpperCase();
    }

    private String extractEntityId(JoinPoint jp, Object result) {
        Object[] args = jp.getArgs();
        return args.length > 0 ? String.valueOf(args[0]) : "N/A";
    }
}
