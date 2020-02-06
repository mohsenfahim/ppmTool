package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.Backlog;
import com.mohsenfahim.ppmtool.domain.Project;
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

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask ){
        //Exceptions: project not found
        //PTs to be added to specific project, Project != null, BackLog must exists

        try {

            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

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
            if (projectTask.getPriority() == null) { // projectTask.getPriority()==0||
                projectTask.setPriority(3);
            }

            // INITIAL status when status is null.
            if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
                projectTask.setStatus("TODO:status");
            }
            return projectTaskRepository.save(projectTask);
        }
        catch (Exception e){
            throw new ProjectNotFoundException("Project ID '"+projectIdentifier+"' does not exists");
        }
    }

    public Iterable<ProjectTask>findBacklogById(String id){

        Project project = projectRepository.findByProjectIdentifier(id);

        if(project==null){
            throw new ProjectNotFoundException("Project with ID: '"+id+"' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }
}
