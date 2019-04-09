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

import java.util.List;

/**
 * Represents a tab completer handler of a command.
 *
 * @param <S> The typename of the sender.
 * @version 1.1.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface CommandTabCompleter<S>
{
    List<String> on_tab_complete(CommandContext<S> context, @NotNull Command<S> command, String label, String[] args);
}