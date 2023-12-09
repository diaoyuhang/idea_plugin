package com._4scaffolding.infrastructure;

import com._4scaffolding.vo.ProjectConfigVO;

public class DataState {

    private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }
}
