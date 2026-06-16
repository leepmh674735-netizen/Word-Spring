<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/2000/ajax">
<head>
    <title>Subscribe Mailing List</title>
    <style>
        .error { color: red; border: 1px solid red; }
    </style>
</head>
<body>

<c:url var="subscribeUrl" value="/mailinglist/subscribe.html" />

<div>
    <%-- 1. 만료 메시지 --%>
    <c:if test="${not empty expired}">
        <div>
            Sorry, your previous subscription request has expired.
            To subscribe you will need to complete a new subscription request using the form.
        </div>
    </c:if>

    <%-- 2. 실패 메시지 --%>
    <c:if test="${not empty failed}">
        <div>
            Sorry, we were unable to confirm your subscription. 
            If you copied the URL from your confirmation e-mail into the browser, please make sure you copied the entire URL.
            Otherwise, you can complete a new subscription request using the form.
        </div>
    </c:if>

    <p>To subscribe, please provide your name and e-mail address.</p>

    <%-- 3. 구독 폼 시작 --%>
    <form:form modelAttribute="subscriber" action="${subscribeUrl}" method="post">
        
        <%-- 글로벌 에러 처리 --%>
        <form:errors path="">
            <div><spring:message code="error.global" /></div>
        </form:errors>
        
        <%-- 이름 입력 영역 --%>
        <div>Your first name:</div>
        <div>
            <form:input path="firstName" cssErrorClass="error" />
            <form:errors path="firstName" cssClass="error" />
        </div>
        
        <%-- 성 입력 영역 --%>
        <div>Your last name:</div>
        <div>
            <form:input path="lastName" cssErrorClass="error" />
            <form:errors path="lastName" cssClass="error" />
        </div>

        <%-- 이메일 입력 영역 --%>
        <div>Email Address:</div>
        <div>
            <form:input path="email" cssErrorClass="error" />
            <form:errors path="email" cssClass="error" />
        </div>
        
        <%-- 제출 버튼 --%>
        <div style="margin-top: 10px;">
            <input type="submit" value="Subscribe" />
        </div>

    </form:form>
</div>

</body>
</html>
