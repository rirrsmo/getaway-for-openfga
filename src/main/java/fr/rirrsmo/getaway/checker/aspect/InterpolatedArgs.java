package fr.rirrsmo.getaway.checker.aspect;

import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record InterpolatedArgs(
        String argName,
        int startIndex,
        int endIndex
) {

    InterpolatedArgs {
        Objects.requireNonNull(argName);
    }

    @NonNull
    public static Optional<InterpolatedArgs> from(String element) {
        Matcher userMatcher = Pattern.compile("\\{(\\w*)}").matcher(element);

        if (userMatcher.find()) {
            return Optional.of(new InterpolatedArgs(
                    userMatcher.group(1),
                    userMatcher.start(1) - 1,
                    userMatcher.end(1) + 1));
        }

        return Optional.empty();
    }
}
