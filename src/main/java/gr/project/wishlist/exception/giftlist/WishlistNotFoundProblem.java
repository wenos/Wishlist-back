package gr.project.wishlist.exception.giftlist;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class WishlistNotFoundProblem extends AbstractThrowableProblem {
    public WishlistNotFoundProblem(Long value) {
        super(
                null,
                "Список не найден",
                Status.NOT_FOUND,
                String.format("Список подарков с id %s не найден", value));
    }
}