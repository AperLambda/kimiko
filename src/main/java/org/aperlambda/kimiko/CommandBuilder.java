/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of kimiko.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.kimiko;

import org.aperlambda.lambdacommon.resources.ResourceName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents an instance builder of a {@link Command}
 *
 * @param <S> The typename of the sender.
 * @version 1.1.0
 * @since 1.0.0
 */
public class CommandBuilder<S>
{
    private final @NotNull ResourceName           name;
    private                String                 usage               = "";
    private                Function<S, String>    usage_getter;
    private                String                 description         = "";
    private                Function<S, String>    descr_getter;
    private @NotNull       List<String>           aliases             = new ArrayList<>();
    private @Nullable      String                 required_permission = null;
    private                CommandExecutor<S>     executor;
    @SuppressWarnings("unchecked")
    private                CommandTabCompleter<S> tab_completer       = (CommandTabCompleter<S>) Command.DEFAULT_TAB_COMPLETER;

    public CommandBuilder(@NotNull ResourceName name)
    {
        this.name = name;
    }

    /**
     * Sets the usage of the command.
     *
     * @param usage The usage of the command.
     * @return The current builder.
     * @see Command#set_usage(String)
     */
    public CommandBuilder<S> usage(@NotNull String usage)
    {
        Objects.requireNonNull(usage, "Usage cannot be null.");
        this.usage = usage;
        return this;
    }

    /**
     * Sets the usage getter of the command.
     *
     * @param usage The usage getter.
     * @return The current builder.
     * @see Command#set_usage(Function)
     */
    public CommandBuilder<S> usage(Function<S, String> usage)
    {
        usage_getter = usage;
        return this;
    }

    /**
     * Sets the description of the command.
     *
     * @param description The description of the command.
     * @return The current builder.
     * @see Command#set_description(String)
     */
    public CommandBuilder<S> description(String description)
    {
        Objects.requireNonNull(description, "Description cannot be null.");
        this.description = description;
        return this;
    }

    /**
     * Sets the description getter of the command.
     *
     * @param description The description getter.
     * @return The current builder.
     * @see Command#set_description(Function)
     */
    public CommandBuilder<S> description(Function<S, String> description)
    {
        this.descr_getter = description;
        return this;
    }

    /**
     * Sets the aliases of the command.
     *
     * @param aliases The aliases of the command.
     * @return The current builder.
     * @see Command#set_aliases(List)
     */
    public CommandBuilder<S> aliases(@NotNull List<String> aliases)
    {
        this.aliases = aliases;
        return this;
    }

    /**
     * Sets the aliases of the command.
     *
     * @param aliases The aliases of the command.
     * @return The current builder.
     * @see Command#set_aliases(String...)
     */
    public CommandBuilder<S> aliases(@NotNull String... aliases)
    {
        return aliases(Arrays.asList(aliases));
    }

    /**
     * Sets the required permission to execute the command.
     *
     * @param required_permission The required permission.
     * @return The current builder.
     * @see Command#set_required_permission(String)
     */
    public CommandBuilder<S> permission(@Nullable String required_permission)
    {
        this.required_permission = required_permission;
        return this;
    }

    /**
     * Sets the executor of the command.
     *
     * @param executor The executor of the command.
     * @return The current builder.
     * @see Command#set_executor(CommandExecutor)
     */
    public CommandBuilder<S> executor(@NotNull CommandExecutor<S> executor)
    {
        Objects.requireNonNull(executor, "Executor cannot be null.");
        this.executor = executor;
        return this;
    }

    /**
     * Sets the tab completer of the command.
     *
     * @param tab_completer The tab completer of the command.
     * @return The current builder.
     * @see Command#set_tab_completer(CommandTabCompleter)
     */
    public CommandBuilder<S> tab_completer(@NotNull CommandTabCompleter<S> tab_completer)
    {
        Objects.requireNonNull(tab_completer, "Tab completer cannot be null.");
        this.tab_completer = tab_completer;
        return this;
    }

    /**
     * Creates a new instance of {@link Command}.
     *
     * @return The new {@link Command}.
     */
    public Command<S> build()
    {
        var command = new Command<S>(name);
        command.set_description(description);
        if (descr_getter != null)
            command.set_description(descr_getter);
        command.set_usage(usage);
        if (usage_getter != null)
            command.set_usage(usage_getter);
        command.set_required_permission(required_permission);
        command.set_aliases(aliases);
        command.set_executor(executor);
        command.set_tab_completer(tab_completer);
        return command;
    }
}