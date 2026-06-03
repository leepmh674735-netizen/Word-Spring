<%-- Source project: sip03, branch: 02 (Maven Project) --%>
<% taglib prefix="form"
    uri="http://www.springframework.org/tags/form" %>

<html>
     <head>
           <title>Nominate a member for the award</title>
    </head>
    <body>
      <h1>Nominate a member for the award</h1>
     <from: from modelAttribute="member">
	      <div>First name:<from:input path="firstName" </div>
	<div>Last name:<form:input path="lastName" /></div>
	<div><input type="sumbit" value="Sumbit"></input>
	</div>
	</from:form>
	</body>
</html>