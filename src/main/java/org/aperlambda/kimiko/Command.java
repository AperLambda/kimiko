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
import org.aperlambda.lambdacommon.utils.Nameable;
import org.aperlambda.lambdacommon.utils.Optional;
import org.aperlambda.lambdacommon.utils.Pair;
import org.aperlambda.lambdacommon.utils.ResourceNameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a command.
 *
 * @param <S> The typename of the sender.
 * @version 1.0.1
 * @since 1.0.0
 */
public class Command<S> implements ResourceNameable
{
	/**
	 * Represents the default tab completer of commands.
	 */
	public static final CommandTabCompleter DEFAULT_TAB_COMPLETER = ((context, command, label, args) -> null);

	private final @NotNull ResourceName           name;
	private                Command<S>             parent;
	private                String                 usage;
	private @NotNull       Function<S, String>    usageGetter       = (sender) -> getUsage();
	private                String                 description;
	private @NotNull       Function<S, String>    descriptionGetter = (sender) -> getDescription();
	private @NotNull       List<String>           aliases           = new ArrayList<>();
	private                String                 permissionRequired;
	private final          List<Command<S>>       subCommands       = new ArrayList<>();
	private                CommandExecutor<S>     executor;
	@SuppressWarnings("unchecked")
	private                CommandTabCompleter<S> tabCompleter      = (CommandTabCompleter<S>) DEFAULT_TAB_COMPLETER;

	public Command(@NotNull ResourceName name)
	{
		this(null, name);
	}

	public Command(@Nullable Command<S> parent, @NotNull ResourceName name)
	{
		this.name = name;
		setParent(parent);
	}

	/**
	 * Gets the parent command of the command.
	 *
	 * @return The parent command.
	 */
	public @Nullable Command<S> getParent()
	{
		return parent;
	}

	protected void setParent(@Nullable Command<S> parent)
	{
		this.parent = parent;
	}

	/**
	 * Checks whether the command has a parent command or not.
	 *
	 * @return True if the command has a parent, else false.
	 */
	public boolean hasParent()
	{
		return parent != null;
	}

	/**
	 * Gets the usage of the command.
	 * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put {@code <command>} for the command name, kimiko will replace it automatically in the usage error message.</p>
	 *
	 * @return The usage.
	 */
	public String getUsage()
	{
		return usage;
	}

	/**
	 * Sets the usage of the command.
	 * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put {@code <command>} for the command name, kimiko will replace it automatically in the usage.</p>
	 *
	 * @param usage The usage of the command.
	 */
	public void setUsage(String usage)
	{
		this.usage = usage.replace("<command>", getName());
	}

	/**
	 * Gets the usage of the command depending of the sender..
	 * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put ${@code <command>} for the command name, Shulker will replace it automatically in the usage error message.</p>
	 *
	 * @param sender The sender who request the usage.
	 * @return The usage.
	 * @see Command#getUsage()
	 */
	public @NotNull String getUsage(S sender)
	{
		return usageGetter.apply(sender);
	}

	/**
	 * Sets the usage of the command depending of the sender.
	 *
	 * @param usage The usage getter of the command.
	 */
	public void setUsage(@NotNull Function<S, String> usage)
	{
		Objects.requireNonNull(usage, "Usage getter cannot be null.");
		usageGetter = usage;
	}

	/**
	 * Gets the description of the command.
	 *
	 * @return The description.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the description of the command.
	 *
	 * @param description The description.
	 */
	public void setDescription(@NotNull String description)
	{
		this.description = description;
	}

	/**
	 * Gets the description of the command depending of the sender.
	 * <p>By default it returns the value of {@link Command#getDescription()} ()}</p>
	 *
	 * @param sender The sender who request the description.
	 * @return The description of the command.
	 * @see Command#getDescription()
	 */
	public @NotNull String getDescription(S sender)
	{
		return descriptionGetter.apply(sender);
	}

	/**
	 * Sets the description of the command depending of the sender.
	 *
	 * @param description The description getter of the command.
	 */
	public void setDescription(@NotNull Function<S, String> description)
	{
		Objects.requireNonNull(description, "Description getter cannot be null.");
		descriptionGetter = description;
	}

	/**
	 * Gets the aliases of the command. Can be empty but not null.
	 *
	 * @return The aliases.
	 */
	public @NotNull List<String> getAliases()
	{
		return aliases;
	}

	/**
	 * Sets the aliases of the command.
	 *
	 * @param aliases The aliases of the command.
	 */
	public void setAliases(@NotNull List<String> aliases)
	{
		this.aliases = aliases;
	}

	/**
	 * Sets the aliases of the command.
	 *
	 * @param aliases The aliases of the command.
	 */
	public void setAliases(@NotNull String... aliases)
	{
		setAliases(Arrays.asList(aliases));
	}

	/**
	 * Gets the permission that is required to execute this command.
	 *
	 * @return The required permission.
	 */
	public @Nullable String getPermissionRequired()
	{
		return permissionRequired;
	}

	/**
	 * Sets the permission that is required to execute this command.
	 *
	 * @param permissionRequired The required permission.
	 */
	public void setPermissionRequired(String permissionRequired)
	{
		this.permissionRequired = permissionRequired;
	}

	/**
	 * Gets the executor of the command.
	 *
	 * @return The executor.
	 */
	public CommandExecutor<S> getExecutor()
	{
		return executor;
	}

	/**
	 * Sets the executor of the command.
	 *
	 * @param executor The executor of the command.
	 */
	public void setExecutor(@NotNull CommandExecutor<S> executor)
	{
		Objects.requireNonNull(executor, "Command executor cannot be null.");
		this.executor = executor;
	}

	/**
	 * Gets the tab completer of the command.
	 *
	 * @return The tab completer.
	 */
	public CommandTabCompleter<S> getTabCompleter()
	{
		return tabCompleter;
	}

	/**
	 * Sets the tab completer of the command.
	 *
	 * @param tabCompleter The tab completer of the command.
	 */
	public void setTabCompleter(@NotNull CommandTabCompleter<S> tabCompleter)
	{
		Objects.requireNonNull(tabCompleter, "Command tab completer cannot be null.");
		this.tabCompleter = tabCompleter;
	}

	/**
	 * Represents the execution process of the command.
	 * <p>The {@code args} argument represents the arguments of the command and not the arguments of the parent command.</p>
	 *
	 * @param context The context of the command.
	 * @param label   The label used to call the command.
	 * @param args    The arguments of the command.
	 * @return The result of the execution of the command.
	 */
	public @NotNull CommandResult execute(CommandContext<S> context, String label, String[] args)
	{
		return executor.execute(context, this, label, args);
	}

	public final @NotNull Pair<CommandResult, String> handleExecution(CommandContext<S> context, String label, String[] args)
	{
		CommandResult result;
		var usage = getUsage(context.getSender()).replace("<command>", getName());
		var subLabel = args[0];
		if (hasSubCommand(subLabel))
		{
			var command = getSubCommand(subLabel).get();
			if (command.getPermissionRequired() != null && !context.hasPermission(permissionRequired))
				result = CommandResult.ERROR_PERMISSION;
			else
				return command.handleExecution(context, subLabel, Arrays.copyOfRange(args, 1, args.length));
			usage = getName() + " " + command.getUsage(context.getSender()).replace("<command>", command.getName());
		}
		else
			result = execute(context, label, args);

		if (result == CommandResult.ERROR_USAGE)
			return new Pair<>(result, usage);

		return new Pair<>(result, null);
	}

	public List<String> onTabComplete(CommandContext<S> context, String label, String[] args)
	{
		if (args.length == 1)
		{
			var subCommands = getSubCommands();
			if (subCommands.isEmpty())
				return tabCompleter.onTabComplete(context, this, label, args);
			var subCommandsString = subCommands.stream()
					.filter(sc -> context.hasPermission(sc.getPermissionRequired()))
					.map(Nameable::getName).collect(Collectors.toList());
			var additionalCompletion = tabCompleter.onTabComplete(context, this, label, args);
			if (additionalCompletion != null && !additionalCompletion.isEmpty())
				subCommandsString.addAll(additionalCompletion);
			return subCommandsString.stream().filter(sc -> sc.startsWith(args[0])).sorted().collect(Collectors.toList());
		}
		else if (args.length > 1)
			if (hasSubCommand(args[0]))
			{
				var subCommand = getSubCommand(args[0]).get();
				if (context.hasPermission(subCommand.getPermissionRequired()))
					return subCommand.onTabComplete(context, label, Arrays.copyOfRange(args, 1, args.length));
			}
		return tabCompleter.onTabComplete(context, this, label, args);
	}

	/**
	 * Adds a new command to the command.
	 *
	 * @param subCommand The command to add.
	 */
	public void addSubCommand(@NotNull Command<S> subCommand)
	{
		if (subCommand.hasParent())
			return;
		if (subCommands.contains(subCommand))
			return;
		subCommand.setParent(this);
		subCommands.add(subCommand);
	}

	/**
	 * Adds a new command to the command.
	 *
	 * @param name         The name of the command.
	 * @param usage        The usage of the command.
	 * @param description  The description of the command.
	 * @param executor     The executor of the command.
	 * @param tabCompleter The tab-completer of the command.
	 */
	public void addSubCommand(@NotNull ResourceName name, @NotNull String usage, @NotNull String description, @NotNull CommandExecutor<S> executor, @Nullable CommandTabCompleter<S> tabCompleter)
	{
		addSubCommand(name, usage, description, null, new ArrayList<>(), executor, tabCompleter);
	}

	/**
	 * Adds a new command to the command.
	 *
	 * @param name         The name of the command.
	 * @param usage        The usage of the command.
	 * @param description  The description of the command.
	 * @param permission   The permission required to execute the command, may be null.
	 * @param aliases      The aliases of the command.
	 * @param executor     The executor of the command.
	 * @param tabCompleter The tab-completer of the command.
	 */
	public void addSubCommand(@NotNull ResourceName name, @NotNull String usage, @NotNull String description, @Nullable String permission, @NotNull List<String> aliases, @NotNull CommandExecutor<S> executor, @Nullable CommandTabCompleter<S> tabCompleter)
	{
		var command = new Command<S>(name);
		command.setUsage(usage);
		command.setDescription(description);
		command.setPermissionRequired(permissionRequired);
		command.setAliases(aliases);
		command.setExecutor(executor);
		if (tabCompleter != null)
			command.setTabCompleter(tabCompleter);
		addSubCommand(command);
	}

	/**
	 * Checks whether the command has the specified command.
	 *
	 * @param subCommand The command to check.
	 * @return True if found, else false.
	 */
	public boolean hasSubCommand(@NotNull Command<S> subCommand)
	{
		return subCommands.contains(subCommand);
	}

	/**
	 * Checks whether the command has the specified command.
	 *
	 * @param label The name (or alias) of the command to check.
	 * @return True if found, else false.
	 */
	public boolean hasSubCommand(@NotNull String label)
	{
		var finalLabel = label.toLowerCase();
		return subCommands.stream().anyMatch(cmd -> cmd.getName().equalsIgnoreCase(finalLabel) ||
				cmd.getAliases().contains(finalLabel));
	}

	/**
	 * Removes the specified command.
	 *
	 * @param subCommand The command to remove.
	 */
	public void removeSubCommand(@NotNull Command<S> subCommand)
	{
		subCommands.remove(subCommand);
	}

	/**
	 * Gets a command by it's name or alias.
	 *
	 * @param label The name or the alias of the command.
	 * @return The optional command.
	 */
	public @NotNull Optional<Command<S>> getSubCommand(@NotNull String label)
	{
		var finalLabel = label.toLowerCase();
		return Optional.fromJava(subCommands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(finalLabel) ||
				cmd.getAliases().contains(finalLabel)).findFirst());
	}

	/**
	 * Gets the commands of the command.
	 *
	 * @return A list of the commands.
	 */
	public @NotNull List<Command<S>> getSubCommands()
	{
		return new ArrayList<>(subCommands);
	}

	@Override
	public @NotNull ResourceName getResourceName()
	{
		return name;
	}
}