package fr.rirrsmo.getaway.checker.aspect;

import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import fr.rirrsmo.getaway.checker.annotation.OpenFGACheck;
import fr.rirrsmo.getaway.checker.exception.AccessDenied;
import fr.rirrsmo.getaway.checker.exception.InvalidCheckAnnotation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class OpenFGACheckerAspect {

    private final OpenFgaClient openFgaClient;

    @Around(value = "execution(* *.*(..)) && @annotation(fr.rirrsmo.getaway.checker.annotation.OpenFGACheck)",
        argNames = "pjp")
    public Object checkOpenFGA(ProceedingJoinPoint pjp) throws Throwable {
        if (pjp.getSignature() instanceof MethodSignature methodSignature) {
            OpenFGACheck openFGACheck = methodSignature.getMethod().getAnnotation(OpenFGACheck.class);

            String user = interpolate(openFGACheck.user(), methodSignature.getParameterNames(), pjp.getArgs());
            String relation = interpolate(openFGACheck.relation(), methodSignature.getParameterNames(), pjp.getArgs());
            String _object = interpolate(openFGACheck._object(), methodSignature.getParameterNames(), pjp.getArgs());

            if (openFGAcheck(user, relation, _object)) {
                return pjp.proceed();
            } else {
                throw new AccessDenied(user, relation, _object);
            }
        }


        throw new InvalidCheckAnnotation("");
    }

    protected String interpolate(String baseString, String[] parameterNames, Object[] args) {
        Optional<InterpolatedArgs> interpolatedArgs;
        StringBuilder result = new StringBuilder(baseString);

        while ((interpolatedArgs = InterpolatedArgs.from(baseString)).isPresent()) {
            boolean found = false;

            for (int i = 0; i < parameterNames.length; i++) {
                String paramName = parameterNames[i];
                if (interpolatedArgs.get().argName().equals(paramName)) {
                    String paramValueAsString = args[i].toString();
                    result.replace(interpolatedArgs.get().startIndex(), interpolatedArgs.get().endIndex(), paramValueAsString);
                    found = true;
                    break;
                }
            }

            baseString = result.toString();

            if (!found) {
                throw new InvalidCheckAnnotation(String.format("Unmatched interpolated argument \"%s\". Please note the matching must be exact and is case-sensitive.", interpolatedArgs.get().argName()));
            }
        }

        return result.toString();
    }

    protected boolean openFGAcheck(String user, String relation, String _object) {
        ClientCheckRequest request = new ClientCheckRequest()
                .user(user)
                .relation(relation)
                ._object(_object);

        try {
            return Boolean.TRUE.equals(openFgaClient.check(request).get().getAllowed());
        } catch (FgaInvalidParameterException e) {
            throw new InvalidCheckAnnotation("Execution of OpenFGA API query failed", e);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e); // TODO
        }
    }
}
