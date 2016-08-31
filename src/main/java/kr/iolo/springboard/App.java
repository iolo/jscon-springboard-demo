package kr.iolo.springboard;

import kr.iolo.springboard.j2v8.J2V8TemplateViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class App {
    private static final Logger L = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        final ApplicationContext ctx = SpringApplication.run(App.class, args);
        if (L.isTraceEnabled()) {
            L.trace("**************************");
            for (final String beanName : ctx.getBeanDefinitionNames()) {
                if (L.isTraceEnabled()) {
                    L.trace(beanName);
                }
            }
            L.trace("**************************");
        }
    }

    @Bean
    public ViewResolver viewResolver() {
        J2V8TemplateViewResolver bean = new J2V8TemplateViewResolver();
        bean.setPrefix("classpath:/templates/");
        //bean.setSuffix(".hbs");
        bean.setSuffix(".html");
        return bean;
    }
}
