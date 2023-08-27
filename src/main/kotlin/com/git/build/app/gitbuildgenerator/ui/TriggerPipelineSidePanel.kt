package com.git.build.app.gitbuildgenerator.ui

import com.git.build.app.gitbuildgenerator.network.RetrofitClient
import com.git.build.app.gitbuildgenerator.network.enqueue
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.JBUI
import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


class TriggerPipelineSidePanel : ToolWindowFactory {
    private val mainPanel = JPanel(VerticalLayout(5))
    private var branches = arrayOf("master")
    private val textInputField = JBTextField()
    private val searchableComboBox = ComboBox<String>()
    private val searchField = JBTextField()
    private val addButtonPanel = JPanel()
    private val triggerButton = JButton("Trigger")
    private val addPairButton = JButton("Add Key-Value")
    private val keyValuePairs = mutableListOf<Pair<String, String>>()
    private val pairPanels = mutableListOf<JPanel>()

    init {
        searchField.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) {
                println("Search: ${e.document.getText(0, e.document.length)}")
                searchBranches(e.document.getText(0, e.document.length))
            }

            override fun removeUpdate(e: DocumentEvent) {
                println("Search: ${e.document.getText(0, e.document.length)}")
                searchBranches(e.document.getText(0, e.document.length))
            }

            override fun changedUpdate(e: DocumentEvent) {
                println("Search: ${e.document.getText(0, e.document.length)}")
                searchBranches(e.document.getText(0, e.document.length))
            }
        })

        addPairButton.addActionListener {
            val newPairPanel = createKeyValuePairPanel()
            pairPanels.add(newPairPanel)
            mainPanel.add(newPairPanel)
            mainPanel.revalidate()
            mainPanel.repaint()
        }

        triggerButton.addActionListener {
            collectKeyValuePairs()
            println("PAIR VALUES: $keyValuePairs")
        }
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        addButtonPanel.layout = BoxLayout(addButtonPanel, BoxLayout.X_AXIS)
        addButtonPanel.add(Box.createHorizontalGlue()) // Pushes the "Add" button to the right
        addButtonPanel.add(addPairButton)

        mainPanel.add(JLabel("Search:"))
        mainPanel.add(searchField)

        // Searchable Branch Selection
        mainPanel.add(JLabel("Select a branch:"))
        mainPanel.add(searchableComboBox)

        textInputField.emptyText.text = "https://gitlab.example.com/api/v4/projects/:id" // Set the hint
        mainPanel.add(Label("Git Repo URL:"))
        mainPanel.add(textInputField)

        mainPanel.add(addButtonPanel)
        mainPanel.add(triggerButton)

        val paddedPanel = JPanel(BorderLayout())
        paddedPanel.border = BorderFactory.createEmptyBorder(12, 12, 12, 12)
        paddedPanel.add(JBScrollPane(mainPanel))

        // Create content for the tool window
        val contentFactory = ContentFactory.getInstance()
        val content: Content = contentFactory.createContent(paddedPanel, "", false)

        // Add content to the tool window
        toolWindow.contentManager.addContent(content)
    }

    private fun createKeyValuePairPanel(): JPanel {
        val keyField = JBTextField().apply {
            emptyText.text = "Key"
            columns = 10 // Adjust as needed
        }
        val valueField = JBTextField().apply {
            emptyText.text = "Value"
            columns = 10 // Adjust as needed
        }
        val deleteButton = JButton("Delete").apply {
            isFocusable = false // To prevent focus rectangle around the button
        }
        val checkBox = JCheckBox() // Create a checkbox

        val pairPanel = JPanel(GridBagLayout()).apply {
            val gbc = GridBagConstraints()
            gbc.fill = GridBagConstraints.HORIZONTAL
            gbc.weightx = 0.0 // Adjust as needed
            gbc.gridx = 0
            gbc.gridy = 0
            gbc.insets = JBUI.insetsRight(5) // Add some spacing to the right of the checkbox
            add(checkBox, gbc) // Add the checkbox

            gbc.gridx = 1
            gbc.weightx = 1.0 // Adjust as needed
            gbc.insets = JBUI.insetsRight(5) // Add some spacing to the right of keyField
            add(keyField, gbc)

            gbc.gridx = 2
            gbc.insets = JBUI.insetsRight(5) // Add some spacing to the right of valueField
            add(valueField, gbc)

            gbc.gridx = 3
            gbc.weightx = 0.0 // Reset weight for the deleteButton
            gbc.insets = JBUI.emptyInsets() // No spacing for deleteButton
            add(deleteButton, gbc)
        }

        deleteButton.addActionListener {
            mainPanel.remove(pairPanel)
            mainPanel.revalidate()
            mainPanel.repaint()
            keyValuePairs.remove(keyField.text to valueField.text)
        }

        checkBox.addActionListener {
            if (checkBox.isSelected){
                keyValuePairs.add(keyField.text to valueField.text)
            }else{
                keyValuePairs.remove(keyField.text to valueField.text)
            }
        }

        return pairPanel
    }


    private fun collectKeyValuePairs() {
        keyValuePairs.clear()
        for (pairPanel in pairPanels) {
            val checkBox = pairPanel.getComponent(0) as? JCheckBox
            val keyField = pairPanel.getComponent(1) as? JBTextField
            val valueField = pairPanel.getComponent(2) as? JBTextField

            if (checkBox?.isSelected == true && keyField?.text?.isNotEmpty() == true && valueField?.text?.isNotEmpty() == true) {
                keyValuePairs.add(keyField.text to valueField.text)
            }
        }
    }


    private fun searchBranches(string: String? = null) {
        val call = RetrofitClient.instance.getBranches("Your Access Token", 1000, search = string)

        call.enqueue(
            onResponse = { response ->
                println(response)
                val newBranches = response.mapNotNull { it.name }
                branches = newBranches.toTypedArray() // Update branches array directly

                // Update the combo box with the new branches
                updateBranchesComboBox()
            },
            onFailure = { t ->
                // Handle network failure
                println("Error: ${t.message}")
            }
        )
    }

    private fun updateBranchesComboBox() {
        searchableComboBox.removeAllItems()
        branches.forEach { branch ->
            searchableComboBox.addItem(branch)
        }
    }

}



