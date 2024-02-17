package assignment2;

import se.his.it401g.todo.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

// Main class for ToDo
public class ToDo {
	private JFrame frame; // Main window of the application
	private JPanel taskPanel; // Panel for displaying tasks
	private JLabel completedTasksLabel; // Label showing the count of completed tasks
	private ArrayList<Task> tasks = new ArrayList<>(); // List of all tasks

	//Entry point of the application
	public static void main(String[] args) {
		ToDo application = new ToDo();
		application.execute(); // Initialize and run the application
	}
	
	// Sets up GUI and frame	 
	public void execute() {
		// Initialize the main application frame
		frame = new JFrame("ToDo List");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);

		// Main panel to hold UI components
		JPanel panel = new JPanel();
		frame.add(panel);

		// Set layout to organize components vertically
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Button to add a new "Home" task
		JButton addHomeTaskButton = new JButton("New HomeTask");
		addHomeTaskButton.addActionListener(new AddHomeTaskActionListener(this));

		// Button to add a new "Study" task
		JButton addStudyTaskButton = new JButton("New StudyTask");
		addStudyTaskButton.addActionListener(new AddStudyTaskActionListener(this));

		// Button to add a new "Custom" task
		JButton addCustomTaskButton = new JButton("New CustomTask");
		addCustomTaskButton.addActionListener(new AddCustomTaskActionListener(this));

		// Button to sort tasks alphabetically
		JButton alphabeticSortButton = new JButton("Sort Alphabetically");
		alphabeticSortButton.addActionListener(new AlphabeticSortActionListener(this));

		// Button to sort tasks by completion status
		JButton completionSortButton = new JButton("Sort by completion");
		completionSortButton.addActionListener(new CompletionSortActionListener(this));

		// Button to sort tasks by type
		JButton typeSortButton = new JButton("Sort by type");
		typeSortButton.addActionListener(new TypeSortActionListener(this));

		// Panel to hold task creation buttons
		JPanel buttonPanel = new JPanel();

		// Set layout to organize components horizontally
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		// Panel to hold task sorting buttons
		JPanel sortPanel = new JPanel();

		// Set layout to organize components horizontally
		sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.X_AXIS));

		// Add buttons to their respective panels
		buttonPanel.add(addHomeTaskButton);
		buttonPanel.add(addStudyTaskButton);
		buttonPanel.add(addCustomTaskButton);

		sortPanel.add(alphabeticSortButton);
		sortPanel.add(completionSortButton);
		sortPanel.add(typeSortButton);

		// Add button panels to the main panel
		panel.add(buttonPanel);
		panel.add(sortPanel);

		// Setup panel to list tasks
		taskPanel = new JPanel();
		taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(taskPanel);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Setup panel to display task completion statistics
		JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		completedTasksLabel = new JLabel("0 out of 0 tasks completed.");
		statsPanel.add(completedTasksLabel);
		frame.add(statsPanel, BorderLayout.SOUTH);

		// Display the main application window
		frame.setVisible(true);
	}

	
	//  Adds a new HomeTask	 
	private void addHomeTask() {
		Task homeTask = new HomeTask(); // Create a new "Home" task
		TaskListener listenerHomeTask = new MyTaskListener(tasks, this); // Create a task listener
		homeTask.setTaskListener(listenerHomeTask); // Associate the listener with the task
		tasks.add(homeTask); // Add the task to the list
		taskPanel.add(homeTask.getGuiComponent()); // Add the task's UI component to the display panel
		updateTaskPanel(); // Refresh the task display panel
		updateTaskStats(); // Update task completion statistics
	}
	
	// Adds a new StudyTask	 
	private void addStudyTask() {
		Task studyTask = new StudyTask(); // Create a new "Study" task
		TaskListener listenerStudyTask = new MyTaskListener(tasks, this); // Create a task listener
		studyTask.setTaskListener(listenerStudyTask); // Associate the listener with the task
		tasks.add(studyTask); // Add the task to the list
		taskPanel.add(studyTask.getGuiComponent()); // Add the task's UI component to the display panel
		updateTaskPanel(); // Refresh the task display panel
		updateTaskStats(); // Update task completion statistics
	}
	
	 // Adds a new CustomTask	 
	private void addCustomTask() {
		Task customTask = new CustomTask(); // Create a new "Custom" task
		TaskListener listenerCustomTask = new MyTaskListener(tasks, this); // Create a task listener
		customTask.setTaskListener(listenerCustomTask); // Associate the listener with the task
		tasks.add(customTask); // Add the task to the list
		taskPanel.add(customTask.getGuiComponent()); // Add the task's UI component to the display panel
		updateTaskPanel(); // Refresh the task display panel
		updateTaskStats(); // Update task completion statistics
	}

	 // Label updates with the current stats 
	public void updateTaskStats() {
		int totalTasks = tasks.size();
		int completedTasks = 0;
		for (Task task : tasks) {
			if (task.isComplete()) {
				completedTasks++;
			}
		}
		completedTasksLabel.setText(completedTasks + " out of " + totalTasks + " tasks completed.");
	}

	 // Refreshes the task panel 
	public void updateTaskPanel() {
		taskPanel.revalidate();
		taskPanel.repaint();
	}
	
	 // Sorts the tasks alphabetically and updates 
	private void sortTasksAlphabetically() {
		Collections.sort(tasks, new TaskAlphabeticalComparator()); // Sort tasks
		arrayListUpdate(); // Update the task list display
		updateTaskPanel(); // Refresh the task display panel
	}

	 // Comparator sorting tasks alphabetically by their text, not case-sensitive	 
	class TaskAlphabeticalComparator implements Comparator<Task> {
		@Override
		public int compare(Task t1, Task t2) {
			return t1.getText().compareToIgnoreCase(t2.getText());
		}
	}

	//  Sorts the tasks by completion status and updates	 
	private void sortTasksByCompletionStatus() {
		Collections.sort(tasks, Comparator.comparing(Task::isComplete).reversed());
		arrayListUpdate(); // Update the task list display
		updateTaskPanel(); // Refresh the task display panel
	}
	
	 // Sorts the tasks by type and updates	
	private void sortTasksByTaskType() {
		Collections.sort(tasks, Comparator.comparing(Task::getTaskType));
		arrayListUpdate(); // Update the task list display
		updateTaskPanel(); // Refresh the task display panel
	}

	 // Removes the task GUI component from panel and updates it	  
	 // @param task The task to remove 
	public void removeTaskGUIComponent(Task task) {
       taskPanel.remove(task.getGuiComponent());
       updateTaskPanel(); // Refresh the task display panel
       tasks.remove(task); // Remove the task from the list
       updateTaskStats(); // Update task completion statistics
   }

	// Clears and repopulates the task display panel with the current list of tasks.
	public void arrayListUpdate() {
		taskPanel.removeAll(); // Clear the task display panel

		// Add all tasks' UI components
		for (Task task : tasks) {
			taskPanel.add(task.getGuiComponent());
		}
	}
	
	 // ActionListener adds new HomeTask when button is used, sets a task listener,
	 // adds to task list and updates	
	class AddHomeTaskActionListener implements ActionListener {
		private ToDo ToDoApplication; // Reference to the main ToDo application
		
		 // Constructor for AddHomeTaskActionListener		  
		 // @param toDoApp instance		 
		public AddHomeTaskActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}
		
		// Invoked when an action occurs
		// @param e The event to be processed		 
		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.addHomeTask();
		}
	}

	 // ActionListener adding new StudyTask, as AddHomeTaskActionListener	 
	class AddStudyTaskActionListener implements ActionListener {
		private ToDo ToDoApplication;

		public AddStudyTaskActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.addStudyTask();
		}
	}
	
	 // ActionListener adding new CustomTask. It starts the addition of a
	 // CustomTask to the task list.	 
	class AddCustomTaskActionListener implements ActionListener {
		private ToDo ToDoApplication;

		public AddCustomTaskActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.addCustomTask();
		}
	}
	
	 // ActionListener sorting tasks alphabetically	 
	class AlphabeticSortActionListener implements ActionListener {
		private ToDo ToDoApplication;

		public AlphabeticSortActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.sortTasksAlphabetically();
		}
	}
	
	 // ActionListener sorting tasks by their completion status
	class CompletionSortActionListener implements ActionListener {
		private ToDo ToDoApplication;

		public CompletionSortActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.sortTasksByCompletionStatus();
		}
	}
	
	 // ActionListener sorting tasks by their type	 
	class TypeSortActionListener implements ActionListener {
		private ToDo ToDoApplication;

		public TypeSortActionListener(ToDo ToDoApplication) {
			this.ToDoApplication = ToDoApplication;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ToDoApplication.sortTasksByTaskType();
		}
	}
}