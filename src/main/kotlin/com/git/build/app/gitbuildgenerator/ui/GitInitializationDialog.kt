package com.git.build.app.gitbuildgenerator.ui

import com.git.build.app.gitbuildgenerator.network.RetrofitClient
import com.git.build.app.gitbuildgenerator.network.enqueue
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import java.awt.*
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class GitInitializationDialog: DialogWrapper(false) {
    private val mainPanel = JPanel(VerticalLayout(5))
    private var branches = arrayOf("master")
    private val textInputField = JBTextField()
    private val searchableComboBox = ComboBox<String>()
    private val searchField = JBTextField()
    private val addPairButton = JButton("Add")

    init {
        init()
        title = "Build Creator"

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
            val pairPanel = createKeyValuePairPanel()
            mainPanel.add(pairPanel)
            mainPanel.revalidate() // Revalidate the panel to apply changes
            mainPanel.repaint() // Repaint the panel
        }
    }

    override fun doOKAction() {
        super.doOKAction()
    }

    override fun doCancelAction() {
        super.doCancelAction()
    }

    override fun createCenterPanel(): JComponent {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val windowWidth = screenSize.width / 2 // Set width to 50% of the screen width
        val windowHeight = screenSize.height / 2 // Set height to 50% of the screen height



        mainPanel.add(JLabel("Search:"))
        mainPanel.add(searchField)

        // Searchable Branch Selection
        mainPanel.add(JLabel("Select a branch:"))
        mainPanel.add(searchableComboBox)

        textInputField.emptyText.text = "https://gitlab.example.com/api/v4/projects/:id" // Set the hint
        mainPanel.add(Label("Git Repo URL:"))
        mainPanel.add(textInputField)


        mainPanel.add(addPairButton)


        mainPanel.preferredSize = Dimension(windowWidth, windowHeight)

        val paddedPanel = JPanel(BorderLayout())
        paddedPanel.border = BorderFactory.createEmptyBorder(12, 12, 12, 12)
        paddedPanel.add(JBScrollPane(mainPanel))
        return paddedPanel
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

        val pairPanel = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(keyField)
            add(valueField)
            add(deleteButton)
        }

        deleteButton.addActionListener {
            mainPanel.remove(pairPanel)
            mainPanel.revalidate()
            mainPanel.repaint()
        }

        return pairPanel
    }
    private fun searchBranches(string: String? = null) {
        val call = RetrofitClient.instance.getBranches("Km_YGiwyr-Sr72kg6hSK", 1000,search = string)

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



