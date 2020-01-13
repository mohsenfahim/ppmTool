package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.Project;
import com.mohsenfahim.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){

        //TODO: Logic for save and update goes here...

        return projectRepository.save(project);
    }
}
