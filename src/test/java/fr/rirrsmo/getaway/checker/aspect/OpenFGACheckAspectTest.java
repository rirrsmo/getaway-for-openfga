package fr.rirrsmo.getaway.checker.aspect;

import dev.openfga.sdk.api.client.OpenFgaClient;
import fr.rirrsmo.getaway.checker.exception.InvalidCheckAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpenFGACheckAspectTest {

    @InjectMocks
    private OpenFGACheckerAspect aspect;

    @Mock
    private OpenFgaClient openFgaClient;

    @Test
    public void shouldInterpolate() {
        String input = "user:{userId}";

        String actual = aspect.interpolate(input, new String[]{"userId"}, new Object[]{1234L});

        Assertions.assertEquals("user:1234", actual);
    }

    @Test
    public void shouldInterpolateMultipleArguments() {
        String input = "{userType}{userSubtype}:{userId}";

        String actual = aspect.interpolate(
                input,
                new String[]{"userId", "userSubtype", "userType"},
                new Object[]{1234L, "Manager", "user"}
        );

        Assertions.assertEquals("userManager:1234", actual);
    }

    @Test
    public void shouldThrowOnMissingParameter() {
        String input = "{userType}{userSubtype}:{userId}";

        Assertions.assertThrows(
                InvalidCheckAnnotation.class,
                () -> aspect.interpolate(
                        input,
                        new String[]{"userId", "userType"},
                        new Object[]{1234L, "user"}
                ));

    }
}
