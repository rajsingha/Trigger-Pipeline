package com.git.build.app.gitbuildgenerator.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel


class HelloWorldToolFactory: ToolWindowFactory{


    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = JPanel()
        val label = JLabel("Hello, World!")

        val button = JButton("Click Me")

        button.addActionListener {
            val dialog = GitInitializationDialog()
            dialog.show()
        }
        // Add components to the panel

        // Add components to the panel
        panel.add(button)
        panel.add(label)

        // Create content for the tool window

        // Create content for the tool window
        val contentFactory = ContentFactory.getInstance()
        val content: Content = contentFactory.createContent(panel, "", false)

        // Add content to the tool window

        // Add content to the tool window
        toolWindow.contentManager.addContent(content)
    }

}