package com._6vo2dto.service;

import com._6vo2dto.domain.model.GenerateContext;
import com._6vo2dto.domain.model.GetObjConfigDO;
import com._6vo2dto.domain.model.SetObjConfigDO;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractGenerateVo2Dto implements IGenerateVo2Dto {
    protected final String setRegex = "set(\\w+)";
    protected final String getRegex = "get(\\w+)";

    @Override
    public void doGenerate(Project project, DataContext dataContext, PsiFile psiFile) {
        //获取上下文
        GenerateContext generateContext = this.getGenerateContext(project, dataContext, psiFile);
        //获取对象的 set方法
        SetObjConfigDO setObjConfigDO = this.getSetObjConfigDo(generateContext);
        //获取对象的 get方法 从剪切板获取
        GetObjConfigDO getObjConfigDO = this.getObjConfigDOByClipboardText(generateContext);
        //织入set方法
        this.weavingSetGetCode(generateContext, setObjConfigDO, getObjConfigDO);
    }

    protected abstract void weavingSetGetCode(GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO);

    protected abstract GetObjConfigDO getObjConfigDOByClipboardText(GenerateContext generateContext);

    protected abstract SetObjConfigDO getSetObjConfigDo(GenerateContext generateContext);

    protected abstract GenerateContext getGenerateContext(Project project, DataContext dataContext, PsiFile psiFile);

    protected List<PsiClass> getAllPsiClassList(PsiClass psiClass) {
        List<PsiClass> psiClassList = new ArrayList<>();
        PsiClass currentPsi = psiClass;
        while (psiClass != null && !"Object".equals(currentPsi.getName())) {
            psiClassList.add(currentPsi);
            currentPsi = psiClass.getSuperClass();
        }
        return psiClassList;
    }
}
