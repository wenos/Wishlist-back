package gr.project.wishlist.exception.gift;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class GiftBookedProblem extends AbstractThrowableProblem {

    public GiftBookedProblem(Long value) {
        super(
                null,
                "Подарок нельзя забронировать",
                Status.CONFLICT,
                String.format("Подарок с id %s уже забронирован", value));
    }
}
