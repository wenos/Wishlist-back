package gr.project.wishlist.exception.link;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.UUID;

public class LinkNotFoundProblem extends AbstractThrowableProblem {

    public LinkNotFoundProblem(UUID value) {
        super(
                null,
                "Ссылка не найдена",
                Status.NOT_FOUND,
                String.format("Ссылка %s не найдена", value)
        );
    }
}
