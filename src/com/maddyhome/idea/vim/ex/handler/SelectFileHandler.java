/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2013 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.ex.handler;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.ex.CommandHandler;
import com.maddyhome.idea.vim.ex.ExCommand;
import com.maddyhome.idea.vim.group.CommandGroups;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class SelectFileHandler extends CommandHandler {
  public SelectFileHandler() {
    super("argu", "ment", RANGE_OPTIONAL | ARGUMENT_OPTIONAL | RANGE_IS_COUNT | DONT_REOPEN);
  }

  public boolean execute(@NotNull Editor editor, @NotNull DataContext context, @NotNull ExCommand cmd) {
    int count = cmd.getCount(editor, context, 0, true);

    if (count > 0) {
      boolean res = CommandGroups.getInstance().getFile().selectFile(count - 1, context);
      if (res) {
        CommandGroups.getInstance().getMark().saveJumpLocation(editor);
      }

      return res;
    }

    return true;
  }
}
