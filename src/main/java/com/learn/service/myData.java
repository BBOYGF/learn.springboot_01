package com.learn.service;

import com.learn.bean.Education;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class myData {
    public List<Education> getEducation()
    {
        ArrayList<Education> educations = new ArrayList<>();
        educations.add(new Education(1,"初中"));
        educations.add(new Education(2,"高中"));
        educations.add(new Education(3,"大专"));
        educations.add(new Education(4,"本科"));
        educations.add(new Education(5,"硕士"));
        educations.add(new Education(6,"博士"));
        return educations;
    }
}
