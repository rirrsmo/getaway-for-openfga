package fr.rirrsmo.getaway.checker.aspect;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class InterpolatedArgsTest {

    @Test
    public void shouldFindMatch() {
        String input = "user:{userId}";

        Optional<InterpolatedArgs> result = InterpolatedArgs.from(input);

        Assertions.assertTrue(result.isPresent());
        InterpolatedArgs userInterpolatedArg = result.get();
        Assertions.assertEquals("userId", userInterpolatedArg.argName());
        Assertions.assertEquals(5, userInterpolatedArg.startIndex());
        Assertions.assertEquals(13, userInterpolatedArg.endIndex());
    }

    @Test
    public void shouldBeEmptyOnNoInterpolation() {
        String input = "user:1234";

        Optional<InterpolatedArgs> result = InterpolatedArgs.from(input);

        Assertions.assertTrue(result.isEmpty());
    }
}
