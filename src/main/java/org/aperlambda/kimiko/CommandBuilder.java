/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
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
 * @version 1.0.0
 * @since 1.0.0
 */
public class CommandBuilder<S>
{
	private final @NotNull ResourceName              name;
	private                String                    usage              = "";
	private                Function<S, String>       usageGetter;
	private                String                    description        = "";
	private                Function<S, String>       descriptionGetter;
	private @NotNull       List<String>              aliases            = new ArrayList<>();
	private @Nullable      String                    permissionRequired = null;
	private                CommandExecutor<S>     executor;
	@SuppressWarnings("unchecked")
	private                CommandTabCompleter<S> tabCompleter       = (CommandTabCompleter<S>) Command.DEFAULT_TAB_COMPLETER;

	public CommandBuilder(@NotNull ResourceName name)
	{
		this.name = name;
	}

	/**
	 * Sets the usage of the command.
	 *
	 * @param usage The usage of the command.
	 * @return The current builder.
	 * @see Command#setUsage(String)
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
	 * @see Command#setUsage(Function)
	 */
	public CommandBuilder<S> usage(Function<S, String> usage)
	{
		usageGetter = usage;
		return this;
	}

	/**
	 * Sets the description of the command.
	 *
	 * @param description The description of the command.
	 * @return The current builder.
	 * @see Command#setDescription(String)
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
	 * @see Command#setDescription(Function)
	 */
	public CommandBuilder<S> description(Function<S, String> description)
	{
		descriptionGetter = description;
		return this;
	}

	/**
	 * Sets the aliases of the command.
	 *
	 * @param aliases The aliases of the command.
	 * @return The current builder.
	 * @see Command#setAliases(List)
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
	 * @see Command#setAliases(String...)
	 */
	public CommandBuilder<S> aliases(@NotNull String... aliases)
	{
		return aliases(Arrays.asList(aliases));
	}

	/**
	 * Sets the required permission to execute the command.
	 *
	 * @param permissionRequired The required permission.
	 * @return The current builder.
	 * @see Command#setPermissionRequired(String)
	 */
	public CommandBuilder<S> permission(@Nullable String permissionRequired)
	{
		this.permissionRequired = permissionRequired;
		return this;
	}

	/**
	 * Sets the executor of the command.
	 *
	 * @param executor The executor of the command.
	 * @return The current builder.
	 * @see Command#setExecutor(CommandExecutor)
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
	 * @param tabCompleter The tab completer of the command.
	 * @return The current builder.
	 * @see Command#setTabCompleter(CommandTabCompleter)
	 */
	public CommandBuilder<S> tabCompleter(@NotNull CommandTabCompleter<S> tabCompleter)
	{
		Objects.requireNonNull(tabCompleter, "Tab completer cannot be null.");
		this.tabCompleter = tabCompleter;
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
		command.setDescription(description);
		if (descriptionGetter != null)
			command.setDescription(descriptionGetter);
		command.setUsage(usage);
		if (usageGetter != null)
			command.setUsage(usageGetter);
		command.setPermissionRequired(permissionRequired);
		command.setAliases(aliases);
		command.setExecutor(executor);
		command.setTabCompleter(tabCompleter);
		return command;
	}
}