package gr.project.wishlist.exception.gift;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class GiftNotFoundProblem extends AbstractThrowableProblem {

    public GiftNotFoundProblem(Long value) {
        super(
                null,
                "Подарок не найден",
                Status.NOT_FOUND,
                String.format("Подарок с id %s не найден", value));
    }
}
