/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2022 The IdeaVim authors
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
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.maddyhome.idea.vim.action.motion.text

import com.maddyhome.idea.vim.api.ExecutionContext
import com.maddyhome.idea.vim.api.VimCaret
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.Argument
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.command.MotionType
import com.maddyhome.idea.vim.command.OperatorArguments
import com.maddyhome.idea.vim.common.Direction
import com.maddyhome.idea.vim.handler.Motion
import com.maddyhome.idea.vim.handler.MotionActionHandler
import com.maddyhome.idea.vim.handler.toMotionOrError
import com.maddyhome.idea.vim.helper.enumSetOf
import java.util.*

class MotionSectionBackwardEndAction : MotionSectionAction('}', Direction.BACKWARDS)
class MotionSectionBackwardStartAction : MotionSectionAction('{', Direction.BACKWARDS)
class MotionSectionForwardEndAction : MotionSectionAction('}', Direction.FORWARDS)
class MotionSectionForwardStartAction : MotionSectionAction('{', Direction.FORWARDS)

sealed class MotionSectionAction(private val charType: Char, val direction: Direction) : MotionActionHandler.ForEachCaret() {
  override val flags: EnumSet<CommandFlags> = enumSetOf(CommandFlags.FLAG_SAVE_JUMP)

  override fun getOffset(
    editor: VimEditor,
    caret: VimCaret,
    context: ExecutionContext,
    argument: Argument?,
    operatorArguments: OperatorArguments,
  ): Motion {
    return getCaretToSectionMotion(
      editor,
      caret,
      charType,
      direction.toInt(),
      operatorArguments.count1
    ).toMotionOrError()
  }

  override val motionType: MotionType = MotionType.EXCLUSIVE
}

fun getCaretToSectionMotion(editor: VimEditor, caret: VimCaret, type: Char, dir: Int, count: Int): Int {
  return if (caret.offset.point == 0 && count < 0 || caret.offset.point >= editor.fileSize() - 1 && count > 0) {
    -1
  } else {
    var res = injector.searchHelper.findSection(editor, caret, type, dir, count)
    if (res != -1) {
      res = injector.engineEditorHelper.normalizeOffset(editor, res, false)
    }
    res
  }
}