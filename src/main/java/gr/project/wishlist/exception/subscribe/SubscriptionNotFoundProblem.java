package gr.project.wishlist.exception.subscribe;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class SubscriptionNotFoundProblem extends AbstractThrowableProblem {

    public SubscriptionNotFoundProblem() {
        super(
                null,
                "Подписка не найдена",
                Status.NOT_FOUND,
                "Вы не подписаны на данный вишлист"
        );
    }
}
