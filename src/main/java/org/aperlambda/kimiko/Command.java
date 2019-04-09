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
import org.aperlambda.lambdacommon.utils.Nameable;
import org.aperlambda.lambdacommon.utils.Pair;
import org.aperlambda.lambdacommon.utils.ResourceNameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a command.
 *
 * @param <S> The typename of the sender.
 * @version 1.1.0
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
    private @NotNull       Function<S, String>    usage_getter  = (sender) -> get_usage();
    private                String                 description;
    private @NotNull       Function<S, String>    descr_getter  = (sender) -> get_description();
    private @NotNull       List<String>           aliases       = new ArrayList<>();
    private                String                 required_permission;
    private final          List<Command<S>>       sub_commands  = new ArrayList<>();
    private                CommandExecutor<S>     executor;
    @SuppressWarnings("unchecked")
    private                CommandTabCompleter<S> tab_completer = (CommandTabCompleter<S>) DEFAULT_TAB_COMPLETER;

    public Command(@NotNull ResourceName name)
    {
        this(null, name);
    }

    public Command(@Nullable Command<S> parent, @NotNull ResourceName name)
    {
        this.name = name;
        set_parent(parent);
    }

    /**
     * Gets the parent command of the command.
     *
     * @return The parent command.
     */
    public @Nullable Command<S> get_parent()
    {
        return this.parent;
    }

    protected void set_parent(@Nullable Command<S> parent)
    {
        this.parent = parent;
    }

    /**
     * Checks whether the command has a parent command or not.
     *
     * @return True if the command has a parent, else false.
     */
    public boolean has_parent()
    {
        return this.parent != null;
    }

    /**
     * Gets the usage of the command.
     * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put {@code <command>} for the command name, kimiko will replace it automatically in the usage error message.</p>
     *
     * @return The usage.
     */
    public String get_usage()
    {
        return this.usage;
    }

    /**
     * Sets the usage of the command.
     * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put {@code <command>} for the command name, kimiko will replace it automatically in the usage.</p>
     *
     * @param usage The usage of the command.
     */
    public void set_usage(String usage)
    {
        this.usage = usage.replace("<command>", get_name());
    }

    /**
     * Gets the usage of the command depending of the sender..
     * <p>The format is {@code <command> <required_argument> [optional_argument]} you can simply put ${@code <command>} for the command name, Shulker will replace it automatically in the usage error message.</p>
     *
     * @param sender The sender who request the usage.
     * @return The usage.
     * @see Command#get_usage()
     */
    public @NotNull String get_usage(S sender)
    {
        return this.usage_getter.apply(sender);
    }

    /**
     * Sets the usage of the command depending of the sender.
     *
     * @param usage The usage getter of the command.
     */
    public void set_usage(@NotNull Function<S, String> usage)
    {
        Objects.requireNonNull(usage, "Usage getter cannot be null.");
        usage_getter = usage;
    }

    /**
     * Gets the description of the command.
     *
     * @return The description.
     */
    public String get_description()
    {
        return this.description;
    }

    /**
     * Sets the description of the command.
     *
     * @param description The description.
     */
    public void set_description(@NotNull String description)
    {
        this.description = description;
    }

    /**
     * Gets the description of the command depending of the sender.
     * <p>By default it returns the value of {@link Command#get_description()} ()}</p>
     *
     * @param sender The sender who request the description.
     * @return The description of the command.
     * @see Command#get_description()
     */
    public @NotNull String get_description(S sender)
    {
        return descr_getter.apply(sender);
    }

    /**
     * Sets the description of the command depending of the sender.
     *
     * @param description The description getter of the command.
     */
    public void set_description(@NotNull Function<S, String> description)
    {
        Objects.requireNonNull(description, "Description getter cannot be null.");
        this.descr_getter = description;
    }

    /**
     * Gets the aliases of the command. Can be empty but not null.
     *
     * @return The aliases.
     */
    public @NotNull List<String> get_aliases()
    {
        return this.aliases;
    }

    /**
     * Sets the aliases of the command.
     *
     * @param aliases The aliases of the command.
     */
    public void set_aliases(@NotNull List<String> aliases)
    {
        this.aliases = aliases;
    }

    /**
     * Sets the aliases of the command.
     *
     * @param aliases The aliases of the command.
     */
    public void set_aliases(@NotNull String... aliases)
    {
        this.set_aliases(Arrays.asList(aliases));
    }

    /**
     * Gets the permission that is required to execute this command.
     *
     * @return The required permission.
     */
    public @Nullable String get_required_permission()
    {
        return this.required_permission;
    }

    /**
     * Sets the permission that is required to execute this command.
     *
     * @param required_permission The required permission.
     */
    public void set_required_permission(String required_permission)
    {
        this.required_permission = required_permission;
    }

    /**
     * Gets the executor of the command.
     *
     * @return The executor.
     */
    public CommandExecutor<S> get_executor()
    {
        return this.executor;
    }

    /**
     * Sets the executor of the command.
     *
     * @param executor The executor of the command.
     */
    public void set_executor(@NotNull CommandExecutor<S> executor)
    {
        Objects.requireNonNull(executor, "Command executor cannot be null.");
        this.executor = executor;
    }

    /**
     * Gets the tab completer of the command.
     *
     * @return The tab completer.
     */
    public CommandTabCompleter<S> get_tab_completer()
    {
        return this.tab_completer;
    }

    /**
     * Sets the tab completer of the command.
     *
     * @param tab_completer The tab completer of the command.
     */
    public void set_tab_completer(@NotNull CommandTabCompleter<S> tab_completer)
    {
        Objects.requireNonNull(tab_completer, "Command tab completer cannot be null.");
        this.tab_completer = tab_completer;
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

    private final @NotNull CommandResult handle_local_execution(CommandContext<S> context, String label, String[] args)
    {
        if (!context.has_permission(required_permission))
            return CommandResult.ERROR_PERMISSION;
        return execute(context, label, args);
    }

    public final @NotNull Pair<CommandResult, String> handle_execution(CommandContext<S> context, String label, String[] args)
    {
        CommandResult result;
        var usage = get_usage(context.get_sender()).replace("<command>", get_name());
        if (args.length == 0)
            result = handle_local_execution(context, label, args);
        else {
            var sub_label = args[0];
            var ocmd = get_sub_command(sub_label);
            if (ocmd.isPresent()) {
                var command = ocmd.get();
                if (command.get_required_permission() != null && !context.has_permission(command.get_required_permission()))
                    result = CommandResult.ERROR_PERMISSION;
                else
                    return command.handle_execution(context, sub_label, Arrays.copyOfRange(args, 1, args.length));
                usage = get_name() + " " + command.get_usage(context.get_sender()).replace("<command>", command.get_name());
            } else
                result = handle_local_execution(context, label, args);
        }

        if (result == CommandResult.ERROR_USAGE)
            return new Pair<>(result, usage);

        return new Pair<>(result, null);
    }

    public List<String> on_tab_complete(CommandContext<S> context, String label, String[] args)
    {
        if (args.length == 1) {
            var subCommands = get_sub_commands();
            if (subCommands.isEmpty())
                return tab_completer.on_tab_complete(context, this, label, args);
            var sub_cmds_str = subCommands.stream()
                    .filter(sc -> context.has_permission(sc.get_required_permission()))
                    .map(Nameable::get_name)
                    .collect(Collectors.toList());
            var additional_completion = tab_completer.on_tab_complete(context, this, label, args);
            if (additional_completion != null && !additional_completion.isEmpty())
                sub_cmds_str.addAll(additional_completion);
            return sub_cmds_str.stream().filter(sc -> sc.startsWith(args[0])).sorted().collect(Collectors.toList());
        } else if (args.length > 1) {
            var subCommand = get_sub_command(args[0]);
            if (subCommand.isPresent()) {
                if (context.has_permission(subCommand.get().get_required_permission()))
                    return subCommand.get().on_tab_complete(context, label, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return tab_completer.on_tab_complete(context, this, label, args);
    }

    /**
     * Adds a new command to the command.
     *
     * @param sub_commmand The command to add.
     */
    public void add_sub_command(@NotNull Command<S> sub_commmand)
    {
        if (sub_commmand.has_parent())
            return;
        if (sub_commands.contains(sub_commmand))
            return;
        sub_commmand.set_parent(this);
        sub_commands.add(sub_commmand);
    }

    /**
     * Adds a new command to the command.
     *
     * @param name          The name of the command.
     * @param usage         The usage of the command.
     * @param description   The description of the command.
     * @param executor      The executor of the command.
     * @param tab_completer The tab-completer of the command.
     */
    public void add_sub_command(@NotNull ResourceName name, @NotNull String usage, @NotNull String description, @NotNull CommandExecutor<S> executor, @Nullable CommandTabCompleter<S> tab_completer)
    {
        add_sub_command(name, usage, description, null, new ArrayList<>(), executor, tab_completer);
    }

    /**
     * Adds a new command to the command.
     *
     * @param name          The name of the command.
     * @param usage         The usage of the command.
     * @param description   The description of the command.
     * @param permission    The permission required to execute the command, may be null.
     * @param aliases       The aliases of the command.
     * @param executor      The executor of the command.
     * @param tab_completer The tab-completer of the command.
     */
    public void add_sub_command(@NotNull ResourceName name, @NotNull String usage, @NotNull String description, @Nullable String permission, @NotNull List<String> aliases, @NotNull CommandExecutor<S> executor, @Nullable CommandTabCompleter<S> tab_completer)
    {
        var command = new Command<S>(name);
        command.set_usage(usage);
        command.set_description(description);
        command.set_required_permission(permission);
        command.set_aliases(aliases);
        command.set_executor(executor);
        if (tab_completer != null)
            command.set_tab_completer(tab_completer);
        add_sub_command(command);
    }

    /**
     * Checks whether the command has the specified command.
     *
     * @param sub_command The command to check.
     * @return True if found, else false.
     */
    public boolean has_sub_command(@NotNull Command<S> sub_command)
    {
        return sub_commands.contains(sub_command);
    }

    /**
     * Checks whether the command has the specified command.
     *
     * @param label The name (or alias) of the command to check.
     * @return True if found, else false.
     */
    public boolean has_sub_command(@NotNull String label)
    {
        var final_label = label.toLowerCase();
        return sub_commands.stream().anyMatch(cmd -> cmd.get_name().equalsIgnoreCase(final_label) || cmd.get_aliases().contains(final_label));
    }

    /**
     * Removes the specified command.
     *
     * @param sub_command The command to remove.
     */
    public void remove_sub_command(@NotNull Command<S> sub_command)
    {
        sub_commands.remove(sub_command);
    }

    /**
     * Gets a command by it's name or alias.
     *
     * @param label The name or the alias of the command.
     * @return The optional command.
     */
    public @NotNull Optional<Command<S>> get_sub_command(@NotNull String label)
    {
        var final_label = label.toLowerCase();
        return sub_commands.stream().filter(cmd -> cmd.get_name().equalsIgnoreCase(final_label) || cmd.get_aliases().contains(final_label)).findFirst();
    }

    /**
     * Gets the commands of the command.
     *
     * @return A list of the commands.
     */
    public @NotNull List<Command<S>> get_sub_commands()
    {
        return new ArrayList<>(sub_commands);
    }

    @Override
    public @NotNull ResourceName get_resource_name()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Command{" +
                "name=" + name +
                ", parent=" + parent +
                ", usage='" + usage + '\'' +
                ", usage_getter=" + usage_getter +
                ", description='" + description + '\'' +
                ", descr_getter=" + descr_getter +
                ", aliases=" + aliases +
                ", required_permission='" + required_permission + '\'' +
                ", sub_commands=" + sub_commands +
                ", executor=" + executor +
                ", tab_completer=" + tab_completer +
                '}';
    }
}