package com.mtsmda.keygen.desktop.configuration;

import com.mtsmda.helper.ObjectHelper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminzat on 9/28/2016.
 */
public class FreeMarkerFacade {

    private static final Logger LOGGER = Logger.getLogger(FreeMarkerFacade.class);

    private Configuration configuration;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
//        this.configuration.setObjectWrapper(new BeansWrapperBuilder().build());
    }

    public void writeContent(String templateName, Writer writer, Object... parameters) throws IOException, TemplateException {
        writeContentProcess(templateName, writer, null, parameters);
    }

    public void writeContent(String templateName, Writer writer, Map<String, Object> params) {
        try {
            writeContentProcess(templateName, writer, params);
        } catch (Exception e) {
            throw new RuntimeException("write content freemarket content error!", e);
        }
    }

    private void writeContentProcess(String templateName, Writer writer, Map<String, Object> params, Object... parameters) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName/*, "UTF-8"*/);
        if (ObjectHelper.objectIsNull(params)) {
            params = new HashMap<>();
            if (ObjectHelper.objectIsNull(parameters) && ObjectHelper.objectIsNull(params)) {
                throw new RuntimeException("Cannot null map and object array!");
            }
            for (int i = 0; i < parameters.length; i += 2) {
                params.put(parameters[i].toString(), parameters[i + 1]);
            }
        }

        template.process(params, writer);
    }

    /*public static void main(String[] args) {
        oo(new HashMap<>(), new String());
        oo(null, new String());
        Map<String, Object> map = null;
        oo(map, new String());
    }

    private static void oo(Map<String, Object> map, Object... objects) {
        System.out.println(ObjectHelper.objectIsNull(map));
        if (ObjectHelper.objectIsNull(map)) {
            map = new HashMap<>();
        }
        System.out.println(ObjectHelper.objectIsNull(map));
    }*/


}