package com.springinpractice.ch09.comment.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.core.io.Resource;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RichTextFilter implements TextFilter {
    private Resource r;
    private Resource convert;
    
    public Resource getR() { 
        return r; 
    }
    
    public void setR(Resource r) { 
        this.r = r; 
    }
    
    public Resource getConvert() { 
        return convert; 
    }
    
    public void setConvert(Resource convert) { 
        this.convert = convert; 
    }
    
    @Override
    public String filter(String text) {
        Context ctx = Context.enter();
        try {
            ScriptableObject scope = ctx.initStandardObjects();
            
            String[] names = { "print", "load" };
            scope.defineFunctionProperties(names, scope.getClass(), ScriptableObject.DONTENUM);
            
            Scriptable argsObj = ctx.newArray(scope, new Object[] {});
            scope.defineProperty("arguments", argsObj, ScriptableObject.DONTENUM);
            
            ctx.evaluateReader(scope, new InputStreamReader(r.getInputStream()), "r", 1, null);
            
            scope.defineProperty("markdown", text, ScriptableObject.DONTENUM);
            
            ctx.evaluateReader(scope, new InputStreamReader(convert.getInputStream()), "convert", 1, null);
            
            return (String) scope.get("html", scope);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Context.exit();
        }
    }
}