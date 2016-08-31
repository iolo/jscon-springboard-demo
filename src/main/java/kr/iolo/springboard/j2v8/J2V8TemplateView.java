package kr.iolo.springboard.j2v8;

import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * TODO: make robust & configurable!
 *
 * @author iolo
 * @see org.springframework.web.servlet.view.script.ScriptTemplateView
 * @see org.springframework.web.servlet.view.script.ScriptTemplateConfig
 * @see org.springframework.web.servlet.view.script.ScriptTemplateConfigurer
 */
public class J2V8TemplateView extends AbstractUrlBasedView {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String DEFAULT_CONTENT_TYPE = "text/html;charset=" + DEFAULT_CHARSET.name();

    private Charset charset = DEFAULT_CHARSET;

    public J2V8TemplateView() {
        this.setContentType(null);
    }

    public J2V8TemplateView(String url) {
        super(url);
        this.setContentType(DEFAULT_CONTENT_TYPE);
    }

    public void setContentType(String contentType) {
        super.setContentType(contentType);
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    protected void renderMergedOutputModel(
            Map<String, Object> model,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {
        final String url = getUrl();
        final String template = getTemplate(url);
        res.setContentType(getContentType());
        res.setCharacterEncoding(charset.name());
        res.getWriter().write(J2V8Utils.render(template, model, getUrl()));
    }

    private String getTemplate(String url) throws Exception {
        final Resource resource = getResource(url);
        final Reader reader = new InputStreamReader(resource.getInputStream(), charset);
        return FileCopyUtils.copyToString(reader);
    }

    private Resource getResource(String url) throws Exception {
        return getApplicationContext().getResource(url);
    }

}
