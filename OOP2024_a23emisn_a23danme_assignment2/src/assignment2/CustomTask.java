package assignment2;

import se.his.it401g.todo.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;

// Represents a custom task in the ToDo list
//It extends the JPanel, allowing tasks to be displayed as a component within the UI
public class CustomTask extends JPanel implements Task {

	// UI components for the task
	private JTextField text; // Field for task description
	private JLabel textLabel;// Label to display additional info or status
	private JCheckBox completed = new JCheckBox(); // Checkbox to mark the task as completed or not
	private TaskListener listener; // Listener for task-related events

	// Constructs CustomTask with initialized UI components and listeners
	public CustomTask() {
		super(new BorderLayout()); // Set BorderLayout for the task panel layout
		this.text = new JTextField("New task", 20); // Initialize the text field with default text
		this.textLabel = new JLabel(); // Label for displaying task information, initially empty
		this.textLabel.setVisible(true); // Ensure the label is visible

		// Panel to hold the text field and label, using FlowLayout for layout
		// management
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.add(text); // Add the text field to the panel
		centerPanel.add(textLabel); // Add the label to the panel
		add(centerPanel, BorderLayout.CENTER); // Add the center panel to the main task panel

		// Initialize and set up the task input listener for keyboard and mouse events
		TaskInputListener inputListener = new TaskInputListener(this, text, textLabel);
		this.text.addKeyListener(inputListener); // Add listener to the text field for keyboard events
		this.textLabel.addMouseListener(inputListener); // Add listener to the label for mouse events

		// Button to highlight the task
		JButton highlightButton = new JButton("Highlight");
		highlightButton.addActionListener(new HighlightActionListener(this)); // Add action listener for highlight

		// Button for removing the task from the list
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(inputListener); // Reuse the input listener for removal functionality

		// Panel for task action buttons, using FlowLayout to align buttons to the right
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(highlightButton); // Add the highlight button to the panel
		buttonPanel.add(removeButton); // Add the remove button to the panel
		add(buttonPanel, BorderLayout.EAST); // Add the button panel to the main task panel

		// Add the completed checkbox to the main task panel, aligned to the left
		add(completed, BorderLayout.WEST);
		completed.addItemListener(inputListener); // Add item listener to handle checkbox changes

		setMaximumSize(new Dimension(1000, 50)); // Set the maximum size for the task panel
		setBorder(new TitledBorder(getTaskType())); // Set a titled border with the task type
	}

	// Return a short description/title of the task
	@Override
	public String getText() {
		return text.getText();
	}

	// Return the Custom name of the task
	@Override
	public String getTaskType() {
		return "Custom";
	}

	// Sets task listener on which this task reports actions
	// @param t, the listener to use
	@Override
	public void setTaskListener(TaskListener t) {
		listener = t;
	}

	// Return the current TaskListener
	@Override
	public TaskListener getTaskListener() {
		return listener;
	}

	// Return true if task-status is "complete", otherwise false.
	@Override
	public boolean isComplete() {
		return completed.isSelected();
	}

	// Return the GUI component representing the task
	@Override
	public Component getGuiComponent() {
		return this;
	}

	// Inner class that implements ActionListener to handle highlight actions
	// Changing background color of the task to show if its highlighted
	class HighlightActionListener implements ActionListener {
		private CustomTask task; // Reference to the CustomTask instance
		private Color highlightColor = new Color(204, 255, 0); // Color used to highlight a task

		public HighlightActionListener(CustomTask task) {
			this.task = task; // Set the task reference
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (task.getBackground().equals(highlightColor)) {
				task.setBackground(null); // Remove highlight if already highlighted
			} else {
				task.setBackground(highlightColor); // Highlight the task
			}
		}
	}
}