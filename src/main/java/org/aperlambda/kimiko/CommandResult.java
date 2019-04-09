/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of kimiko.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.kimiko;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Represents the result of a command.
 */
public class CommandResult
{
    public static final CommandResult SUCCESS          = new CommandResult(() -> "");
    public static final CommandResult ERROR_PERMISSION = new CommandResult(() -> "translate:error.permission");
    public static final CommandResult ERROR_USAGE      = new CommandResult(() -> "translate:error.usage");
    public static final CommandResult ERROR_RUNTIME    = new CommandResult(() -> "translate:error.runtime");

    private final @NotNull Callable<String> callable;

    public CommandResult(@NotNull Callable<String> callable)
    {
        Objects.requireNonNull(callable, "Result's handler cannot be null.");
        this.callable = callable;
    }

    /**
     * Calls the result of the command.
     * <p>Note for the implementation: sends the result to the sender but handles the {@code translate:} result differently.</p>
     *
     * @return The result of the command.
     */
    public String call()
    {
        try {
            return callable.call();
        } catch (Exception e) {
            return "";
        }
    }
}