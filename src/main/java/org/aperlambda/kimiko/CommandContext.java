/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of kimiko.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.kimiko;

/**
 * Represents a context of a command execution or a command tab completion.
 *
 * @param <S> The typename of the sender.
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CommandContext<S>
{
	/**
	 * Gets the sender of the command.
	 *
	 * @return The sender of the command.
	 */
	S getSender();

	/**
	 * Gets the command sender's name.
	 *
	 * @return The command sender's name.
	 */
	String getSenderName();

	/**
	 * Checks whether the sender has the permission or not.
	 *
	 * @param permission The permission to test.
	 * @return True if the sender has the permission, else false.
	 */
	boolean testPermission(String permission);
}