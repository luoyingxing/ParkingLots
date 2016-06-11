package com.luo.park.entity;

import com.luo.park.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * projectList
 * <p/>
 * Created by luoyingxing on 16/6/9.
 */
public class Project implements Serializable {
    private int id;
    private int imageId;
    private String title;

    public Project() {
    }

    public static List<Project> getProjectList() {
        List<Project> projectList = new ArrayList<>();

        Project project1 = new Project();
        project1.setId(80001);
        project1.setImageId(R.mipmap.icon_main_scan);
        project1.setTitle("扫一扫");
        projectList.add(project1);

        Project project2 = new Project();
        project2.setId(80002);
        project2.setImageId(R.mipmap.icon_main_seach);
        project2.setTitle("查询");
        projectList.add(project2);

        Project project3 = new Project();
        project3.setId(80003);
        project3.setImageId(R.mipmap.icon_main_statistics);
        project3.setTitle("统计");
        projectList.add(project3);

        Project project4 = new Project();
        project4.setId(80004);
        project4.setImageId(R.mipmap.icon_main_history);
        project4.setTitle("停车记录");
        projectList.add(project4);

        Project project5 = new Project();
        project5.setId(80005);
        project5.setImageId(R.mipmap.icon_main_card);
        project5.setTitle("办卡登记");
        projectList.add(project5);

        Project project6 = new Project();
        project6.setId(80006);
        project6.setImageId(R.mipmap.icon_main_fare);
        project6.setTitle("缴费");
        projectList.add(project6);

        return projectList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
