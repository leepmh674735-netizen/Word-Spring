<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

display message

<security:accesscontrollist hasPermission="2,8,16" domainObject="${message}">
    <ul>
        <security:accesscontrollist hasPermission="2" domainObject="${message}">
            <li><a href="${editMessageUrl}">Edit message</a></li>
        </security:accesscontrollist>
        
        <security:accesscontrollist hasPermission="16" domainObject="${message}">
            <li><a href="#">Block message</a></li>
            <li><a href="#">Unblock message</a></li>
        </security:accesscontrollist>
        
        <security:accesscontrollist hasPermission="8" domainObject="${message}">
            <li><a href="#">Delete message</a></li>
        </security:accesscontrollist>
    </ul>
    
    other stuff
</security:accesscontrollist>