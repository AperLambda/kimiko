/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of kimiko.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.kimiko;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an executor handler of a command.
 *
 * @param <S> The typename of the sender.
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface CommandExecutor<S>
{
	/**
	 * Represents the execution process of the command.
	 * <p>The {@code args} argument represents the arguments of the command and not the arguments of the parent command.</p>
	 *
	 * @param context The context of the command.
	 * @param command The command which is executed.
	 * @param label   The label used to call the command.
	 * @param args    The arguments of the command.
	 * @return The result of the execution of the command.
	 */
	@NotNull CommandResult execute(CommandContext<S> context, @NotNull Command command, String label, String[] args);
}