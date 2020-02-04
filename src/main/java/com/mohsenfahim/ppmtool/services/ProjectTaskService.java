package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.Backlog;
import com.mohsenfahim.ppmtool.domain.ProjectTask;
import com.mohsenfahim.ppmtool.repositories.BacklogRepository;
import com.mohsenfahim.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask ){
        //Exceptions: project not found
        //PTs to be added to specific project, Project != null, BackLog must exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        // Set the backlog to projecttask/PT.
        projectTask.setBacklog(backlog);
        //we want our project sequence to be lile this: IDPRO-2 ... IDPRO-100
        Integer BacklogSeqeunce = backlog.getPTSequence();
        // Update the BL sequence
        BacklogSeqeunce++;
        backlog.setPTSequence(BacklogSeqeunce);
        //Add sequence to PT
        projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSeqeunce);
        projectTask.setProjectIdentifier(projectIdentifier);
        //INITIAL priority when priority null
        if (projectTask.getPriority()==0||projectTask.getPriority()==null) {
            projectTask.setPriority(3);
        }

        // INITIAL status when status is null.
        if (projectTask.getStatus()==""||projectTask.getStatus()==null){
            projectTask.setStatus("TODO:status");
        }
        return projectTaskRepository.save(projectTask);
    }
}
