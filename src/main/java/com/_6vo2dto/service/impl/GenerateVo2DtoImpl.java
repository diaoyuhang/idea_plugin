package com._6vo2dto.service.impl;

import com._6vo2dto.domain.model.GenerateContext;
import com._6vo2dto.domain.model.GetObjConfigDO;
import com._6vo2dto.domain.model.SetObjConfigDO;
import com._6vo2dto.service.AbstractGenerateVo2Dto;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class GenerateVo2DtoImpl extends AbstractGenerateVo2Dto {


    @Override
    protected void weavingSetGetCode(GenerateContext generateContext, SetObjConfigDO setObjConfigDO, GetObjConfigDO getObjConfigDO) {
        Application application = ApplicationManager.getApplication();

        application.runWriteAction(() -> {
            Integer offset = generateContext.getOffset();
            Document document = generateContext.getDocument();
            int lineStartNumber = document.getLineNumber(offset) + 1;

            List<String> paramList = setObjConfigDO.getParamList();
            Map<String, String> setParamMtdMap = setObjConfigDO.getParamMtdMap();
            Map<String, String> getParamMtdMap = getObjConfigDO.getParamMtdMap();

            for (String param : paramList) {
                int lineStartOffset = document.getLineStartOffset(lineStartNumber++);
                WriteCommandAction.runWriteCommandAction(generateContext.getProject(), () -> {
                    generateContext.getDocument().insertString(lineStartOffset, String.format("%s.%s(%s.%s());\n",
                            setObjConfigDO.getClazzParamName(),
                            setParamMtdMap.get(param),
                            getObjConfigDO.getClazzParam(),
                            getParamMtdMap.get(param)));
                });

            }
        });
    }

    @Override
    protected GetObjConfigDO getObjConfigDOByClipboardText(GenerateContext generateContext) {
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = systemClipboard.getContents(null);
        String systemClipboardText = "";
        if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                systemClipboardText = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] split = systemClipboardText.split(" ");
        if (split.length < 2) {
            return new GetObjConfigDO(null, null, new HashMap<>());
        }

        String clazz = split[0];
        String clazzParam = split[1];

        @NotNull PsiClass[] psiClasses = PsiShortNamesCache.getInstance(generateContext.getProject()).getClassesByName(clazz, GlobalSearchScope.projectScope(generateContext.getProject()));
        PsiClass psiClass = psiClasses[0];
        List<PsiClass> classes = getAllPsiClassList(psiClass);

        Pattern pattern = Pattern.compile(getRegex);
        HashMap<String, String> paramMap = new HashMap<>();
        List<String> paramMethod = new ArrayList<>();
        for (PsiClass psiClazz : classes) {
            List<String> methods = getMethods(psiClazz, getRegex);
            for (String method : methods) {
                String name = pattern.matcher(method).replaceAll("$1").toLowerCase();
                paramMethod.add(method);
                paramMap.put(name, method);
            }
        }

        return new GetObjConfigDO(clazz, clazzParam, paramMap);
    }

    @Override
    protected SetObjConfigDO getSetObjConfigDo(GenerateContext generateContext) {
        PsiElement psiElement = generateContext.getPsiElement();

        PsiClass psiClass = null;
        String clazzParamName = null;

        if (psiElement instanceof PsiClass) {
            PsiFile psiFile = generateContext.getPsiFile();
            PsiElement elementAt = psiFile.findElementAt(generateContext.getOffset());
            //获取当前光标所在的类
            psiClass = (PsiClass) generateContext.getPsiElement();
            int offsetStep = generateContext.getOffset() + 1;

            while (null == elementAt || elementAt.getText().equals(psiClass.getName()) || elementAt instanceof PsiWhiteSpace) {
                elementAt = psiFile.findElementAt(++offsetStep);
            }

            System.out.println(elementAt.getText());
            clazzParamName = elementAt.getText();
        } else if (psiElement instanceof PsiLocalVariable) {
            PsiFile psiFile = generateContext.getPsiFile();
            PsiElement elementAt = psiFile.findElementAt(generateContext.getOffset());
            System.out.println(elementAt.getText());

        }
        //获取类的set方法
        List<PsiClass> psiClassList = getAllPsiClassList(psiClass);
        Map<String, String> paramMtdMap = new HashMap<>();
        List<String> paramList = new ArrayList<>();

        Pattern pattern = Pattern.compile(setRegex);
        for (PsiClass psiClazz : psiClassList) {
            List<String> methodList = getMethods(psiClazz, setRegex);

            for (String method : methodList) {
                String param = pattern.matcher(method).replaceAll("$1").toLowerCase();
                paramMtdMap.put(param, method);
                paramList.add(param);
            }
        }
        return new SetObjConfigDO(clazzParamName, paramList, paramMtdMap);
    }

    private List<String> getMethods(PsiClass psiClazz, String setRegex) {
        List<String> methodList = new ArrayList<>();
        PsiMethod[] methods = psiClazz.getMethods();

        for (PsiMethod method : methods) {
            String methodName = method.getName();
            if (Pattern.matches(setRegex, methodName) && !methodList.contains(methodName)) {
                methodList.add(methodName);
            }
        }
        return methodList;
    }


    @Override
    protected GenerateContext getGenerateContext(Project project, DataContext dataContext, PsiFile psiFile) {
        Editor editor = CommonDataKeys.EDITOR.getData(dataContext);
        PsiElement psiElementData = CommonDataKeys.PSI_ELEMENT.getData(dataContext);

        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int offset = caretModel.getOffset();
        int lineNumber = document.getLineNumber(offset);

        int lineStartOffset = document.getLineStartOffset(lineNumber);
        CharSequence charsSequence = document.getCharsSequence();

        GenerateContext generateContext = new GenerateContext();
        generateContext.setProject(project);
        generateContext.setPsiFile(psiFile);
        generateContext.setPsiElement(psiElementData);
        generateContext.setEditor(editor);
        generateContext.setDataContext(dataContext);
        generateContext.setDocument(document);
        generateContext.setOffset(offset);
        generateContext.setLineNumber(lineNumber);
        generateContext.setStartOffset(lineStartOffset);
        generateContext.setEditorText(charsSequence);

        return generateContext;
    }
}
