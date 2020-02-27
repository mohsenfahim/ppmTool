package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.Backlog;
import com.mohsenfahim.ppmtool.domain.ProjectTask;
import com.mohsenfahim.ppmtool.exceptions.ProjectNotFoundException;
import com.mohsenfahim.ppmtool.repositories.BacklogRepository;
import com.mohsenfahim.ppmtool.repositories.ProjectRepository;
import com.mohsenfahim.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username ){
        //Exceptions: project not found
        //PTs to be added to specific project, Project != null, BackLog must exists

        // Original implementation code
        //  try {
        //  Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();

            // Set the backlog to projecttask/PT.
            projectTask.setBacklog(backlog);

            //we want our project sequence to be lile this: IDPRO-2 ... IDPRO-100
            Integer BacklogSeqeunce = backlog.getPTSequence();

            // Update the BL sequence
            BacklogSeqeunce++;
            backlog.setPTSequence(BacklogSeqeunce);

            //Add sequence to PT
            projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSeqeunce);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null
            if (projectTask.getPriority() == null || projectTask.getPriority()==0){
                projectTask.setPriority(3);
            }

            // INITIAL status when status is null.
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TODO:status");
            }

            return projectTaskRepository.save(projectTask);

            /*} end of original implementation code
        catch (Exception e){
            throw new ProjectNotFoundException("Project ID '"+projectIdentifier+"' does not exists");
        }*/
    }

    public Iterable<ProjectTask>findBacklogById(String id, String username) {

        /* original implementation code
        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
        }
        */

        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTBySequence(String backlog_id, String pt_id, String username){

        // make sure the backlog exists and we are searching for the correct backlog
        // Original implementation code
        /*
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null) {
            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
        }
        */

        projectService.findProjectByIdentifier(backlog_id, username);

        // make sure that our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null){
            throw new ProjectNotFoundException("Project task '"+pt_id+"' not found");
        }

        //make sure that backlog id in the project corresponds to right projeat

        if (!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project task '"+pt_id+"' not found in Project'"+backlog_id);
        }
        //make sure we are searching on the right backlog.

        return projectTask;
    }

    public ProjectTask updateProjectTask(ProjectTask updatedTask, String block_id, String pt_id, String username){

        ProjectTask projectTask = findPTBySequence(block_id, pt_id, username);

        //ProjectTask projectTask = projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());

        projectTask = updatedTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTbyProjectSequence(String block_id, String pt_id, String username){

        //ProjectTask projectTask = projectTaskRepository.findByProjectSequence(updatedTask.getProjectSequence());

        ProjectTask projectTask = findPTBySequence(block_id, pt_id, username);

       // Following four line of code works for deleting and saving the backlog onto the repository. But JPA gives
        // us a much better supported way by using cascade tag. Check Domain.ProjectTask and Domain.Project using JPA.
        /*
        Backlog backlog = projectTask.getBacklog();
        List<ProjectTask> pts = backlog.getProjectTasks();
        pts.remove(projectTask);
        backlogRepository.save(backlog);
        */

        projectTaskRepository.delete(projectTask);
    }
}
