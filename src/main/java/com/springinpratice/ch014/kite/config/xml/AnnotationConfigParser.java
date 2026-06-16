package com.springinpratice.ch014.kite.config.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.springinpractice.ch14.kite.interceptor.GuardListSourcePointcut;
import com.springinpractice.ch14.kite.interceptor.AnnotationGuardListSource;
import com.springinpractice.ch14.kite.interceptor.GuardListInterceptor;

public class AnnotationConfigParser implements BeanDefinitionParser {

    private static final String GUARD_LIST_ADV_BEAN_NAME = 
            "com.springinpractice.ch14.kite.interceptor.internalGuardListAdvisor";
    
    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigParser.class);

    @Override
    public BeanDefinition parse(Element elem, ParserContext parserCtx) {
        new AopAutoProxyConfigurer(elem, parserCtx);
        return null;
    }
    
    private static class AopAutoProxyConfigurer {
        private final String tagName;
        private final ParserContext parserCtx;
        private final BeanDefinitionRegistry reg;
        private final Object src;
        private final int baseOrder;
        
        public AopAutoProxyConfigurer(Element elem, ParserContext parserCtx) {
            this.tagName = elem.getTagName();
            this.parserCtx = parserCtx;
            this.reg = parserCtx.getRegistry();
            this.src = parserCtx.extractSource(elem);
            
            this.baseOrder = elem.hasAttribute("order") ? 
                    Integer.parseInt(elem.getAttribute("order")) : 0;
            
            AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserCtx, elem);
            configureGuardList();
        }
        
        private void configureGuardList() {
            if (reg.containsBeanDefinition(GUARD_LIST_ADV_BEAN_NAME)) {
                return;
            }
            
            RootBeanDefinition sdef = createDef(AnnotationGuardListSource.class);
            String sname = registerWithGeneratedName(sdef);
            
            RootBeanDefinition idef = createDef(GuardListInterceptor.class);
            addRuntimeProp(idef, "source", sname);
            String iname = registerWithGeneratedName(idef);
            
            RootBeanDefinition pdef = createDef(GuardListSourcePointcut.class);
            addRuntimeProp(pdef, "source", sname);
            String pname = registerWithGeneratedName(pdef);
            
            RootBeanDefinition adef = createDef(DefaultBeanFactoryPointcutAdvisor.class);
            addProp(adef, "adviceBeanName", iname);
            addRuntimeProp(adef, "pointcut", pname);
            addOrderProp(adef, baseOrder);
            
            reg.registerBeanDefinition(GUARD_LIST_ADV_BEAN_NAME, adef);
            
            doLogicalView(sdef, sname, idef, iname, adef, GUARD_LIST_ADV_BEAN_NAME); 
        }

        private RootBeanDefinition createDef(Class<?> clazz) {
            return new RootBeanDefinition(clazz);
        }

        private String registerWithGeneratedName(RootBeanDefinition def) {
            return BeanDefinitionReaderUtils.registerWithGeneratedName(def, this.reg);
        }

        private void addProp(RootBeanDefinition def, String name, Object value) {
            def.getPropertyValues().add(name, value);
        }

        private void addRuntimeProp(RootBeanDefinition def, String name, String beanRefName) {
            def.getPropertyValues().add(name, new org.springframework.beans.factory.config.RuntimeBeanReference(beanRefName));
        }

        private void addOrderProp(RootBeanDefinition def, int order) {
            def.getPropertyValues().add("order", order);
        }

        private void doLogicalView(Object... args) {
        }
    }
}
