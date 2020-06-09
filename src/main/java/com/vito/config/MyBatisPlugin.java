package com.vito.config;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by VitoYi on 2020/6/9.
 */
public class MyBatisPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        if (hasLombok) {
            // 添加domain的import
            topLevelClass.addImportedType("lombok.Data");

            // 添加domain的注解
            topLevelClass.addAnnotation("@Data");
        }

        topLevelClass.addJavaDocLine("/**");

        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" * ").append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @author ").append(System.getProperties().getProperty("user.name"));
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @date ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        return true;
    }

//    生成注解
//    @Override
//    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
//                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
//        field.addJavaDocLine("/**");
//        String remarks = introspectedColumn.getRemarks();
//        if (StringUtility.stringHasValue(remarks)) {
//            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
//            for (String remarkLine : remarkLines) {
//                field.addJavaDocLine(" * " + remarkLine);
//            }
//        }
//        field.addJavaDocLine(" */");
//        return true;
//    }

//    @Override
//    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        // 添加Mapper的import
//        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
//
//        // 添加Mapper的注解
//        interfaze.addAnnotation("@Mapper");
//        return true;
//    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成getter
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        return !hasLombok;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成setter
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        return !hasLombok;
    }

    protected String getDateString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(LocalDateTime.now());
    }
}
