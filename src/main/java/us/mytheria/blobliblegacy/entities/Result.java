package us.mytheria.blobliblegacy.entities;

import java.util.Optional;

public class Result<T> {
    private final T value;
    private final boolean isValid;
    /**
     * A class that represents a result of a process.
     * We recommend using static method 'ofNullable' instead of the default constructor.
     *
     * @param value   the value
     * @param isValid if the result is valid
     */
    public Result(T value, boolean isValid) {
        this.value = value;
        this.isValid = isValid;
    }

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return a valid result
     */
    public static <T> Result<T> valid(T value) {
        return new Result<>(value, true);
    }

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return an invalid result
     */
    public static <T> Result<T> invalid(T value) {
        return new Result<>(value, false);
    }

    /**
     * @param value the value
     * @param <T>   the type of the value
     * @return If value is null will return an invalid result. Otherwise, a valid result.
     */
    public static <T> Result<T> ofNullable(T value) {
        return new Result<>(value, value != null);
    }

    /**
     * Should be the default constructor for a Result object.
     *
     * @param <T> the type of the value
     * @return an invalid result, since the value is null it's not required to pass it.
     */
    public static <T> Result<T> invalidBecauseNull() {
        return new Result<>(null, false);
    }

    /**
     * @return If not valid, will return an empty Optional. Otherwise, will return an Optional with the value.
     */
    public Optional<T> toOptional() {
        if (!isValid) return Optional.empty();
        return Optional.of(value);
    }

    public T value() {
        return value;
    }

    public boolean isValid() {
        return isValid;
    }
}
