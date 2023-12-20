package com._6vo2dto.action;

import com._6vo2dto.service.IGenerateVo2Dto;
import com._6vo2dto.service.impl.GenerateVo2DtoImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;

public class Vo2DtoGenerateAction extends AnAction {
    IGenerateVo2Dto iGenerateVo2Dto = new GenerateVo2DtoImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {

        iGenerateVo2Dto.doGenerate(e.getProject(), e.getDataContext(), e.getData(LangDataKeys.PSI_FILE));
    }
}
