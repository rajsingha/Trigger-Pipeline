package com.git.build.app.gitbuildgenerator.actions

import com.git.build.app.gitbuildgenerator.ui.GitInitializationDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class GitInitializationAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val dialog = GitInitializationDialog()
        dialog.show()
    }
}