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

import java.util.List;
import java.util.Optional;

/**
 * Represents a manager for commands.
 *
 * @param <S> The typename of the sender.
 * @version 1.1.0
 * @since 1.0.0
 */
public abstract class CommandManager<S>
{
    public abstract void register(Command<S> command);

    public abstract boolean has_command(ResourceName name);

    public abstract Optional<Command<S>> get_command(ResourceName name);

    public abstract List<Command<S>> get_commands();

    public abstract void clear_commands();
}