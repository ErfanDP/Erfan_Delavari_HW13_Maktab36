package com.example.erfan_delavari_hw12_maktab36.repository;

import com.example.erfan_delavari_hw12_maktab36.model.Task;
import com.example.erfan_delavari_hw12_maktab36.model.TaskState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements RepositoryInterface<Task>{
    private static TaskRepository sRepository;
    private static boolean sInitialised = false;
    private List<Task> mTaskList = new ArrayList<>();


    private TaskRepository(int numberOfTasks,String name ) {
        for (int i = 0; i < numberOfTasks; i++) {
            TaskState taskState = TaskState.DOING;
            switch (((int)(Math.random()*10)) % 3){
                case 1:
                    taskState = TaskState.DONE;
                    break;
                case 2:
                    taskState = TaskState.TODO;
            }
            mTaskList.add(new Task(name+"#"+(i+1) , taskState));
        }
    }

    public static void initialiseTaskList(int numberOfTasks , String name){
        sRepository  = new TaskRepository(numberOfTasks,name);
        sInitialised = true;
    }

    /**
     * for making this method to work you should first initialise TaskList
     * with initialiseTaskList() method
     * @return if sInitialised == true return Repository singleTone
     */
    public static TaskRepository getRepository() {
        if(sInitialised){
            return sRepository;
        }
        return null;
    }

    @Override
    public List<Task> getList() {
        return mTaskList;
    }

    @Override
    public Task get(UUID uuid) {
        for (Task task:mTaskList) {
            if(task.getUUID().equals(uuid)){
                return task;
            }
        }
        return null;
    }

    @Override
    public void setList(List<Task> list) {
        mTaskList = list;
    }

    @Override
    public void delete(Task task) {
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getUUID().equals(task.getUUID())) {
                mTaskList.remove(i);
                return;
            }
        }
    }

    @Override
    public void update(Task task) {
        Task updateTask = get(task.getUUID());
        updateTask.setName(task.getName());
        updateTask.setTaskState(task.getTaskState());
    }

    @Override
    public void insert(Task task) {
        mTaskList.add(task);
    }

    @Override
    public void insertToList(List<Task> list) {mTaskList.addAll(list);}


    public List<Task> getTaskListByTaskState(TaskState taskState){
        List<Task> taskList = new ArrayList<>();
        for (Task task:mTaskList) {
            if(task.getTaskState().equals(taskState)){
                taskList.add(task);
            }
        }
        return taskList;
    }
}
