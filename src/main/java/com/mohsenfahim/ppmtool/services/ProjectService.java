package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.Backlog;
import com.mohsenfahim.ppmtool.domain.Project;
import com.mohsenfahim.ppmtool.exceptions.ProjectIdException;
import com.mohsenfahim.ppmtool.exceptions.ProjectIdException;
import com.mohsenfahim.ppmtool.repositories.BacklogRepository;
import com.mohsenfahim.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){

        //TODO: Logic for save and update goes here...

        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId()==null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null){
            throw new ProjectIdException("Project ID '"+projectId+"' does not exists");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectbyIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null){
            throw new ProjectIdException("Cannot find Project ID '"+projectId+"' This project does not exists");
        }
        projectRepository.delete(project);
    }

}
