package com.bjs.cv.controllers;

import com.bjs.cv.entities.*;
import com.bjs.cv.enums.Level;
import com.bjs.cv.services.CVService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CvControllerTests {

    private CvController cvController;
    private CVService cvService;

    @BeforeEach
    public void initEachTest(){
        cvService = Mockito.mock(CVService.class);
        cvController = new CvController(cvService);
    }

    public CV createCV(){
        Address address = new Address("stret name", "ASN452", "London", "UK");
        UserInfo user = new UserInfo("Eduardo", 20, "19998446031",address);
        List<Education> educations = new ArrayList<>();
        educations.add(new Education("UFSCar", "Computer Science", "Bachelor Degree"));
        Skill skill = new Skill("python", Level.HIGH);
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        List<WorkExperience> workExperiences = new ArrayList<>();
        workExperiences.add(new WorkExperience("Daitan", "Good Work", LocalDate.of(2018, Month.JUNE, 13), LocalDate.of(2020, Month.AUGUST, 01)));
        CV cv = new CV(user, educations, skills, workExperiences);
        cv.setId(1);
        return cv;
    }

    @Test
    public void testGetAllCvsSuccessfully(){
        List<CV> Cvs = new ArrayList<>();
        Cvs.add(this.createCV());

        when(cvService.getAllCVs()).thenReturn(Cvs);
        Assert.isInstanceOf(ArrayList.class, cvController.getAllCVs(),"Should return a list of CVs");
        verify(cvService, times(1)).getAllCVs();
    }

    @Test
    public void testGetCVByIdSuccessfully(){
        CV cv = this.createCV();

        when(cvService.getCVById(cv.getId())).thenReturn(cv);
        Assert.isInstanceOf(CV.class, cvController.getCVById(cv.getId()),"Should return a instance of CV");
        Assertions.assertEquals(cv, cvController.getCVById(cv.getId()), "Should return the CV created");
        verify(cvService, times(2)).getCVById(cv.getId());
    }

}
