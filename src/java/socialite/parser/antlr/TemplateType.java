package socialite.parser.antlr;

import socialite.util.MySTGroupFile;

import java.util.List;

public class TemplateType extends TableOpt {
    String templateName;
    List<String> args;
    public TemplateType(String _templateName, List<String> _args) {
        templateName = _templateName;
        args = _args;
        try {
            new MySTGroupFile(getClass().getResource(templateName + ".stg"), "UTF-8", '<', '>').load();
        } catch (Exception e) {
            throw new RuntimeException("Cannot load table type "+templateName);
        }
    }
}
