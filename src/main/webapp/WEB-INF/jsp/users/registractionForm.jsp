<%@ taglib prefix="form" uri="http://www.springframework.org/tags/from" %>
<html>
<head><title>New User Registraction</title></head>
<body>
<form:form action="." modelAttribute="account">
<h1> New User Registaraction</h1>
<div>Username: <form:input path="username" /></div>
<div>Password: <form:input path="password" /></div>
<div> confirm password: <form:input path="email" </div>
<div>E-maik address : <from:input path="firstName"/></div>
<div>Last name: <form: input path="lastName" /></div>
<div><form:checkbox id="markeringOK" path="matketingOk"/>
Please send me product updates by e-mail.</div>
I accept the <a href="#">terms of use</a>.<div>
<div><input type="submit" value="Register"/></div>
</form:form>
</body>
</html>