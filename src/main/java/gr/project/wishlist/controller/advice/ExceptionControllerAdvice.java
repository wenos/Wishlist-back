package gr.project.wishlist.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionControllerAdvice implements ProblemHandling{
}
