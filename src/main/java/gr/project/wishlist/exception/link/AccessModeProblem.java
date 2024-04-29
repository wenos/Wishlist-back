package gr.project.wishlist.exception.link;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.UUID;

public class AccessModeProblem extends AbstractThrowableProblem {
    public AccessModeProblem() {
        super(
                null,
                "Доступ для редактирования запрещен",
                Status.FORBIDDEN,
                null
        );
    }
}
