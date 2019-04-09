/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
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
 * @version 1.1.0
 * @since 1.0.0
 */
public interface CommandContext<S>
{
    /**
     * Gets the sender of the command.
     *
     * @return The sender of the command.
     */
    S get_sender();

    /**
     * Gets the command sender's name.
     *
     * @return The command sender's name.
     */
    String get_sender_name();

    /**
     * Sends a message to the sender.
     *
     * @param message The message to send.
     */
    void send_message(String message);

    /**
     * Checks whether the sender has the permission or not.
     *
     * @param permission The permission to test.
     * @return True if the sender has the permission, else false.
     */
    boolean has_permission(String permission);
}