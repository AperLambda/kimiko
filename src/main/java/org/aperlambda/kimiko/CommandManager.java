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
import org.aperlambda.lambdacommon.utils.Optional;

import java.util.List;

/**
 * Represents a manager for commands.
 *
 * @param <S> The typename of the sender.
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class CommandManager<S>
{
	public abstract void register(Command<S> command);

	public abstract boolean hasCommand(ResourceName name);

	public abstract Optional<Command<S>> getCommand(ResourceName name);

	public abstract List<Command<S>> getCommands();

	public abstract void clearCommands();
}