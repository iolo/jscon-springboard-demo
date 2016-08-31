package kr.iolo.springboard.j2v8;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author iolo
 * @see org.springframework.web.servlet.view.script.ScriptTemplateViewResolver
 */
public class J2V8TemplateViewResolver extends UrlBasedViewResolver {

    public J2V8TemplateViewResolver() {
        this.setViewClass(this.requiredViewClass());
    }

    public J2V8TemplateViewResolver(String prefix, String suffix) {
        this();
        this.setPrefix(prefix);
        this.setSuffix(suffix);
    }

    protected Class<?> requiredViewClass() {
        return J2V8TemplateView.class;
    }

}
