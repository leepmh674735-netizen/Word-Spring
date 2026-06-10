package com.springinpractice.ch14.kite.config.xml;

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

// 아래 클래스들은 사용자의 도메인/라이브러리 내부 클래스라고 가정합니다. (패키지명 오타 수정)
import com.springinpractice.ch14.kite.interceptor.GuardListSourcePointcut;
import com.springinpractice.ch14.kite.interceptor.AnnotationGuardListSource;
import com.springinpractice.ch14.kite.interceptor.GuardListInterceptor;

public class AnnotationConfigParser implements BeanDefinitionParser {

    private static final String GUARD_LIST_ADV_BEAN_NAME = 
            "com.springinpractice.ch14.kite.interceptor.internalGuardListAdvisor";
    
    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigParser.class);

    @Override
    public BeanDefinition parse(Element elem, ParserContext parserCtx) {
        // 내부 헬퍼 클래스를 인스턴스화하여 빈 등록 프로세스를 시작합니다.
        new AopAutoProxyConfigurer(elem, parserCtx);
        return null;
    }
    
    // 정적 내부 클래스로 올바르게 분리
    private static class AopAutoProxyConfigurer {
        private final String tagName;
        private final ParserContext parserCtx;
        private final BeanDefinitionRegistry reg;
        private final Object src; // 오타 수정 (scr -> src)
        private final int baseOrder;
        
        public AopAutoProxyConfigurer(Element elem, ParserContext parserCtx) {
            this.tagName = elem.getTagName();
            this.parserCtx = parserCtx;
            this.reg = parserCtx.getRegistry();
            this.src = parserCtx.extractSource(elem);
            
            this.baseOrder = elem.hasAttribute("order") ? 
                    Integer.parseInt(elem.getAttribute("order")) : 0;
            
            // 프록시 생성기 등록
            AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserCtx, elem);
            configureGuardList();
        }
        
        private void configureGuardList() {
            if (reg.containsBeanDefinition(GUARD_LIST_ADV_BEAN_NAME)) {
                return;
            }
            
            // 1. Source 정의 및 등록
            RootBeanDefinition sdef = createDef(AnnotationGuardListSource.class);
            String sname = registerWithGeneratedName(sdef);
            
            // 2. Interceptor 정의 및 등록 (오타 수정 idf -> idef, sname 참조)
            RootBeanDefinition idef = createDef(GuardListInterceptor.class);
            addRuntimeProp(idef, "source", sname);
            String iname = registerWithGeneratedName(idef);
            
            // 3. Pointcut 정의 및 등록 (중복 변수명 pname 해결 및 오타 수정)
            RootBeanDefinition pdef = createDef(GuardListSourcePointcut.class);
            addRuntimeProp(pdef, "source", sname);
            String pname = registerWithGeneratedName(pdef);
            
            // 4. Advisor 정의 및 등록 (클래스명 오타 및 속성 메서드 수정)
            RootBeanDefinition adef = createDef(DefaultBeanFactoryPointcutAdvisor.class);
            addProp(adef, "adviceBeanName", iname);
            addRuntimeProp(adef, "pointcut", pname);
            addOrderProp(adef, baseOrder); // baseOrder 적용
            
            reg.registerBeanDefinition(GUARD_LIST_ADV_BEAN_NAME, adef);
            
            doLogicalView(sdef, sname, idef, iname, adef, GUARD_LIST_ADV_BEAN_NAME); 
        }

        // --- 생략된 헬퍼 메서드들 구현 예시 (Listing 14.31 가정) ---
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
            // RuntimeBeanReference를 사용하는 스프링 표준 방식
            def.getPropertyValues().add(name, new org.springframework.beans.factory.config.RuntimeBeanReference(beanRefName));
        }

        private void addOrderProp(RootBeanDefinition def, int order) {
            def.getPropertyValues().add("order", order);
        }

        private void doLogicalView(Object... args) {
            // 논리적 뷰 구성 로직 (디버깅/시각화용)
        }
    }
}