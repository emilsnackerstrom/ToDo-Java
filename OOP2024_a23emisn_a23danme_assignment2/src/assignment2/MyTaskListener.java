package assignment2;

import se.his.it401g.todo.*;
import java.util.ArrayList;

//Implements the TaskListener interface to respond to events
// Updates the task statistics and UI components if changes are made
public class MyTaskListener implements TaskListener {
	private ArrayList<Task> tasks; // List of tasks being monitored
	private ToDo currentToDo; // Reference to the main ToDo application instance

	// Constructs MyTaskListener with list of tasks and a reference to the ToDo list
	// @param tasks Which task listener is doing actions with
	// @param currentToDo makes updates
	public MyTaskListener(ArrayList<Task> tasks, ToDo currentToDo) {
		this.tasks = tasks;
		this.currentToDo = currentToDo;
	}

	// Called when a task is modified
	// @param t is the modified task
	@Override
	public void taskChanged(Task t) {
		currentToDo.updateTaskStats(); // Update task completion statistics
	}

	// Called when a task is completed
	// @param t is the completed task
	@Override
	public void taskCompleted(Task t) {
		currentToDo.updateTaskStats(); // Update task completion statistics
	}

	// Called when completion status is removed
	// @param t is the modified task
	@Override
	public void taskUncompleted(Task t) {
		currentToDo.updateTaskStats(); // Update task completion statistics
	}

	// Called when a task gets created
	// @param t is the created task
	@Override
	public void taskCreated(Task t) {
		currentToDo.updateTaskStats(); // Update task completion statistics
	}

	// Called when a task is removed
	// @param t is the removed task
	@Override
	public void taskRemoved(Task t) {
		currentToDo.removeTaskGUIComponent(t); // Remove the task's GUI component
		currentToDo.updateTaskStats(); // Update task completion statistics
	}
}