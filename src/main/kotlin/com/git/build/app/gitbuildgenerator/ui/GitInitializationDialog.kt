package com.git.build.app.gitbuildgenerator.ui

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.components.panels.VerticalLayout
import java.awt.Dimension
import java.awt.Label
import java.awt.Toolkit
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class GitInitializationDialog: DialogWrapper(false) {
    private val branches = arrayOf("Branch A", "Branch B", "Branch C","fb/PLugin","v_3.8.1","LPM-1234")
    private val textInputFields = mutableListOf<JBTextField>()
    private val textInputField = JBTextField()

    companion object{
        fun newInstance(){
            GitInitializationDialog.apply {

            }
        }
    }
    init {
        init()
        title = "Build Creator"
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

        val mainPanel = JPanel(VerticalLayout(5))
        // Searchable Branch Selection
        mainPanel.add(JLabel("Select a branch:"))
        val branchComboBox = ComboBox(branches)
        val searchableComboBox = ComboboxWithFilter(branchComboBox)
        mainPanel.add(searchableComboBox)

        textInputField.emptyText.text = "https://gitlab.example.com/api/v4/projects/:id" // Set the hint
        mainPanel.add(Label("Git Repo URL:"))
        mainPanel.add(textInputField)

        // Set preferred size to 50% of the screen size
        val preferredSize = Dimension(windowWidth, windowHeight)
        mainPanel.preferredSize = preferredSize

        setOKButtonText("Save")
        return JBScrollPane(mainPanel)
    }
}

class ComboboxWithFilter(private val comboBox: ComboBox<String>) : JPanel() {
    init {
        layout = VerticalLayout(0)
        add(comboBox)
        val document = (comboBox.editor.editorComponent as? JBTextField)?.document
        document?.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent) {
                updateFilter(e.document.getText(0, e.document.length))
            }

            override fun removeUpdate(e: DocumentEvent) {
                updateFilter(e.document.getText(0, e.document.length))
            }

            override fun changedUpdate(e: DocumentEvent) {
                updateFilter(e.document.getText(0, e.document.length))
            }
        })
    }

    private fun updateFilter(filter: String) {
        val model = comboBox.model
        val items = mutableListOf<String>()
        for (i in 0 until model.size) {
            val item = model.getElementAt(i)
            if (item.contains(filter, ignoreCase = true)) {
                items.add(item)
            }
        }
        comboBox.removeAllItems()
        items.forEach { comboBox.addItem(it) }
        comboBox.isPopupVisible = true
    }

}




