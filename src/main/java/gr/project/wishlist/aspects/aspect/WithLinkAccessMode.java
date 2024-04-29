package gr.project.wishlist.aspects.aspect;


import gr.project.wishlist.domain.model.AccessMode;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.exception.link.AccessModeProblem;
import gr.project.wishlist.service.LinkService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@AllArgsConstructor
public class WithLinkAccessMode {
    private final LinkService linkService;

    @Before("@annotation(gr.project.wishlist.aspects.annotations.WithLinkAccessMode)")
    public void checkUser(JoinPoint joinPoint) {
        Object[] methodArgs = joinPoint.getArgs();

        UUID uuid = null;
        for (Object arg : methodArgs) {
            if (arg instanceof UUID) {
                uuid = (UUID) arg;
                break;
            }
        }

        if (uuid == null) {
            return;
        }

        SharedAccess sharedAccess = linkService.getById(uuid);
        if (sharedAccess.getAccessMode() == AccessMode.VIEW_MODE) {
            throw new AccessModeProblem();
        }
        System.out.println("PUPUPUPU: Доступно редактирование");
    }
}
