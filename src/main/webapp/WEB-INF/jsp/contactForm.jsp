<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<c:url var="contactUrl" value="/contact" />

<form:form cssClass="main" action="${contactUrl}" modelAttribute="userMessage" method="post">
    <form:errors path="*">
        <div class="warning alert">
            <spring:message code="error.global" />
        </div>
    </form:errors>
    
    <form:hidden path="referer" />
    
    <div>Your name</div>
    <div>
        <form:input path="name" cssErrorClass="error" />
        <form:errors path="name" element="div" cssClass="error-message" htmlEscape="false" />
    </div>
    
    <div>Email Address</div>
    <div>
        <form:input path="email" cssErrorClass="error" />
        <form:errors path="email" element="div" cssClass="error-message" />
    </div>

    <div>Message Text</div>
    <div>
        <form:textarea path="text" cssErrorClass="error" rows="5" cols="30" />
        <form:errors path="text" element="div" cssClass="error-message" />
    </div>
    
    <div>
        <input type="submit" value="Submit" />
    </div>
</form:form>